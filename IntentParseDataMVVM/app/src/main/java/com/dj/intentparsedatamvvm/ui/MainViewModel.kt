package com.dj.intentparsedatamvvm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dj.intentparsedatamvvm.api.RetrofitInstance
import com.dj.intentparsedatamvvm.data.ListItemHolder
import com.dj.intentparsedatamvvm.other.Constants.IMAGE_URL
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _list = MutableLiveData<List<ListItemHolder>>()
    val list: LiveData<List<ListItemHolder>>
        get() = _list

    fun fetchIssues(username: String, repositoryName: String) {
        viewModelScope.launch {
            val response = RetrofitInstance.retrofit.getIssueList(
                githubUsername = username,
                repositoryName = repositoryName
            ).mapIndexed { index, issue ->
                if (index == 2) {
                    ListItemHolder(issue = issue, image = IMAGE_URL)
                } else {
                    ListItemHolder(issue = issue)
                }
            }
            _list.postValue(response)
        }
    }

}