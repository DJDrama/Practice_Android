package com.dj.sampleapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
class PopularCardDetailViewModel
@Inject
constructor(
    private val repository: MainRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Empty)
    val uiState: StateFlow<UiState> = _uiState

    private val _cardId = MutableLiveData<Int>()
    val cardId: LiveData<Int>
        get() = _cardId

    fun setCardId(id: Int) {
        _cardId.value = id
    }

    fun fetchCardDetail(id: Int) {
        if (_uiState.value != UiState.Empty)
            return

        viewModelScope.launch {
            repository.fetchCardDetail(id = id).collect {
                when (it) {
                    is DataState.Success -> {
                        it.data?.let { map ->
                            _uiState.value = UiState.Success(data = map)
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
        _cardId.value?.let {
            _uiState.value = UiState.Empty
            fetchCardDetail(it)
        }
    }

    sealed class UiState {
        data class Success(val data: Map<String, Any>) : UiState()
        data class Error(val errorMessage: String) : UiState()
        object Empty : UiState()
    }
}