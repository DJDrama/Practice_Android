package com.movierecom.www.repository

import com.movierecom.www.api.KobisService
import com.movierecom.www.api.NaverSearchService
import com.movierecom.www.db.KeywordDao
import com.movierecom.www.model.DailyBoxOffice
import com.movierecom.www.model.SearchKeyword
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.*

class MainRepository
constructor(
    private val naverSearchService: NaverSearchService,
    private val keywordDao: KeywordDao,
    private val kobisService: KobisService
) {

    fun getDailyBoxOffice(): Flow<List<DailyBoxOffice>> = flow {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        val cTime = cal.time
        val df = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val formattedDate = df.format(cTime)
        try {
            val response = kobisService.searchMovies(date = formattedDate)
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                val dailyBoxOfficeList = responseBody.boxOfficeResult.dailyBoxOfficeList
                dailyBoxOfficeList.map {
                    val naverResponse =
                        naverSearchService.searchMovies(query = it.movieNm, display = 1)
                    val naverResponseBody = naverResponse.body()
                    if (naverResponse.isSuccessful && naverResponseBody != null) {
                        it.naverMovie = naverResponseBody.items[0]
                    }
                }
                emit(dailyBoxOfficeList)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getKeywordsRank(): Flow<List<String>> = flow {
        try {
            val result = keywordDao.getKeywordsForRank()
            emit(result)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}