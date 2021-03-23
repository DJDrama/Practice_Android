package com.dj.testing.di

import android.content.Context
import androidx.room.Room
import com.dj.testing.data.local.ShoppingDao
import com.dj.testing.data.local.ShoppingItemDatabase
import com.dj.testing.data.remote.PixabayAPI
import com.dj.testing.other.Constants.BASE_URL
import com.dj.testing.other.Constants.DATABASE_NAME
import com.dj.testing.repositories.ShoppingRepository
import com.dj.testing.repositories.ShoppingRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext con: Context
    ) = Room.databaseBuilder(con, ShoppingItemDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideShoppingDao(db: ShoppingItemDatabase) = db.shoppingDao()

    @Singleton
    @Provides
    fun providePixabayApi(): PixabayAPI{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideShoppingRepository(pixabayAPI: PixabayAPI, dao: ShoppingDao): ShoppingRepository{
        return ShoppingRepositoryImpl(pixabayAPI = pixabayAPI, shoppingDao = dao)
    }
}