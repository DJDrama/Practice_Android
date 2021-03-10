package com.dj.searchbook.repository

import com.dj.searchbook.api.ApiService
import com.dj.searchbook.data.DataState
import com.dj.searchbook.data.model.Document
import com.dj.searchbook.db.DocumentDao
import com.dj.searchbook.util.CANNOT_LOAD_MORE
import com.dj.searchbook.util.PAGINATION_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookRepositoryImpl
@Inject
constructor(
    private val network: ApiService,
    private val documentDao: DocumentDao,
) : BookRepository {

    override fun searchBooks(
        query: String,
        page: Int,
    ): Flow<DataState<List<Document>>> = flow {
        try {
            // network operation
            val response = network.getBookSearchResults(query = query, page = page)
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                val documents = responseBody.documents
                documentDao.insertDocuments(documents = documents)
                if (responseBody.meta.isEnd) {
                    emit(DataState.Error<List<Document>>(errorMessage = CANNOT_LOAD_MORE))
                }
                val cacheResult = documentDao.searchDocuments(query = query, page = page)
                emit(DataState.Success(data = cacheResult))
            }else{
                emit(DataState.Error<List<Document>>(errorMessage = response.errorBody().toString()))
            }
        } catch (e: Exception) {
            if (e.message?.contains("Unable to resolve host \"dapi.kakao.com\"") == true) {
                val cacheResult = documentDao.searchDocuments(query = query, page = page)
                if(cacheResult.size < PAGINATION_SIZE)
                    emit(DataState.Error<List<Document>>(errorMessage = CANNOT_LOAD_MORE))
                emit(DataState.Success(data = cacheResult))
            }else {
                emit(DataState.Error<List<Document>>(errorMessage = e.message ?: "Unkown Error!"))
            }
        }
    }

    override fun restoreBooks(query: String, page: Int): Flow<DataState<List<Document>>> = flow{
        try{
            val cacheResult = documentDao.restoreDocuments(query=query, page = page)
            emit(DataState.Success(data=cacheResult))
        }catch(e: Exception){
            emit(DataState.Error<List<Document>>(errorMessage = e.message ?: "Unkown Error!"))
        }
    }
}
