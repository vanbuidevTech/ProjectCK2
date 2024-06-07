package com.kotlin.recipebook.domain.use_cases.categories

import com.kotlin.recipebook.core.Resource
import com.kotlin.recipebook.data.models.categories.Category
import com.kotlin.recipebook.domain.repositories.categories.CategoriesRepository
import com.kotlin.recipebook.domain.use_cases.meals.GetMealUseCase
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) {
    operator fun invoke(): Flow<Resource<List<Category>>> = categoriesRepository.getCategories()
}
