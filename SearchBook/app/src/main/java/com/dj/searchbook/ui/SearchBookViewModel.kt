package com.dj.searchbook.ui

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
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

const val STATE_KEY_PAGE = "document.state.page.key"
const val STATE_KEY_QUERY = "document.state.query.key"
const val STATE_KEY_SCROLL_STATE = "document.state.scroll_state.key"

@HiltViewModel
class SearchBookViewModel
@Inject
constructor(
    private val repository: BookRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Empty)
    val uiState: StateFlow<SearchUiState>
        get() = _uiState

    private val _documents: MutableStateFlow<List<Document>> = MutableStateFlow(ArrayList())
    private val _query = MutableStateFlow("가나다") // Default

    private var scrollState: Parcelable? = null
    private var canLoadMore = true
    private var page = 1

    init {
        savedStateHandle.apply {
            get<Int>(STATE_KEY_PAGE)?.let {
                setPage(it)
            }
            get<String>(STATE_KEY_QUERY)?.let {
                setQuery(it)
            }
            get<Parcelable>(STATE_KEY_SCROLL_STATE)?.let {
                setLayoutManagerState(it)
            }
        }
        if (scrollState != null && _query.value.isNotEmpty()) {
            restoreDocumentsFromProcessDeath()
        } else {
            fetchBooks(query = _query.value)
        }
    }

    private fun resetValues(query: String) {
        canLoadMore = true
        page = 1

        setQuery(query = query)
    }

    private fun restoreDocumentsFromProcessDeath() {
        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading
            repository.restoreBooks(query = _query.value, page = page).collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        if (dataState.data!!.isEmpty()) {
                            _uiState.value = SearchUiState.Error(errorMessage = NO_DATA)
                        } else {
                            _documents.value = dataState.data
                            _uiState.value = SearchUiState.Restore(state = scrollState,
                                documents = _documents.value)
                        }
                    }
                    is DataState.Error -> {
                        _uiState.value = SearchUiState.Error(
                            errorMessage = dataState.errorMessage
                                ?: "Unknown Error")
                    }
                }
            }
        }
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
            repository.searchBooks(query = query, page = page).collect { dataState ->
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
                            canLoadMore = false
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

    fun nextPage(query: String) {
        viewModelScope.launch {
            if (canLoadMore) {
                if (_uiState.value == SearchUiState.Loading) {
                    return@launch
                }
                incrementPage()
                _uiState.value = SearchUiState.Loading
                repository.searchBooks(query = query, page = page)
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
                                    canLoadMore = false
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

    fun loadMore() {
        nextPage(query = _query.value)
    }

    private fun incrementPage() {
        setPage(page + 1)
    }

    fun setSearchUiState(query: String) {
        _uiState.value = SearchUiState.Search(query = query)
    }

    private fun setQuery(query: String) {
        _query.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }

    private fun setPage(value: Int) {
        page = value
        savedStateHandle.set(STATE_KEY_PAGE, page)
    }

    fun setLayoutManagerState(state: Parcelable?) {
        state?.let {
            scrollState = it
            savedStateHandle.set(STATE_KEY_SCROLL_STATE, scrollState)
        }
    }

    sealed class SearchUiState {
        class Success(val documents: List<Document>) : SearchUiState()
        class Error(val errorMessage: String) : SearchUiState()
        object Loading : SearchUiState()
        class Search(val query: String) : SearchUiState()
        class Restore(val state: Parcelable?, val documents: List<Document>) : SearchUiState()
        object Empty : SearchUiState()
    }
}