package com.kotlin.recipebook.data.models.categories

import com.kotlin.recipebook.data.models.meals.Meal
import com.kotlin.recipebook.data.models.meals.MealCategory
import com.kotlin.recipebook.data.models.meals.MealData

data class CategoryMealResponse (
    val meals: List<MealCategory>
)

