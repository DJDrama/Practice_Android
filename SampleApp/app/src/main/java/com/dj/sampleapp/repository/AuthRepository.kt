package com.dj.sampleapp.repository

import com.dj.sampleapp.api.RetrofitService
import com.dj.sampleapp.data.DataState
import com.dj.sampleapp.data.model.PopularCard
import com.dj.sampleapp.data.model.UserRegistration
import com.dj.sampleapp.other.UNKNOWN_ERROR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepository(
    private val retrofitService: RetrofitService,
) {

    fun login(nickname: String, pwd: String): Flow<DataState<Int>> = flow {
        try {
            val userRegistration = UserRegistration(nickname = nickname, pwd = pwd)
            val response = retrofitService.login(userRegistration = userRegistration)
            if (response.ok) {
                response.userId?.let{userId->
                    emit(DataState.Success(data = userId))
                }
            } else {
                emit(
                    DataState.Error<Int>(
                        errorMessage = response.errorMsg
                            ?: UNKNOWN_ERROR
                    )
                )
            }
        } catch (e: Exception) {
            emit(DataState.Error<Int>(errorMessage = e.message ?: UNKNOWN_ERROR))
        }
    }

    fun register(nickname: String, pwd: String, introduction: String): Flow<DataState<Int>> = flow {
        try {
            val userRegistration =
                UserRegistration(nickname = nickname, pwd = pwd, introduction = introduction)
            val response = retrofitService.registerUser(userRegistration = userRegistration)
            if (response.ok) {
                response.userId?.let { userId ->
                    emit(DataState.Success(data = userId))
                }
            } else {
                emit(
                    DataState.Error<Int>(
                        errorMessage = response.errorMsg
                            ?: UNKNOWN_ERROR
                    )
                )
            }
        } catch (e: Exception) {
            emit(DataState.Error<Int>(errorMessage = e.message ?: UNKNOWN_ERROR))
        }
    }
}