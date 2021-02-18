package com.example.jetpackcomposepractice.cache

import androidx.room.Dao
import androidx.room.Insert
import com.example.jetpackcomposepractice.cache.model.RecipeEntity

@Dao
interface RecipeDao {
    @Insert
    suspend fun insertRecipe(recipeEntity: RecipeEntity): Long


}