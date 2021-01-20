package com.example.jetpackcomposepractice.presentation.components

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetpackcomposepractice.R
import com.example.jetpackcomposepractice.domain.model.Recipe
import com.example.jetpackcomposepractice.presentation.components.util.SnackbarController
import com.example.jetpackcomposepractice.presentation.ui.recipe_list.PAGE_SIZE
import com.example.jetpackcomposepractice.presentation.ui.recipe_list.RecipeListEvent
import com.example.jetpackcomposepractice.presentation.ui.recipe_list.RecipeListViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    onChangeRecipeScrollPosition: (Int) -> Unit,
    page: Int,
    onTriggerEvent: (RecipeListEvent) -> Unit,
    scaffoldState: ScaffoldState,
    snackbarController: SnackbarController,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
    ) { // like a frame layout
        if (loading && recipes.isEmpty()) {
            LoadingRecipeListShimmer(imageHeight = 250.dp)
        } else {
            LazyColumn {
                itemsIndexed(items = recipes) { index, recipe ->
                    onChangeRecipeScrollPosition(index)
                    //viewModel.onChangeRecipeScrollPosition(index)
                    if (((index + 1) >= (page * PAGE_SIZE)) && !loading) {
                        //viewModel.onTriggerEvent(RecipeListEvent.NextPageEvent)
                        onTriggerEvent(RecipeListEvent.NextPageEvent)
                        //viewModel.nextPage()
                    }
                    RecipeCard(recipe = recipe) {
                        // OnClick
                        if(recipe.id != null){
                            val bundle = Bundle()
                            bundle.putInt("recipeId", recipe.id)
                            navController.navigate(R.id.action_recipeListFragment_to_recipeFragment, bundle)
                        }else{
                            snackbarController.getScope().launch {
                                snackbarController.showSnackbar(
                                    scaffoldState = scaffoldState,
                                    message = "Recipe Error!",
                                    actionLabel = "OK"
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}