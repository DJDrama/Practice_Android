package com.dj.sampleapp.repository

import com.dj.sampleapp.api.RetrofitService
import com.dj.sampleapp.data.DataState
import com.dj.sampleapp.data.model.PopularCard
import com.dj.sampleapp.data.model.PopularUser
import com.dj.sampleapp.other.UNKNOWN_ERROR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository(
    private val retrofitService: RetrofitService,
) {

    fun fetchHomeData(): Flow<DataState<Map<String, List<Any>>>> = flow {
        try {
            val response = retrofitService.getHomeData()
            if (response.ok) {
                val map = mutableMapOf<String, List<Any>>()
                response.popularCards?.let {
                    map["popular_cards"] = it
                }
                response.popularUsers?.let {
                    map["popular_users"] = it
                }
                emit(DataState.Success(data = map))
            } else {
                emit(DataState.Error<Map<String, List<Any>>>(errorMessage = response.msg
                    ?: UNKNOWN_ERROR))
            }
        } catch (e: Exception) {
            emit(DataState.Error<Map<String, List<Any>>>(errorMessage = e.message ?: UNKNOWN_ERROR))
        }
    }

    fun fetchPhotoFeeds(page: Int = 1): Flow<DataState<List<PopularCard>>> = flow {
        try {
            val response = retrofitService.getPhotoFeed(page)
            if (response.ok) {
                emit(DataState.Success(data = response.cards))
            } else {
                emit(DataState.Error<List<PopularCard>>(errorMessage = response.msg
                    ?: UNKNOWN_ERROR))
            }
        } catch (e: Exception) {
            emit(DataState.Error<List<PopularCard>>(errorMessage = e.message ?: UNKNOWN_ERROR))
        }
    }

    fun fetchCardDetail(id: Int): Flow<DataState<Map<String, Any>>> = flow {
        try {
            val response = retrofitService.getCardDetail(id)
            if (response.ok) {
                val map = mutableMapOf<String, Any>()
                map["card"] = response.card
                map["user"] = response.user
                map["recommend_cards"] = response.recommendCards
                emit(DataState.Success(data = map))
            } else {
                emit(DataState.Error<Map<String, Any>>(errorMessage = response.msg
                    ?: UNKNOWN_ERROR))
            }
        } catch (e: Exception) {
            emit(DataState.Error<Map<String, Any>>(errorMessage = e.message ?: UNKNOWN_ERROR))
        }
    }

    fun fetchUserDetail(id: Int): Flow<DataState<PopularUser>> = flow {
        try {
            val response = retrofitService.getUserDetail(id)
            if (response.ok) {
                emit(DataState.Success(data = response.user))
            } else {
                emit(DataState.Error<PopularUser>(errorMessage = response.msg
                    ?: UNKNOWN_ERROR))
            }
        } catch (e: Exception) {
            emit(DataState.Error<PopularUser>(errorMessage = e.message ?: UNKNOWN_ERROR))
        }
    }
}