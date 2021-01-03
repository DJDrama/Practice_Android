package com.example.jetpackcomposepractice.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposepractice.domain.model.Recipe
import com.example.jetpackcomposepractice.repository.RecipeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Named

class RecipeListViewModel
@ViewModelInject
constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String
) : ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query: MutableState<String> = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null) // can be null
    var categoryScrollPosition: Float = 0f // track position

    val loading = mutableStateOf(false)

    init {
        newSearch()
    }
    private fun resetSearchState(){
        recipes.value = listOf() //reset list
        if(selectedCategory.value?.value != query.value)
            clearSelectedCategory()

    }
    private fun clearSelectedCategory(){
        selectedCategory.value = null
    }

    fun newSearch() {
        viewModelScope.launch {
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
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }

    fun onChangeCategoryScrollPosition(position: Float) {
        categoryScrollPosition = position
    }
}