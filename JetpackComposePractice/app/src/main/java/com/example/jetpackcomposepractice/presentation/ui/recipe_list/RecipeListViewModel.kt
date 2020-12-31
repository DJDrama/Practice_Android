package com.example.jetpackcomposepractice.presentation.ui.recipe_list

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

class RecipeListViewModel
@ViewModelInject
constructor(
    private val randomString: String
): ViewModel(){

    init{
        Log.e("TEST", "Inside VM: $randomString")
    }

}