package com.dj.onboarding_presentation.weight

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dj.core.domain.preferences.Preferences
import com.dj.core.navigation.Route
import com.dj.core.util.UiEvent
import com.dj.core.util.UiText
import com.dj.onboarding_presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeightViewModel
@Inject
constructor(
    private val preferences: Preferences,
) : ViewModel() {
    var weight by mutableStateOf("80.0")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onWeightEnter(weight: String) {
        if (weight.length <= 5) {
            this.weight = weight
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val weightNumber = weight.toFloatOrNull() ?: run {
                _uiEvent.send(
                    element = UiEvent.ShowSnackBar(
                        message = UiText.StringResource(
                            resId = R.string.error_weight_cant_be_empty
                        )
                    )
                )
                return@launch
            }
            preferences.saveWeight(weight = weightNumber)
            _uiEvent.send(element = UiEvent.Navigate(route = Route.ACTIVITY))
        }
    }
}