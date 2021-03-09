package com.dj.searchbook.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dj.searchbook.data.DataState
import com.dj.searchbook.data.model.Document
import com.dj.searchbook.repository.BookRepository
import com.dj.searchbook.util.CANNOT_LOAD_MORE
import com.dj.searchbook.util.NO_DATA
import com.dj.searchbook.util.PAGINATION_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchBookViewModel
@Inject
constructor(
    private val repository: BookRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Empty)
    val uiState: StateFlow<SearchUiState>
        get() = _uiState

    private val _documents: MutableStateFlow<List<Document>> = MutableStateFlow(ArrayList())
    private val _query = MutableStateFlow("가나다")

    private var _canLoadMore = true
    private var _page = 1

    init {
        fetchBooks(query = _query.value)
    }

    private fun resetValues(query: String) {
        _canLoadMore = true
        _page = 1
        _query.value = query
    }

    fun fetchBooks(query: String) {
        if (query.isEmpty())
            return
        viewModelScope.launch {
            if (_uiState.value == SearchUiState.Loading) {
                return@launch
            }
            resetValues(query = query)
            _uiState.value = SearchUiState.Loading
            repository.searchBooks(query = query, page = _page).collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        if (dataState.data!!.isEmpty()) {
                            _uiState.value = SearchUiState.Error(errorMessage = NO_DATA)
                        } else {
                            _documents.value = dataState.data
                            _uiState.value = SearchUiState.Success(documents = _documents.value)
                        }
                    }
                    is DataState.Error -> {
                        if (dataState.errorMessage == CANNOT_LOAD_MORE) {
                            _canLoadMore = false
                        } else {
                            _uiState.value = SearchUiState.Error(
                                errorMessage = dataState.errorMessage
                                    ?: "Unknown Error"
                            )
                        }
                    }
                }
            }
        }
    }

    fun loadMore() {
        nextPage(query = _query.value)
    }

    fun nextPage(query: String) {
        viewModelScope.launch {
            if (_canLoadMore) {
                if (_uiState.value == SearchUiState.Loading) {
                    return@launch
                }
                _page += 1
                _uiState.value = SearchUiState.Loading
                repository.searchBooks(query = query, page = _page)
                    .collect { dataState ->
                        when (dataState) {
                            is DataState.Success -> {
                                // append
                                val list = ArrayList(_documents.value)
                                list.addAll(dataState.data!!)
                                _documents.value = list
                                _uiState.value = SearchUiState.Success(documents = _documents.value)
                            }
                            is DataState.Error -> {
                                if (dataState.errorMessage == CANNOT_LOAD_MORE) {
                                    _canLoadMore = false
                                } else {
                                    _uiState.value = SearchUiState.Error(
                                        errorMessage = dataState.errorMessage
                                            ?: "Unknown Error"
                                    )
                                }
                            }
                        }
                    }
            }
        }
    }

    fun setQuery(query: String) {
        _uiState.value = SearchUiState.Search(query = query)
    }



    sealed class SearchUiState {
        class Success(val documents: List<Document>) : SearchUiState()
        class Error(val errorMessage: String) : SearchUiState()

        class Search(val query: String) : SearchUiState()
        class NextPage(val query: String) : SearchUiState()

        object Loading : SearchUiState()
        object Empty : SearchUiState()
    }
}