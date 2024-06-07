package com.kotlin.recipebook.data.service

import com.kotlin.recipebook.data.models.categories.CategoryMealResponse
import com.kotlin.recipebook.data.models.categories.CategoryResponse
import com.kotlin.recipebook.data.models.meals.MealResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("categories.php")
    suspend fun getCategories(): Response<CategoryResponse>

    @GET("filter.php?")
    suspend fun getMealsByCategory(
        @Query("c") filter: String
    ): Response<CategoryMealResponse>

    @GET("lookup.php?")
    suspend fun getMeal(
        @Query("i") id: String
    ): Response<MealResponse>

}
