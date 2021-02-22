package com.example.jetpackcomposepractice.presentation.ui.recipe_list

import com.example.jetpackcomposepractice.presentation.ui.recipe_list.FoodCategory.*

enum class FoodCategory(val value: String) {
    ERROR(value = "error"),
    CHICKEN(value = "Chicken"),
    BEEF("Beef"),
    SOUP("Soup"),
    DESSERT("Dessert"),
    VEGETARIAN("Vegetarian"),
    MILK("Milk"),
    VEGAN("Vegan"),
    PIZZA("Pizza"),
    DONUT("Donut")
}

fun getAllFoodCategories(): List<FoodCategory> {
    return listOf(
        ERROR,
        CHICKEN,
        BEEF,
        SOUP,
        DESSERT,
        VEGETARIAN,
        MILK,
        VEGAN,
        PIZZA,
        DONUT
    )
}

fun getFoodCategory(value: String): FoodCategory? {
    val map = FoodCategory.values().associateBy(FoodCategory::value)
    return map[value]
}