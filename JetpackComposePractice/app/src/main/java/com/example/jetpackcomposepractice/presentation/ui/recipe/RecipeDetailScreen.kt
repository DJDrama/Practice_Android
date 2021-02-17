package com.example.jetpackcomposepractice.presentation.ui.recipe

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
    Log.d(TAG, "RecipeDetailScreen: ${viewModel}")
    Text("RecipeDetailScreen: $recipeId")

//    val loading = viewModel.loading.value
//    val recipe = viewModel.recipe.value
//    val scaffoldState = rememberScaffoldState()
//
//    AppTheme(
//        displayProgressBar = loading,
//        scaffoldState = scaffoldState,
//        darkTheme = isDarkTheme,
//    ) {
//        Scaffold(
//            scaffoldState = scaffoldState,
//            snackbarHost = {
//                scaffoldState.snackbarHostState
//            }
//        ) {
//            Box(
//                modifier = Modifier.fillMaxSize()
//            ) {
//                if (loading && recipe == null) LoadingRecipeShimmer(imageHeight = IMAGE_HEIGHT.dp)
//                else recipe?.let {
//                    if (it.id == 1) { // force an error to demo snackbar
//                        snackbarController.getScope().launch {
//                            snackbarController.showSnackbar(
//                                scaffoldState = scaffoldState,
//                                message = "An error occurred with this recipe",
//                                actionLabel = "Ok"
//                            )
//                        }
//                    } else {
//                        RecipeView(
//                            recipe = it,
//                        )
//                    }
//                }
//            }
//        }
//    }

}