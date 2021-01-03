package com.example.jetpackcomposepractice.presentation.components

import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CircularIndeterminateProgressBar(
    isDisplayed: Boolean
) {
    if (isDisplayed) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val progressBar = createRef()
            val topGuideLine = createGuidelineFromTop(0.3f)
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = Modifier.constrainAs(progressBar){
                    top.linkTo(topGuideLine)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }
    }
}

