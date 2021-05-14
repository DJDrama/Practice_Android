package com.movierecom.www.repository

import android.util.Log
import com.movierecom.www.api.KobisService
import com.movierecom.www.api.NaverSearchService
import com.movierecom.www.api.TmdbService
import com.movierecom.www.db.KeywordDao
import com.movierecom.www.model.DailyBoxOffice
import com.movierecom.www.model.NaverMovie
import com.movierecom.www.model.SearchKeyword
import com.movierecom.www.model.TmdbMovie
import com.movierecom.www.model.TmdbTrailer
import com.movierecom.www.util.PAGINATION_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.*

class MainRepository
constructor(
    private val naverSearchService: NaverSearchService,
    private val keywordDao: KeywordDao,
    private val kobisService: KobisService,
    private val tmdbService: TmdbService,
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

    fun searchQuery(query: String, start: Int): Flow<List<NaverMovie>> = flow {
        try {
            val naverResponse =
                naverSearchService.searchMovies(
                    query = query,
                    start = start,
                    display = PAGINATION_SIZE
                )
            val naverResponseBody = naverResponse.body()
            if (naverResponse.isSuccessful && naverResponseBody != null) {
                val movieList = naverResponseBody.items
                if (start == 1 && movieList.isNotEmpty()) { // put keyword into cache
                    val result = keywordDao.insert(searchKeyword = SearchKeyword(searchKeyword = query))
                    if(result<0){ // Conflict, Update
                        keywordDao.update(searchKeyword = query)
                    }
                }
                // emit result
                emit(movieList)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getTrailer(query: String): Flow<String> = flow{
        try{
            val tmdbMovieResponse = tmdbService.getMovies(query = query)
            val responseBody = tmdbMovieResponse.body()
            if(tmdbMovieResponse.isSuccessful && responseBody!=null){
                val movieList = responseBody.results
                val tmdbTrailerResponse = tmdbService.getTrailer(movieId = movieList[0].id)
                val trailerResponseBody = tmdbTrailerResponse.body()
                if(tmdbTrailerResponse.isSuccessful && trailerResponseBody!=null){
                    emit(trailerResponseBody.results[0].key)
                }
            }
        }catch(e: Exception){
            e.printStackTrace()
        }
    }
}