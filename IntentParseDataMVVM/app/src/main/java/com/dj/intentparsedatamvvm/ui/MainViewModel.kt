package com.dj.intentparsedatamvvm.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dj.intentparsedatamvvm.api.RetrofitInstance
import com.dj.intentparsedatamvvm.data.ListItemHolder
import com.dj.intentparsedatamvvm.other.Constants.IMAGE_URL
import com.dj.intentparsedatamvvm.repository.MainRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel
@Inject
constructor(
    private val repository: MainRepository
) : ViewModel() {
    private val _list = MutableLiveData<List<ListItemHolder>>()
    val list: LiveData<List<ListItemHolder>>
        get() = _list

    init{
    }

    fun fetchIssues(username: String, repositoryName: String) {
        Log.e("check", "check : " + repository)

        viewModelScope.launch {
            val response = repository.fetchIssues(username, repositoryName)
            _list.postValue(response)
        }
    }

}