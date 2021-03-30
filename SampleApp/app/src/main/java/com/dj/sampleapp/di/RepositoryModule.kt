package com.dj.sampleapp.di

import com.dj.sampleapp.api.RetrofitService
import com.dj.sampleapp.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository(
        network: RetrofitService,
    ): MainRepository {
        return MainRepository(
            retrofitService = network,
        )
    }
}