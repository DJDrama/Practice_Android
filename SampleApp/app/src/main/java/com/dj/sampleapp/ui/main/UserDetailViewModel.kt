package com.dj.sampleapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dj.sampleapp.data.DataState
import com.dj.sampleapp.data.model.PopularUser
import com.dj.sampleapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel
@Inject
constructor(
    private val repository: MainRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Empty)
    val uiState: StateFlow<UiState> = _uiState

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int>
        get() = _userId

    fun setUserId(id: Int) {
        _userId.value = id
    }

    fun fetchUserDetails() {
        viewModelScope.launch {
            userId.value?.let {
                repository.fetchUserDetail(it).collect { dataState ->
                    when (dataState) {
                        is DataState.Success -> {
                            dataState.data?.let { popularUser ->
                                _uiState.value = UiState.Success(data = popularUser)
                            }
                        }
                        is DataState.Error -> {
                            dataState.errorMessage?.let { errorMsg ->
                                _uiState.value = UiState.Error(errorMessage = errorMsg)
                            }
                        }
                    }
                }
            }
        }
    }

    fun refresh() {
        _uiState.value = UiState.Empty
        fetchUserDetails()
    }

    sealed class UiState {
        data class Success(val data: PopularUser) : UiState()
        data class Error(val errorMessage: String) : UiState()
        object Empty : UiState()
    }
}