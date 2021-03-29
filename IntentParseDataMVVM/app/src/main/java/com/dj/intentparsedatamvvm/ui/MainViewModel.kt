package com.dj.intentparsedatamvvm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dj.intentparsedatamvvm.api.RetrofitInstance
import com.dj.intentparsedatamvvm.data.model.Issue
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _list = MutableLiveData<List<Issue>>()
    val list: LiveData<List<Issue>>
        get() = _list

    fun fetchIssues(username: String, repositoryName: String) {
        viewModelScope.launch {
            val response = RetrofitInstance.retrofit.getIssueList(
                githubUsername = username,
                repositoryName = repositoryName
            )
            _list.postValue(response)
        }
    }

}