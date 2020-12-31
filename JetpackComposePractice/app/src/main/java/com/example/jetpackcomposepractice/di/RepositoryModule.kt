package com.example.jetpackcomposepractice.di

import com.example.jetpackcomposepractice.network.RecipeService
import com.example.jetpackcomposepractice.network.model.RecipeDtoMapper
import com.example.jetpackcomposepractice.repository.RecipeRepository
import com.example.jetpackcomposepractice.repository.RecipeRepository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeDtoMapper: RecipeDtoMapper
    ): RecipeRepository {
        return RecipeRepository_Impl(
            recipeService = recipeService,
            mapper = recipeDtoMapper
        )
    }

}