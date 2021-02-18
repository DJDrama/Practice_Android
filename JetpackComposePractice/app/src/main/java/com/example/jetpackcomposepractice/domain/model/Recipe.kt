package com.example.jetpackcomposepractice.domain.model

import java.util.*

data class Recipe (
    val id: Int,
    val title: String,
    val publisher: String,
    val featuredImage: String,
    val rating: Int = 0,
    val sourceUrl: String,
    val ingredients: List<String> = listOf(),
    val dateAdded: Date,
    val dateUpdated: Date,
)