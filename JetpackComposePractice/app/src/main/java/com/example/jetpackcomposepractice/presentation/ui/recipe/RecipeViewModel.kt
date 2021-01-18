package com.example.jetpackcomposepractice.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcomposepractice.domain.model.Recipe
import com.example.jetpackcomposepractice.repository.RecipeRepository
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Named

const val STATE_KEY_RECIPE = "state.key.recipeId"


class RecipeViewModel
@ViewModelInject
constructor(
    private val recipeRepository: RecipeRepository,
    @Named("auth_token") private val token: String,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {
    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    val loading = mutableStateOf(false)

    init {
        //restore if process death
        state.get<Int>(STATE_KEY_RECIPE)?.let { recipeId ->
            onTriggerEvent(RecipeEvent.GetRecipeEvent(recipeId))
        }
    }

     fun onTriggerEvent(event: RecipeEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is RecipeEvent.GetRecipeEvent -> {
                        if (recipe.value == null) {
                            getRecipe(event.id)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "onTriggerEvent ${e}, ${e.cause}")
            }
        }
    }

    private suspend fun getRecipe(id: Int) {
        loading.value = true
        val recipe = recipeRepository.get(token = token, id = id)
        this.recipe.value = recipe
        state.set(STATE_KEY_RECIPE, recipe.id)
        loading.value = false
    }
}