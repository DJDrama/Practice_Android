package com.dj.searchbook.repository

import android.util.Log
import com.dj.searchbook.api.ApiService
import com.dj.searchbook.data.DataState
import com.dj.searchbook.data.model.Document
import com.dj.searchbook.data.response.SearchResponse
import com.dj.searchbook.db.DocumentDao
import com.dj.searchbook.util.CANNOT_LOAD_MORE
import com.dj.searchbook.util.ConnectivityManagerObject
import com.dj.searchbook.util.NO_DATA
import com.dj.searchbook.util.PAGINATION_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class BookRepositoryImpl
@Inject
constructor(
    private val network: ApiService,
    private val connectivityManagerObject: ConnectivityManagerObject,
    private val documentDao: DocumentDao
) : BookRepository {

    override fun searchBooks(query: String, page: Int): Flow<DataState<List<Document>>> = flow {
        try {
            // network operation
            if (connectivityManagerObject.isNetworkAvailable.value) {
                val response = network.getBookSearchResults(query = query, page = page)
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    val documents = responseBody.documents
                    documentDao.insertDocuments(documents = documents)
//                    if (responseBody.meta.isEnd) {
//                        emit(DataState.Error<List<Document>>(errorMessage = CANNOT_LOAD_MORE))
//                    }
                }
            }
            // fetch from cache
            val cacheResult = documentDao.searchDocuments(query = query, page = page)
            if(cacheResult.size< PAGINATION_SIZE){
                emit(DataState.Error<List<Document>>(errorMessage = CANNOT_LOAD_MORE))
            }
            emit(DataState.Success(data = cacheResult))
        } catch (e: Exception) {
            emit(DataState.Error<List<Document>>(errorMessage = e.message ?: "Unkown Error!"))
        }
    }
}
