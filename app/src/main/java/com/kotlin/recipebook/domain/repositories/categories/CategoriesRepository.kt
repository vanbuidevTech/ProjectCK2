package com.kotlin.recipebook.domain.repositories.categories

import com.kotlin.recipebook.core.Resource
import com.kotlin.recipebook.data.models.categories.Category
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {
    fun getCategories(): Flow<Resource<List<Category>>>
}
