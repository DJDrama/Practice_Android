package com.example.jetpackcomposepractice.interactors.recipe_list

import com.example.jetpackcomposepractice.cache.RecipeDao
import com.example.jetpackcomposepractice.cache.model.RecipeEntityMapper
import com.example.jetpackcomposepractice.domain.data.DataState
import com.example.jetpackcomposepractice.domain.model.Recipe
import com.example.jetpackcomposepractice.network.RecipeService
import com.example.jetpackcomposepractice.network.model.RecipeDtoMapper
import com.example.jetpackcomposepractice.util.RECIPE_PAGINATION_PAGE_SIZE
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRecipes(
    private val recipeDao: RecipeDao,
    private val recipeService: RecipeService,
    private val entityMapper: RecipeEntityMapper,
    private val dtoMapper: RecipeDtoMapper,
) {
    fun execute(
        token: String,
        page: Int,
        query: String,
    ): Flow<DataState<List<Recipe>>> = flow {
        try {
            emit(DataState.loading<List<Recipe>>()) // true

            // just to show pagination (debug mode)
            delay(1000)

            if(query=="error")
                throw Exception("search Failed!")

            val recipes = getRecipesFromNetwork(token = token, page = page, query = query)

            // insert into the cache
            recipeDao.insertRecipes(entityMapper.toEntityList(recipes))

            // query the cache
            val cacheResult = if (query.isBlank()) {
                recipeDao.getAllRecipes(
                    pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                    page = page
                )
            } else {
                recipeDao.searchRecipes(
                    query = query,
                    pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                    page = page
                )
            }

            // emit List<Recipe>
            val list = entityMapper.fromEntityList(cacheResult)
            emit(DataState.success(list))

        } catch (e: Exception) {
            emit(DataState.error<List<Recipe>>(e.message ?: "Unknown Error"))
        }
    }

    private suspend fun getRecipesFromNetwork(
        token: String,
        page: Int,
        query: String
    ): List<Recipe> {
        val recipeSearchResponse = recipeService.search(
            token = token,
            page = page,
            query = query
        )
        val recipes = recipeSearchResponse.recipes
        return dtoMapper.toDomainList(recipes)
    }
}