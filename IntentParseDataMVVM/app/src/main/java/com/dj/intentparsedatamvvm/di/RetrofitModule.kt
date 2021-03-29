package com.dj.intentparsedatamvvm.di

import com.dj.intentparsedatamvvm.api.RetrofitService
import com.dj.intentparsedatamvvm.other.Constants.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
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
            .baseUrl(BASE_URL)
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit.Builder): RetrofitService {
        return retrofit.build().create(RetrofitService::class.java)
    }
}