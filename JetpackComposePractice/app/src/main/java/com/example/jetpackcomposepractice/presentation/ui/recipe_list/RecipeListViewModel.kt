package com.example.jetpackcomposepractice.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.jetpackcomposepractice.domain.model.Recipe
import com.example.jetpackcomposepractice.repository.RecipeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Named

const val PAGE_SIZE = 30

const val STATE_KEY_PAGE = "recipe.state.page.key"
const val STATE_KEY_QUERY = "recipe.state.query.key"
const val STATE_KEY_LIST_POSITION = "recipe.state.query.list_position"
const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.query.selected_category"

class RecipeListViewModel
@ViewModelInject
constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query: MutableState<String> = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null) // can be null
    var categoryScrollPosition: Float = 0f // track position

    val loading = mutableStateOf(false)

    val page = mutableStateOf(1)
    private var recipeListScrollPosition = 0

    init {
        /** For Process Death **/
        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let {
            setPage(it)
        }
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let {
            setQuery(it)
        }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let {
            setListScrollPosition(it)
        }
        savedStateHandle.get<FoodCategory>(STATE_KEY_SELECTED_CATEGORY)?.let {
            setSelectedCategory(it)
        }

        if (recipeListScrollPosition != 0) {
            // Restore States
            onTriggerEvent(RecipeListEvent.RestoreStateEvent)
        } else {
            onTriggerEvent(RecipeListEvent.NewSearchEvent)
        }
    }

    fun onTriggerEvent(event: RecipeListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is RecipeListEvent.NewSearchEvent -> {
                        newSearch()
                    }
                    is RecipeListEvent.NextPageEvent -> {
                        nextPage()
                    }
                    is RecipeListEvent.RestoreStateEvent -> {
                        restoreState()
                    }
                }
            } catch (e: Exception) {
                Log.e("RecipeListVM", "onTriggerEvent: Exception ${e} ${e.cause}")
            }

        }
    }

    //since we do not use caching
    private suspend fun restoreState() {
        loading.value = true
        val results: MutableList<Recipe> = mutableListOf()
        for (p in 1..page.value) {
            val result = repository.search(
                token = token,
                page = p,
                query = query.value
            )
            results.addAll(result)
            if (p == page.value) {
                recipes.value = results
                loading.value = false
            }
        }
    }

    /** Append New recipes to the current list of recipes **/
    private fun appendRecipes(recipes: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    private fun incrementPage() {
        setPage(page.value + 1)
        //page.value = page.value + 1
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        setListScrollPosition(position)
    }

    private fun resetSearchState() {
        recipes.value = listOf() //reset list
        if (selectedCategory.value?.value != query.value)
            clearSelectedCategory()

    }

    private fun clearSelectedCategory() {
        //selectedCategory.value = null
        setSelectedCategory(null)
    }

    //use case #1
    private suspend fun newSearch() {
        loading.value = true
        resetSearchState() // reset the list
        /** Just to see loading **/
        delay(2000)


        val result = repository.search(
            token = token,
            page = 1,
            query = query.value
        )
        recipes.value = result
        loading.value = false
    }

    //use case #2
    private suspend fun nextPage() {
        // prevent duplicate events due to recompose happening to quickly
        if ((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
            loading.value = true
            incrementPage()

            // to show pagination
            delay(1000)

            if (page.value > 1) {
                val result = repository.search(
                    token = token,
                    page = page.value,
                    query = query.value
                )
                appendRecipes(result)
            }
            loading.value = false
        }
    }


    fun onQueryChanged(query: String) {
        setQuery(query)
        //this.query.value = query
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        //selectedCategory.value = newCategory
        setSelectedCategory(newCategory)
        onQueryChanged(category)
    }

    fun onChangeCategoryScrollPosition(position: Float) {
        categoryScrollPosition = position
    }

    /** SavedHandleState **/
    private fun setListScrollPosition(position: Int) {
        recipeListScrollPosition = position
        savedStateHandle.set(STATE_KEY_LIST_POSITION, position)
    }

    private fun setPage(page: Int) {
        this.page.value = page
        savedStateHandle.set(STATE_KEY_PAGE, page)
    }

    private fun setSelectedCategory(category: FoodCategory?) {
        selectedCategory.value = category
        savedStateHandle.set(STATE_KEY_SELECTED_CATEGORY, category)
    }

    private fun setQuery(query: String) {
        this.query.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }
}