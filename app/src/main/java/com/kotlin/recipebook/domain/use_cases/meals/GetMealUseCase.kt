package com.kotlin.recipebook.domain.use_cases.meals

import com.kotlin.recipebook.core.Resource
import com.kotlin.recipebook.data.models.meals.Meal
import com.kotlin.recipebook.domain.repositories.meals.MealsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMealUseCase @Inject constructor(
    private val repository: MealsRepository
) {
    suspend operator fun invoke(id: String): Flow<Resource<Meal>> = repository.getMeal(id)
}
