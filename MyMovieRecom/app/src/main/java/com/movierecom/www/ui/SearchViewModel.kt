package com.movierecom.www.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movierecom.www.model.NaverMovie
import com.movierecom.www.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val repository: MainRepository
) : ViewModel() {
    private val _movieSet = MutableLiveData<Set<NaverMovie>>(mutableSetOf())
    val movieSet: LiveData<Set<NaverMovie>>
        get() = _movieSet

    private var query = ""
    private var page = 1

    fun searchQuery(query: String) {
        if (query.isEmpty() || query.isBlank())
            return
        resetPage()
        this.query = query
        repository.searchQuery(query = query, start = page).onEach {
            _movieSet.value = it.toSet()
        }.launchIn(viewModelScope)
    }

    fun nextQuery() {
        incrementPage()
        repository.searchQuery(query = query, start = page).onEach {
            val tempSet = _movieSet.value as MutableSet
            tempSet.addAll(it)
            _movieSet.value = tempSet
        }.launchIn(viewModelScope)
    }

    private fun resetPage() {
        page = 1
    }

    private fun incrementPage() {
        page += 1
    }
}