package com.example.jetpackcomposepractice.interactors.recipe_list

import android.util.Log
import com.example.jetpackcomposepractice.cache.RecipeDao
import com.example.jetpackcomposepractice.cache.model.RecipeEntityMapper
import com.example.jetpackcomposepractice.domain.data.DataState
import com.example.jetpackcomposepractice.domain.model.Recipe
import com.example.jetpackcomposepractice.util.RECIPE_PAGINATION_PAGE_SIZE
import com.example.jetpackcomposepractice.util.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RestoreRecipes(
    private val recipeDao: RecipeDao,
    private val entityMapper: RecipeEntityMapper
) {
    fun execute(page: Int, query: String): Flow<DataState<List<Recipe>>> = flow {
        try {
            emit(DataState.loading())

            delay(1000)

            val cacheResult = if (query.isBlank()) {
                recipeDao.restoreAllRecipes(
                    pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                    page = page
                )
            } else {
                recipeDao.restoreRecipes(
                    query = query,
                    pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                    page = page
                )
            }
            val list = entityMapper.fromEntityList(cacheResult)
            emit(DataState.success(list))
        } catch (e: Exception) {
            Log.e(TAG, "execute: ${e.message}")
            emit(DataState.error<List<Recipe>>(e.message ?: "Unkown Error"))
        }
    }
}