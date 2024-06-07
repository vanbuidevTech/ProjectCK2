package com.kotlin.recipebook.domain.repositories.meals

import android.util.Log
import com.kotlin.recipebook.core.Resource
import com.kotlin.recipebook.data.models.meals.Meal
import com.kotlin.recipebook.data.models.meals.MealCategory
import com.kotlin.recipebook.data.models.meals.toEmptyMeal
import com.kotlin.recipebook.data.models.meals.toMeal
import com.kotlin.recipebook.data.service.ApiService
import com.kotlin.recipebook.di.CoroutineIODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MealsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    @CoroutineIODispatcher private val coroutineDispatcher: CoroutineDispatcher
): MealsRepository {


    override fun getMealsByCategory(filter: String): Flow<Resource<List<MealCategory>>> = flow{
        emit(Resource.Loading)
        try {
            val data = apiService.getMealsByCategory(filter)
            val response = data.body()

            if (data.isSuccessful && data.code() == 200) {
                response?.let {
                    emit(Resource.Success(it.meals))
                }
            } else {
                emit(Resource.Failure(Exception("We can't load the data ${data.errorBody()}")))
            }

        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }.flowOn(coroutineDispatcher)

    override suspend fun getMeal(id: String): Flow<Resource<Meal>> = flow {
        emit(Resource.Loading)
        try {
            val data = apiService.getMeal(id)
            val response = data.body()
            if (data.isSuccessful && data.code() == 200) {
                response?.let {
                    Log.e("TEST", "getMeal: ${it.meals[0].toMeal()}", )

                    val meal = if (it.meals.isNotEmpty()) {
                        it.meals[0].toMeal()
                    } else {
                        it.meals.toEmptyMeal()
                    }
                    emit(Resource.Success(meal))
                }
            } else {
                emit(Resource.Failure(Exception("We can't load the data ${data.errorBody()}")))
            }

        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }.flowOn(coroutineDispatcher)
}
