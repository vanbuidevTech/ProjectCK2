package com.kotlin.recipebook.di

import com.kotlin.recipebook.data.service.ApiService
import com.kotlin.recipebook.domain.repositories.categories.CategoriesRepository
import com.kotlin.recipebook.domain.repositories.categories.CategoriesRepositoryImpl
import com.kotlin.recipebook.domain.repositories.meals.MealsRepository
import com.kotlin.recipebook.domain.repositories.meals.MealsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent


@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class AppRepositoriesModule {

    @Binds
    abstract fun bindsCategoryRepository(
        categoriesRepositoryImpl: CategoriesRepositoryImpl
    ): CategoriesRepository


    @Binds
    abstract fun bindsMealsRepository(
        mealsRepositoryImpl: MealsRepositoryImpl
    ): MealsRepository

}