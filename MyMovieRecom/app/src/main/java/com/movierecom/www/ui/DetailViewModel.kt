package com.movierecom.www.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movierecom.www.model.DailyBoxOffice
import com.movierecom.www.model.TmdbMovie
import com.movierecom.www.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject
constructor(
    private val repository: MainRepository,
) : ViewModel() {
    private val _trailer = MutableLiveData<String>()
    val trailer: LiveData<String>
        get() = _trailer

    fun getTrailer(query: String) {
        repository.getTrailer(query).onEach {
            _trailer.value = it
        }.launchIn(viewModelScope)
    }
}

