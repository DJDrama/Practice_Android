package com.example.jetpackcomposepractice.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jetpackcomposepractice.cache.RecipeDao
import com.example.jetpackcomposepractice.cache.database.AppDatabase
import com.example.jetpackcomposepractice.cache.database.AppDatabase.Companion.DATABASE_NAME
import com.example.jetpackcomposepractice.cache.model.RecipeEntityMapper
import com.example.jetpackcomposepractice.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(app: BaseApplication): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideRecipeDao(app: AppDatabase): RecipeDao{
        return app.recipeDao()
    }

    @Singleton
    @Provides
    fun provideCacheRecipeMapper(): RecipeEntityMapper {
        return RecipeEntityMapper()
    }
}