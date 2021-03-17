package com.movierecom.www.ui

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
    private val _movieList = MutableStateFlow<List<NaverMovie>>(ArrayList())
    val movieList: StateFlow<List<NaverMovie>>
        get() = _movieList

    private var page = 1

    fun searchQuery(query: String) {
        if (query.isEmpty() || query.isBlank())
            return
        repository.searchQuery(query = query, start = page).onEach {
            if(page==1)
                _movieList.value = it
            else{ // append
                val tempList = _movieList.value as MutableList
                tempList.addAll(it)
                _movieList.value = tempList
            }
        }.launchIn(viewModelScope)
    }

    fun incrementPage(){
        page+=1
    }
}