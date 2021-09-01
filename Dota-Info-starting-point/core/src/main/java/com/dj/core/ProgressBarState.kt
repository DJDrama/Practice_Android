package com.dj.core

sealed class ProgressBarState {
    object Loading: ProgressBarState()
    object Idle: ProgressBarState()
}