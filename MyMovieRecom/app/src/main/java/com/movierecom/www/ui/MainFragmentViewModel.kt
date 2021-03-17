package com.movierecom.www.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movierecom.www.model.DailyBoxOffice
import com.movierecom.www.model.SearchKeyword
import com.movierecom.www.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel
@Inject
constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _dailyBoxOfficeList = MutableLiveData<List<DailyBoxOffice>>(ArrayList())
    val dailyBoxOfficeList: LiveData<List<DailyBoxOffice>>
        get() = _dailyBoxOfficeList

    private val _keywordList = MutableLiveData<List<String>>(ArrayList())
    val keywordList: LiveData<List<String>>
        get() = _keywordList

    init {
        fetchDailyBoxOffice()
    }

    private fun fetchDailyBoxOffice() {
        repository.getDailyBoxOffice().onEach {
            _dailyBoxOfficeList.value = it
        }.launchIn(viewModelScope)
    }

    fun fetchQueryRank() {
        repository.getKeywordsRank().onEach{
            _keywordList.value = it
        }.launchIn(viewModelScope)
    }
}