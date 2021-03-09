package com.dj.searchbook.di

import com.dj.searchbook.api.ApiService
import com.dj.searchbook.db.DocumentDao
import com.dj.searchbook.repository.BookRepository
import com.dj.searchbook.repository.BookRepositoryImpl
import com.dj.searchbook.util.ConnectivityManagerObject
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
    fun provideBookRepository(
        network: ApiService,
        documentDao: DocumentDao,
        connectivityManagerObject: ConnectivityManagerObject
    ): BookRepository {
        return BookRepositoryImpl(
            network = network,
            connectivityManagerObject = connectivityManagerObject,
            documentDao = documentDao
        )
    }

}