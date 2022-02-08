package com.dj.onboarding_presentation.age

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dj.core.domain.preferences.Preferences
import com.dj.core.domain.use_case.FilterOutDigits
import com.dj.core.util.UiEvent
import com.dj.core.util.UiText
import com.dj.onboarding_presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgeViewModel
@Inject
constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {
    var age by mutableStateOf("20")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAgeEnter(age: String) {
        if (age.length <= 3) {
            this.age = filterOutDigits(age)
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val ageNumber = age.toIntOrNull() ?: run {
                _uiEvent.send(
                    element = UiEvent.ShowSnackBar(
                        message = UiText.StringResource(
                            resId = R.string.error_age_cant_be_empty
                        )
                    )
                )
                return@launch
            }
            preferences.saveAge(age = ageNumber)
            _uiEvent.send(element = UiEvent.Success)
        }
    }
}