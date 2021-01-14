package com.example.jetpackcomposepractice.presentation.components.util

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ScaffoldState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SnackbarController(
    private val scope: CoroutineScope
) {
    private var snackbarJob: Job? = null

    init{
        // Just Clean when constructor
        cancelActiveJob()
    }

    fun getScope() = scope

    @ExperimentalMaterialApi
    fun showSnackbar(
        scaffoldState: ScaffoldState,
        message: String,
        actionLabel: String
    ) {
        if(snackbarJob == null){
            snackbarJob = scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = actionLabel
                ) // wait
                cancelActiveJob()
            }
        }else{
            cancelActiveJob()
            snackbarJob = scope.launch{
                scaffoldState.snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = actionLabel
                ) // wait
                cancelActiveJob()
            }
        }
    }
    private fun cancelActiveJob(){
        snackbarJob?.let{ job->
            job.cancel()
            snackbarJob = Job()
        }
    }

}