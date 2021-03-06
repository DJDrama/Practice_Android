package com.dj.searchbook.repository

import com.dj.searchbook.data.DataState
import com.dj.searchbook.data.model.Document
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun searchBooks(
        query: String,
        page: Int = 1,
    ): Flow<DataState<List<Document>>>

    fun restoreBooks(
        query: String,
        page: Int
    ): Flow<DataState<List<Document>>>
}
