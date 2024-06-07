package com.kotlin.recipebook.domain.repositories.meals

import com.kotlin.recipebook.core.Resource
import com.kotlin.recipebook.data.models.meals.Meal
import com.kotlin.recipebook.data.models.meals.MealCategory
import kotlinx.coroutines.flow.Flow

interface MealsRepository {
     fun getMealsByCategory(filter: String): Flow<Resource<List<MealCategory>>>
    suspend fun getMeal(id: String): Flow<Resource<Meal>>
}
