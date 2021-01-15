package com.example.jetpackcomposepractice.presentation.ui.recipe_list

sealed class RecipeListEvent {
    // object since no arguments. if arguments there should be class or data class
    object NewSearchEvent : RecipeListEvent()
    object NextPageEvent : RecipeListEvent()
}