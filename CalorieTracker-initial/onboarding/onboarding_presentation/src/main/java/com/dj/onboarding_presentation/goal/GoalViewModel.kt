package com.dj.onboarding_presentation.goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dj.core.domain.model.ActivityLevel
import com.dj.core.domain.model.GoalType
import com.dj.core.domain.preferences.Preferences
import com.dj.core.navigation.Route
import com.dj.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel
@Inject
constructor(
    private val preferences: Preferences
) : ViewModel() {

    var selectedGoal by mutableStateOf<GoalType>(GoalType.KeepWeight)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onGoalTypeClick(goalType: GoalType) {
        selectedGoal = goalType
    }

    fun onNextClick() {
        viewModelScope.launch {
            preferences.saveGoalType(type = selectedGoal)
            _uiEvent.send(element = UiEvent.Navigate(
                route = Route.NUTRIENT_GOAL
            ))
        }
    }
}