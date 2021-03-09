package com.dj.searchbook.di

import android.content.Context
import com.dj.searchbook.BaseApplication
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
    fun provideApp(app: Context): BaseApplication {
        return app as BaseApplication
    }
}