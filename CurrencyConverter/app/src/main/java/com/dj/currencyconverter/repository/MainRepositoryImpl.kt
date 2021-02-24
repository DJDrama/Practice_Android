package com.dj.currencyconverter.repository

import com.dj.currencyconverter.data.CurrencyApi
import com.dj.currencyconverter.data.models.CurrencyResponse
import com.dj.currencyconverter.util.Resource
import javax.inject.Inject

class MainRepositoryImpl
@Inject
constructor(
    private val api: CurrencyApi
) : MainRepository {

    override suspend fun getRates(base: String): Resource<CurrencyResponse> {
        return try {
            val response = api.getRates(base)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(data = result)
            } else {
                Resource.Error(message = response.message())
            }
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "Unknown Error!")
        }
    }

}