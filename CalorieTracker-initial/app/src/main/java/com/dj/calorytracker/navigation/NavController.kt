package com.dj.calorytracker.navigation

import androidx.navigation.NavController
import com.dj.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate){
    this.navigate(event.route)
}