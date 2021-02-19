package com.example.jetpackcomposepractice.interactors.recipe

import android.util.Log
import com.example.jetpackcomposepractice.cache.RecipeDao
import com.example.jetpackcomposepractice.cache.model.RecipeEntityMapper
import com.example.jetpackcomposepractice.domain.data.DataState
import com.example.jetpackcomposepractice.domain.model.Recipe
import com.example.jetpackcomposepractice.network.RecipeService
import com.example.jetpackcomposepractice.network.model.RecipeDtoMapper
import com.example.jetpackcomposepractice.util.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class GetRecipe(
    private val recipeDao: RecipeDao,
    private val recipeService: RecipeService,
    private val recipeDtoMapper: RecipeDtoMapper,
    private val recipeEntityMapper: RecipeEntityMapper
) {
    fun execute(recipeId: Int, token: String): Flow<DataState<Recipe>> = flow {
        try {
            emit(DataState.loading())
            delay(1000)

            var recipe = getRecipeFromCache(recipeId = recipeId)
            if (recipe != null) {
                emit(DataState.success(recipe))
            } else {
                val networkRecipe = getRecipeFromNetwork(token = token, recipeId = recipeId)
                recipeDao.insertRecipe(recipeEntityMapper.mapFromDomainModel(networkRecipe))
                recipe = getRecipeFromCache(recipeId = recipeId)

                recipe?.let {
                    emit(DataState.success(it))
                } ?: throw Exception("Unable to get the recipe!")
            }

        } catch (e: Exception) {
            Log.e(TAG, "execute: ${e.message}")
            emit(DataState.error<Recipe>(e.message ?: "Unknown Error"))
        }
    }

    private suspend fun getRecipeFromCache(recipeId: Int): Recipe? {
        return recipeDao.getRecipeById(id = recipeId)?.let { recipeEntity ->
            recipeEntityMapper.mapToDomainModel(recipeEntity)
        }
    }

    private suspend fun getRecipeFromNetwork(recipeId: Int, token: String): Recipe {
        val recipeDto = recipeService.get(token = token, id = recipeId)
        return recipeDtoMapper.mapToDomainModel(recipeDto)
    }
}