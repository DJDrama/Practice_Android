package com.example.jetpackcomposepractice.presentation.components


import android.util.Log
import androidx.compose.animation.transition
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.WithConstraints
import androidx.compose.ui.platform.AmbientDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposepractice.presentation.components.anim_util.ShimmerAnimationDefinition
import com.example.jetpackcomposepractice.presentation.components.anim_util.ShimmerAnimationDefinition.AnimationState.*

@Composable
fun LoadingRecipeShimmer(
    imageHeight: Dp,
    padding: Dp = 16.dp
) {
    WithConstraints {
        val cardWidthPx = with(AmbientDensity.current) {
            ((maxWidth - (padding * 2)).toPx())
        }
        val cardHeightPx = with(AmbientDensity.current) {
            (imageHeight - padding).toPx()
        }
        val cardAnimationDefinition = remember {
            ShimmerAnimationDefinition(
                widthPx = cardWidthPx,
                heightPx = cardHeightPx
            )
        }
        val cardShimmerTranslateAnim = transition(
            definition = cardAnimationDefinition.shimmerTransitionDefinition,
            initState = START,
            toState = END
        )
        val colors = listOf(
            Color.LightGray.copy(alpha = 0.9f),
            Color.LightGray.copy(alpha = 0.2f),
            Color.LightGray.copy(alpha = 0.9f),
        )
        val xCardShimmer = cardShimmerTranslateAnim[cardAnimationDefinition.xShimmerPropKey]
        val yCardShimmer = cardShimmerTranslateAnim[cardAnimationDefinition.yShimmerPropKey]
        Log.d("Shimmer", "LoadingRecipeListShimmer: $xCardShimmer $yCardShimmer")

        ScrollableColumn {
            val brush = Brush.linearGradient(
                colors,
                start = Offset(
                    xCardShimmer - cardAnimationDefinition.gradientWidth,
                    yCardShimmer - cardAnimationDefinition.gradientWidth
                ),
                end = Offset(xCardShimmer, yCardShimmer)
            )

            Column(
                modifier = Modifier.padding(padding)
            ) {
                Surface(shape = MaterialTheme.shapes.small) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .preferredHeight(imageHeight )
                            .background(brush = brush)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Surface(shape = MaterialTheme.shapes.small) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .preferredHeight(imageHeight / 10)
                            .background(brush = brush)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Surface(shape = MaterialTheme.shapes.small) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .preferredHeight(imageHeight / 10)
                            .background(brush = brush)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Surface(shape = MaterialTheme.shapes.small) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .preferredHeight(imageHeight / 10)
                            .background(brush = brush)
                    )
                }
            }
        }
    }

}