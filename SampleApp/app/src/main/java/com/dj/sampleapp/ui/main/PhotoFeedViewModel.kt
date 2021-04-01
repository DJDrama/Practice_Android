package com.dj.sampleapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dj.sampleapp.data.DataState
import com.dj.sampleapp.data.model.PopularCard
import com.dj.sampleapp.other.PAGINATION_SIZE
import com.dj.sampleapp.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoFeedViewModel
@Inject
constructor(
    private val repository: MainRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Empty)
    val uiState: StateFlow<UiState> = _uiState

    private var currentList = mutableListOf<PopularCard>()
    private var page = 1
    private var canLoadMore = true

    init {
        fetchPopularCards()
    }

    private fun fetchPopularCards() {
        viewModelScope.launch {
            if (!canLoadMore || _uiState.value == UiState.Loading)
                return@launch
            _uiState.value = UiState.Loading
            repository.fetchPhotoFeeds(page = page).collect {
                when (it) {
                    is DataState.Success -> {
                        it.data?.let { list ->
                            if (list.size < PAGINATION_SIZE)
                                canLoadMore = false
                            currentList.addAll(list)
                            _uiState.value = UiState.Success(data = currentList)
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

    private fun onNextPopularCards() {
        viewModelScope.launch {
            if (!canLoadMore || _uiState.value == UiState.Loading)
                return@launch
            _uiState.value = UiState.Loading
            repository.fetchPhotoFeeds(page = page).collect {
                when (it) {
                    is DataState.Success -> {
                        it.data?.let { list ->
                            if (list.size < PAGINATION_SIZE)
                                canLoadMore = false
                            currentList.addAll(list)
                            _uiState.value = UiState.Success(data = currentList)
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
        resetPage()
    }

    private fun resetPage() {
        this.page = 1
        currentList.clear()
        canLoadMore = true
        fetchPopularCards()
    }


    fun incrementPage() {
        this.page += 1
        onNextPopularCards()
    }

    sealed class UiState {
        data class Success(val data: List<PopularCard>) : UiState()
        data class Error(val errorMessage: String) : UiState()
        object Loading : UiState()
        object Empty : UiState()
    }
}