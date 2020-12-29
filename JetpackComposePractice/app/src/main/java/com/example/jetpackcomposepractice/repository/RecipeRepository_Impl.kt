package com.example.jetpackcomposepractice.repository

import com.example.jetpackcomposepractice.domain.model.Recipe
import com.example.jetpackcomposepractice.network.RecipeService
import com.example.jetpackcomposepractice.network.model.RecipeDtoMapper

class RecipeRepository_Impl(
    private val recipeService: RecipeService,
    private val mapper: RecipeDtoMapper
) : RecipeRepository {
    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        return mapper.toDomainList(recipeService.search(token, page, query).recipes)
    }

    override suspend fun get(token: String, id: Int): Recipe {
        return mapper.mapToDomainModel(recipeService.get(token, id))
    }

}