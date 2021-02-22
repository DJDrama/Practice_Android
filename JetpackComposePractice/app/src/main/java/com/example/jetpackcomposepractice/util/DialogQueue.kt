package com.example.jetpackcomposepractice.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.jetpackcomposepractice.presentation.components.GenericDialogInfo
import com.example.jetpackcomposepractice.presentation.components.PositiveAction
import java.util.*

class DialogQueue {
    val queue: MutableState<Queue<GenericDialogInfo>> = mutableStateOf(LinkedList())

    private fun removeHeadMessage() {
        if (queue.value.isNotEmpty()) {
            val update = queue.value
            update.remove() // remove first

            queue.value = ArrayDeque() // force recompose
            queue.value = update
        }
    }

    fun appendErrorMessage(title: String, description: String) {
        queue.value.offer(
            GenericDialogInfo.Builder()
                .title(title = title)
                .onDismiss(this::removeHeadMessage)
                .description(description = description)
                .positiveAction(PositiveAction(
                    positiveBtnTxt="OK",
                    onPositiveAction = this::removeHeadMessage))
                .build()
        )
    }
}