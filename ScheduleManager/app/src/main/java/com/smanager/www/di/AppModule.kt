package com.smanager.www.di

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.room.Room
import com.smanager.www.R
import com.smanager.www.db.AppDatabase
import com.smanager.www.others.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRunningDatabase(
        @ApplicationContext
        app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "schedule_manager"
    ).build()

    @Singleton
    @Provides
    fun provideEventDao(db: AppDatabase) = db.getEventDao()

}