package com.kotlin.recipebook.domain.use_cases.meals

import com.kotlin.recipebook.core.Resource
import com.kotlin.recipebook.data.models.meals.Meal
import com.kotlin.recipebook.data.models.meals.MealCategory
import com.kotlin.recipebook.domain.repositories.meals.MealsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMealsByCategoryUseCase @Inject constructor(
    private val repository: MealsRepository
) {
    operator fun invoke(filter: String): Flow<Resource<List<MealCategory>>> = repository.getMealsByCategory(filter)
}


