package com.dj.sampleapp.data

sealed class DataState<out T>(
    val data: T?,
    val errorMessage: String?,
) {
    class Success<T>(data: T) : DataState<T>(data = data, errorMessage = null)
    class Error<T>(errorMessage: String) : DataState<T>(data = null, errorMessage = errorMessage)
}