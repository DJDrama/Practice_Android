package com.dj.dotainfo.di

import com.dj.core.util.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLogger(): Logger{
        return Logger(
            tag = "AppDebug",
            isDebug = true
        )
    }
}