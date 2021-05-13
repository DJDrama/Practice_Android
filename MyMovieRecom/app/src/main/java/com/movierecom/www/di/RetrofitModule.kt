package com.movierecom.www.di

import com.movierecom.www.api.KobisService
import com.movierecom.www.api.NaverSearchService
import com.movierecom.www.api.TmdbService
import com.movierecom.www.util.KOBIS_BASE_URL
import com.movierecom.www.util.NAVER_BASE_URL
import com.movierecom.www.util.TMDB_BASE_URL
import com.movierecom.www.util.TMDB_KEY
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideMoshiBuilder(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitModule(moshi: Moshi): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    @Singleton
    @Provides
    fun provideNaverSearchService(retrofit: Retrofit.Builder): NaverSearchService {
        return retrofit.baseUrl(NAVER_BASE_URL)
            .build().create(NaverSearchService::class.java)
    }

    @Singleton
    @Provides
    fun provideKobisService(retrofit: Retrofit.Builder): KobisService {
        return retrofit.baseUrl(KOBIS_BASE_URL).build().create(KobisService::class.java)
    }

    @Singleton
    @Provides
    fun provideTmdbService(retrofit: Retrofit.Builder): TmdbService {
        return retrofit.baseUrl(TMDB_BASE_URL).build().create(TmdbService::class.java)
    }
}