package com.movierecom.www.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.movierecom.www.model.SearchKeyword

@Dao
interface KeywordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(searchKeyword: SearchKeyword): Long

    @Query("UPDATE search_keywords SET count = count+1 WHERE search_keyword = :searchKeyword")
    suspend fun update(searchKeyword: String)

//    @Query("SELECT search_keyword FROM search_keywords WHERE search_keyword = :searchKeyword ORDER BY count DESC LIMIT 5")
//    fun getSearchKeywords(searchKeyword: String): List<String>

    @Query("SELECT search_keyword FROM search_keywords ORDER BY count DESC LIMIT 5")
    suspend fun getKeywordsForRank(): List<String>
}