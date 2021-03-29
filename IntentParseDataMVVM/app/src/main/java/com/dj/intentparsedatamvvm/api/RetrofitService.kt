package com.dj.intentparsedatamvvm.api

import com.dj.intentparsedatamvvm.data.model.Issue
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("repos/{github_username}/{repository_name}/issues")
    suspend fun getIssueList(
        @Path("github_username") githubUsername: String,
        @Path("repository_name") repositoryName: String
    ): List<Issue>
}