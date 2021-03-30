package com.dj.sampleapp.di

import com.dj.sampleapp.api.RetrofitService
import com.dj.sampleapp.repository.AuthRepository
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
    fun provideAuthRepository(
        network: RetrofitService,
    ): AuthRepository {
        return AuthRepository(
            retrofitService = network,
        )
    }

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