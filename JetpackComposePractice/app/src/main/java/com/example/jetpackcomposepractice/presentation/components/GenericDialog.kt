package com.example.jetpackcomposepractice.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GenericDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    title: String,
    description: String? = null,
    positiveAction: PositiveAction? = null,
    negativeAction: NegativeAction? = null
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            description?.let {
                Text(it)
            }
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                negativeAction?.let {
                    Button(
                        modifier = Modifier.padding(8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.onError),
                        onClick = it.onNegativeAction
                    ) {
                        Text(it.negativeBtnTxt)
                    }
                }
                positiveAction?.let {
                    Button(
                        modifier = Modifier.padding(8.dp),
                        onClick = it.onPositiveAction
                    ) {
                        Text(it.positiveBtnTxt)
                    }
                }
            }
        }
    )
}

data class PositiveAction(
    val positiveBtnTxt: String,
    val onPositiveAction: () -> Unit,
)

data class NegativeAction(
    val negativeBtnTxt: String,
    val onNegativeAction: () -> Unit,
)