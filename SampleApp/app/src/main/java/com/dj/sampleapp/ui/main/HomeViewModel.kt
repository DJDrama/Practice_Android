package com.dj.sampleapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dj.sampleapp.data.DataState
import com.dj.sampleapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val repository: MainRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Empty)
    val uiState: StateFlow<UiState> = _uiState

    init {
        fetchHomeData()
    }

    private fun fetchHomeData() {
        viewModelScope.launch {
            repository.fetchHomeData().collect {
                when (it) {
                    is DataState.Success -> {
                        it.data?.let { data ->
                            _uiState.value = UiState.Success(data = data)
                        }
                    }
                    is DataState.Error -> {
                        it.errorMessage?.let { errorMsg ->
                            _uiState.value = UiState.Error(errorMessage = errorMsg)
                        }
                    }
                }
            }
        }
    }

    fun refresh() {
        _uiState.value = UiState.Empty
        fetchHomeData()
    }

    sealed class UiState {
        data class Success(val data: Map<String, List<Any>>) : UiState()
        data class Error(val errorMessage: String) : UiState()
        object Empty : UiState()
    }
}