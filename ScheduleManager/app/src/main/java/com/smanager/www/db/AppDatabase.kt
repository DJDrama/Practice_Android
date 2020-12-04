package com.smanager.www.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.smanager.www.model.Event

@Database(
    entities = [Event::class],
    version = 1
)
@TypeConverters(LocalDateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getEventDao(): EventDao
}