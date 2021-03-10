package com.dj.searchbook.di

import android.content.Context
import androidx.room.Room
import com.dj.searchbook.db.AppDatabase
import com.dj.searchbook.db.AppDatabase.Companion.DB_NAME
import com.dj.searchbook.db.DocumentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun provideDocumentDao(db: AppDatabase): DocumentDao {
        return db.getDocumentDao()
    }
}
