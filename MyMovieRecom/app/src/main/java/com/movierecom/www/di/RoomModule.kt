package com.movierecom.www.di

import android.content.Context
import androidx.room.Room
import com.movierecom.www.db.AppDatabase
import com.movierecom.www.db.KeywordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.movierecom.www.db.AppDatabase.Companion.DB_NAME

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
    fun provideKeywordDao(appDb: AppDatabase): KeywordDao {
        return appDb.getKeywordDao()
    }
}