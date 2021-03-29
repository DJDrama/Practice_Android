package com.dj.intentparsedatamvvm.repository

import com.dj.intentparsedatamvvm.api.RetrofitService
import com.dj.intentparsedatamvvm.data.ListItemHolder
import com.dj.intentparsedatamvvm.other.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository
@Inject
constructor(private val retrofit: RetrofitService) {

    suspend fun fetchIssues(username: String, repositoryName: String) : List<ListItemHolder> {
        return  retrofit.getIssueList(
            githubUsername = username,
            repositoryName = repositoryName
        ).mapIndexed { index, issue ->
            if (index == 2) {
                ListItemHolder(image = Constants.IMAGE_URL)
            } else {
                ListItemHolder(issue = issue)
            }
        }
    }
}