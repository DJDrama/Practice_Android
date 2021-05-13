package com.movierecom.www.di

import com.movierecom.www.api.KobisService
import com.movierecom.www.api.NaverSearchService
import com.movierecom.www.api.TmdbService
import com.movierecom.www.db.KeywordDao
import com.movierecom.www.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideMainRepository(
        naverSearchService: NaverSearchService,
        keywordDao: KeywordDao,
        kobisService: KobisService,
        tmdbService: TmdbService
    ): MainRepository {
        return MainRepository(
            naverSearchService = naverSearchService,
            keywordDao = keywordDao,
            kobisService = kobisService,
            tmdbService = tmdbService
        )
    }
}