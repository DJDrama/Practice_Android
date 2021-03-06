package com.example.jetpackcomposepractice.presentation

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.test.core.app.ActivityScenario.launch
import com.example.jetpackcomposepractice.datastore.SettingsDataStore
import com.example.jetpackcomposepractice.interactors.app.DoesNetworkHaveInternet
import com.example.jetpackcomposepractice.presentation.navigation.Screen
import com.example.jetpackcomposepractice.presentation.ui.recipe.RecipeDetailScreen
import com.example.jetpackcomposepractice.presentation.ui.recipe.RecipeViewModel
import com.example.jetpackcomposepractice.presentation.ui.recipe_list.RecipeListScreen
import com.example.jetpackcomposepractice.presentation.ui.recipe_list.RecipeListViewModel
import com.example.jetpackcomposepractice.presentation.util.ConnectivityManagerObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var settingsDataStore: SettingsDataStore

    @Inject
    lateinit var connectivityManagerObject: ConnectivityManagerObject

    override fun onStart() {
        super.onStart()
        connectivityManagerObject.registerConnectionObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManagerObject.unregisterConnectionObserver(this)
    }

    @ExperimentalComposeUiApi
    @ExperimentalCoroutinesApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.RecipeList.route) {
                composable(route = Screen.RecipeList.route) { navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    val viewModel: RecipeListViewModel = viewModel("RecipeList", factory)

                    RecipeListScreen(
                        isNetworkAvailable = connectivityManagerObject.isNetworkAvailable.value,
                        isDarkTheme = settingsDataStore.isDark.value,
                        onToggleTheme = settingsDataStore::toggleTheme,
                        onNavigateToRecipeDetailScreen = navController::navigate,
                        viewModel = viewModel
                    )
                }

                composable(
                    route = Screen.RecipeDetail.route + "/{recipeId}",
                    arguments = listOf(
                        navArgument("recipeId") {
                            type = NavType.IntType
                        }
                    )
                ) { navBackStackEntry ->
                    val factory = HiltViewModelFactory(LocalContext.current, navBackStackEntry)
                    val viewModel: RecipeViewModel = viewModel("RecipeDetail", factory)
                    RecipeDetailScreen(
                        isNetworkAvailable = connectivityManagerObject.isNetworkAvailable.value,
                        isDarkTheme = settingsDataStore.isDark.value,
                        recipeId = navBackStackEntry.arguments?.getInt("recipeId"),
                        viewModel = viewModel
                    )
                }
            }
        }
    }

}