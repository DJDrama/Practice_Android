package com.example.jetpackcomposepractice.presentation.ui.recipe_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.jetpackcomposepractice.presentation.BaseApplication
import com.example.jetpackcomposepractice.presentation.components.*
import com.example.jetpackcomposepractice.presentation.components.util.SnackbarController
import com.example.jetpackcomposepractice.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalMaterialApi
@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val snackbarController = SnackbarController(lifecycleScope)

    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                /** Snackbar Demo **/
//                // val isShowing = remember { mutableStateOf(false) }
//                val snackbarHostState = remember {
//                    SnackbarHostState()
//                }
//                Column {
//                    Button(
//                        onClick = {
//                            lifecycleScope.launch { // since when view gets destroyed, snackbar will be destroyed also
//                                snackbarHostState.showSnackbar(
//                                    message = "SNackbar Host State Demo",
//                                    actionLabel = "Hide",
//                                    duration = SnackbarDuration.Short
//                                )
//                            }
//
//                            //isShowing.value = true
//                        }
//                    ) {
//                        Text("Show Snackbar")
//                    }
//                    DecoupledSnackbarDemo(snackbarHostState = snackbarHostState)
//
////                    SnackbarDemo(
////                        isShowing = isShowing.value,
////                        onHideSnackbar = {
////                            isShowing.value = false
////                        }
////                    )
//                }

                /** Real **/
                val recipes = viewModel.recipes.value // observable ds
                val query = viewModel.query.value
                val selectedCategory = viewModel.selectedCategory.value
                val loading = viewModel.loading.value
                val page = viewModel.page.value

                val scaffoldState = rememberScaffoldState()

                AppTheme(
                    darkTheme = application.isDark.value,
                    displayProgressBar = loading,
                    scaffoldState = scaffoldState
                ) {


                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = {
                                    if (viewModel.selectedCategory.value?.value == "Milk") {
                                        snackbarController.getScope().launch {
                                            snackbarController.showSnackbar(
                                                scaffoldState = scaffoldState,
                                                message = "Invalid category: Milk!",
                                                actionLabel = "Hide"
                                            )
                                        }
                                    } else {
                                        viewModel.onTriggerEvent(RecipeListEvent.NewSearchEvent)
                                        //viewModel.newSearch()
                                    }
                                },
                                scrollPosition = viewModel.categoryScrollPosition,
                                selectedCategory = selectedCategory,
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                onChangeCategoryScrollPosition = viewModel::onChangeCategoryScrollPosition,
                                onToggleTheme = {
                                    application.toggleLightTheme()
                                }
                            )
                        },
//                        bottomBar = {
//                            MyBottomBar()
//                        },
//                        drawerContent = {
//                            MyDrawer()
//                        }
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }
                    ) {
                        RecipeList(
                            loading = loading,
                            recipes = recipes,
                            onChangeRecipeScrollPosition = { viewModel::onChangeRecipeScrollPosition },
                            page = page,
                            onTriggerEvent = { viewModel.onTriggerEvent(RecipeListEvent.NextPageEvent) },
                            scaffoldState = scaffoldState,
                            snackbarController = snackbarController,
                            navController = findNavController()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MyBottomBar(
    //navigationController: NavController
) {
    BottomNavigation(
        elevation = 12.dp
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Default.BrokenImage) },
            selected = false,
            onClick = {

            })
        BottomNavigationItem(
            icon = { Icon(Icons.Default.Search) },
            selected = false,
            onClick = {

            })
        BottomNavigationItem(
            icon = { Icon(Icons.Default.AccountBalanceWallet) },
            selected = false,
            onClick = {

            })
    }
}

@Composable
fun MyDrawer() {
    Column() {
        Text("Item1")
        Text("Item2")
        Text("Item3")
        Text("Item4")
        Text("Item5")
    }
}

// Snackbar
@Composable
fun SnackbarDemo(
    isShowing: Boolean,
    onHideSnackbar: () -> Unit
) {
    if (isShowing) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val snackbar = createRef()
            Snackbar(
                modifier = Modifier.constrainAs(snackbar) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                action = {
                    Text(
                        text = "Hide",
                        modifier = Modifier.clickable(
                            onClick = onHideSnackbar
                        ),
                        style = MaterialTheme.typography.h5
                    )
                }
            ) {
                Text("Hey Look! This is a Snackbar")
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun DecoupledSnackbarDemo(
    snackbarHostState: SnackbarHostState
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val snackbar = createRef()
        SnackbarHost(
            modifier = Modifier.constrainAs(snackbar) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            hostState = snackbarHostState,
            snackbar = {
                Snackbar(
                    action = {
                        TextButton(
                            onClick = {
                                snackbarHostState.currentSnackbarData?.dismiss()
                            }
                        ) {
                            Text(
                                //text = "Hide",
                                text = snackbarHostState.currentSnackbarData?.actionLabel ?: "Hide",
                                style = TextStyle(color = Color.White)
                            )
                        }
                    }
                ) {
                    //Text("Hey Look! This is a Snackbar")
                    Text(snackbarHostState.currentSnackbarData?.message ?: "")
                }
            }
        )
    }
}