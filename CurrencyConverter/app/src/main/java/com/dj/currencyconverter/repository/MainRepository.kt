package com.dj.currencyconverter.repository

import com.dj.currencyconverter.data.models.CurrencyResponse
import com.dj.currencyconverter.util.Resource

interface MainRepository {

    suspend fun getRates(base: String): Resource<CurrencyResponse>

}