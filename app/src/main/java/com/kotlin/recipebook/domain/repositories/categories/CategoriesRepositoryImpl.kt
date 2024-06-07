package com.kotlin.recipebook.domain.repositories.categories

import com.kotlin.recipebook.core.Resource
import com.kotlin.recipebook.data.models.categories.Category
import com.kotlin.recipebook.data.service.ApiService
import com.kotlin.recipebook.di.CoroutineIODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
   private val apiService: ApiService,
   @CoroutineIODispatcher private val coroutineDispatcher: CoroutineDispatcher
) : CategoriesRepository {

    override fun getCategories(): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading)
        try {
            val data = apiService.getCategories()
            val response = data.body()

            if (data.isSuccessful && data.code() == 200) {
                response?.let {
                    emit(Resource.Success(it.categories))
                }
            } else {
                emit(Resource.Failure(Exception("We can't load the data ${data.errorBody()}")))
            }

        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }.flowOn(coroutineDispatcher)
}
