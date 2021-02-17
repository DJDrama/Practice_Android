package com.example.jetpackcomposepractice.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.viewinterop.viewModel
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetpackcomposepractice.presentation.navigation.Screen
import com.example.jetpackcomposepractice.presentation.ui.recipe.RecipeDetailScreen
import com.example.jetpackcomposepractice.presentation.ui.recipe.RecipeViewModel
import com.example.jetpackcomposepractice.presentation.ui.recipe_list.RecipeListScreen
import com.example.jetpackcomposepractice.presentation.ui.recipe_list.RecipeListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @ExperimentalCoroutinesApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.RecipeList.route) {

                composable(route = Screen.RecipeList.route) { navBackStackEntry ->
                    val factory = HiltViewModelFactory(AmbientContext.current, navBackStackEntry)
                    val viewModel: RecipeListViewModel = viewModel("RecipeList", factory)

                    RecipeListScreen(
                        isDarkTheme = (application as BaseApplication).isDark.value,
                        onToggleTheme = (application as BaseApplication)::toggleLightTheme,
                        viewModel = viewModel

                    )
                }

                composable(route = Screen.RecipeDetail.route) { navBackStackEntry ->
                    val factory = HiltViewModelFactory(AmbientContext.current, navBackStackEntry)
                    val viewModel: RecipeViewModel = viewModel("RecipeDetail", factory)
                    RecipeDetailScreen(
                        isDarkTheme = (application as BaseApplication).isDark.value,
                        recipeId = 1,
                        viewModel = viewModel
                    )
                }
            }
        }
    }

}