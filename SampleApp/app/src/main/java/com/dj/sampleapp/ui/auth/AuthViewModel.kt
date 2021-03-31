package com.dj.sampleapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dj.sampleapp.data.DataState
import com.dj.sampleapp.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject
constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Empty)
    val uiState: StateFlow<UiState>
        get() = _uiState

    fun login(nickname: String, pwd: String) {
        // need to reset for failure attempts
        _uiState.value = UiState.Empty
        viewModelScope.launch {
            authRepository.login(nickname = nickname, pwd = pwd).collect {
                when (it) {
                    is DataState.Success -> {
                        _uiState.value = UiState.Success
                    }
                    is DataState.Error -> {
                        _uiState.value = UiState.Error(it.errorMessage ?: "Unknown Error")
                    }
                }
            }
        }
    }

    fun register(nickname: String, pwd: String, introduction: String) {
        // need to reset for failure attempts
        _uiState.value = UiState.Empty
        viewModelScope.launch {
            authRepository.register(nickname = nickname, pwd = pwd, introduction = introduction)
                .collect {
                    when (it) {
                        is DataState.Success -> {
                            _uiState.value = UiState.Success
                        }
                        is DataState.Error -> {
                            _uiState.value = UiState.Error(it.errorMessage ?: "Unknown Error")
                        }
                    }
                }
        }
    }

    fun logOut(){
        _uiState.value = UiState.Empty
    }


    sealed class UiState {
        object Success : UiState()
        data class Error(val errorMessage: String) : UiState()
        object Empty : UiState()
    }

}
