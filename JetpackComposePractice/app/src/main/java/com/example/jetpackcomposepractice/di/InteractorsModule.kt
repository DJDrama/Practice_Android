package com.example.jetpackcomposepractice.di

import com.example.jetpackcomposepractice.cache.RecipeDao
import com.example.jetpackcomposepractice.cache.model.RecipeEntityMapper
import com.example.jetpackcomposepractice.interactors.recipe_list.RestoreRecipes
import com.example.jetpackcomposepractice.interactors.recipe_list.SearchRecipes
import com.example.jetpackcomposepractice.network.RecipeService
import com.example.jetpackcomposepractice.network.model.RecipeDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class) // Only exists in ViewModels
object InteractorsModule {

    /** Use Cases **/

    @ViewModelScoped
    @Provides
    fun provideSearchRecipes(
        recipeDao: RecipeDao,
        recipeService: RecipeService,
        dtoMapper: RecipeDtoMapper,
        entityMapper: RecipeEntityMapper
    ): SearchRecipes {
        return SearchRecipes(recipeDao, recipeService, entityMapper, dtoMapper)
    }

    @ViewModelScoped
    @Provides
    fun provideRestoreRecipes(
        recipeDao: RecipeDao,
        entityMapper: RecipeEntityMapper
    ): RestoreRecipes {
        return RestoreRecipes(recipeDao, entityMapper)
    }
}