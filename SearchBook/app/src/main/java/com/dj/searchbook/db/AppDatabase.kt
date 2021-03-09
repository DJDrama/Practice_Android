package com.dj.searchbook.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dj.searchbook.data.model.Document

@Database(entities = [Document::class], version=1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getDocumentDao(): DocumentDao
    companion object{
        val DB_NAME = "document_db"
    }
}