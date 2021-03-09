package com.dj.searchbook.repository

import com.dj.searchbook.data.DataState
import com.dj.searchbook.data.model.Document
import com.dj.searchbook.data.response.SearchResponse
import kotlinx.coroutines.flow.Flow


interface BookRepository {
    fun searchBooks(query: String, page: Int=1): Flow<DataState<List<Document>>>
}