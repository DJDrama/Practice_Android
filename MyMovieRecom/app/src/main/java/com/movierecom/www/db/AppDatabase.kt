package com.movierecom.www.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.movierecom.www.model.SearchKeyword

@Database(entities = [SearchKeyword::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getKeywordDao(): KeywordDao
    companion object{
       const val DB_NAME = "my_movie_recom_db"
    }
}