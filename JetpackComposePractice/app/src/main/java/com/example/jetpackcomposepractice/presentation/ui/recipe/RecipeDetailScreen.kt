package com.example.jetpackcomposepractice.presentation.ui.recipe

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposepractice.presentation.components.IMAGE_HEIGHT
import com.example.jetpackcomposepractice.presentation.components.LoadingRecipeShimmer
import com.example.jetpackcomposepractice.presentation.components.RecipeView
import com.example.jetpackcomposepractice.presentation.theme.AppTheme
import com.example.jetpackcomposepractice.util.TAG
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalMaterialApi
@Composable
fun RecipeDetailScreen(
    isDarkTheme: Boolean,
    recipeId: Int?,
    viewModel: RecipeViewModel
) {
    if (recipeId == null){
        TODO("Show Invalid Recipe")
    }else {
        // fire a one-off event to get the recipe from api
        val onLoad = viewModel.onLoad.value
        if (!onLoad) {
            viewModel.onLoad.value = true
            viewModel.onTriggerEvent(RecipeEvent.GetRecipeEvent(recipeId))
        }

        val loading = viewModel.loading.value

        val recipe = viewModel.recipe.value

        val scaffoldState = rememberScaffoldState()

        val dialogQueue = viewModel.dialogQueue

        AppTheme(
            displayProgressBar = loading,
            scaffoldState = scaffoldState,
            dialogQueue = dialogQueue.queue.value,
            darkTheme = isDarkTheme,
        ){
            Scaffold(
                scaffoldState = scaffoldState,
                snackbarHost = {
                    scaffoldState.snackbarHostState
                }
            ) {
                Box (
                    modifier = Modifier.fillMaxSize()
                ){
                    if (loading && recipe == null) {
                        LoadingRecipeShimmer(imageHeight = IMAGE_HEIGHT.dp)
                    }
                    else if(!loading && recipe == null && onLoad){
                        TODO("Show Invalid Recipe")
                    }
                    else {
                        recipe?.let {RecipeView(recipe = it) }
                    }
                }
            }
        }
    }
}