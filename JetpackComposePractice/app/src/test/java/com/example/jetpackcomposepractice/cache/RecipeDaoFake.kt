package com.example.jetpackcomposepractice.cache

import com.example.jetpackcomposepractice.cache.model.RecipeEntity

class RecipeDaoFake(
    private val appDatabaseFake: AppDatabaseFake
): RecipeDao {

    override suspend fun insertRecipe(recipe: RecipeEntity): Long {
        // when trying to test error
//        if(recipe.id == 1){
//            throw Exception("something went wrong")
//        }
        appDatabaseFake.recipes.add(recipe)
        return 1 // return success
    }

    override suspend fun insertRecipes(recipes: List<RecipeEntity>): LongArray {
        appDatabaseFake.recipes.addAll(recipes)
        return longArrayOf(1) // return success
    }

    override suspend fun getRecipeById(id: Int): RecipeEntity? {
        return appDatabaseFake.recipes.find{
            it.id == id
        }
    }

    override suspend fun deleteRecipes(ids: List<Int>): Int {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllRecipes() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRecipe(primaryKey: Int): Int {
        TODO("Not yet implemented")
    }

    override suspend fun searchRecipes(
        query: String,
        page: Int,
        pageSize: Int
    ): List<RecipeEntity> {
        // not filtering, just return a simple list for testing
        return appDatabaseFake.recipes
    }

    override suspend fun getAllRecipes(page: Int, pageSize: Int): List<RecipeEntity> {
        return appDatabaseFake.recipes
    }

    override suspend fun restoreRecipes(
        query: String,
        page: Int,
        pageSize: Int
    ): List<RecipeEntity> {
        return appDatabaseFake.recipes
    }

    override suspend fun restoreAllRecipes(page: Int, pageSize: Int): List<RecipeEntity> {
        return appDatabaseFake.recipes
    }

}