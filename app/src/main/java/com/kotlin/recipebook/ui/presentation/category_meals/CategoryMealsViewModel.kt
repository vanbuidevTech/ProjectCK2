package com.kotlin.recipebook.ui.presentation.category_meals


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.recipebook.core.Resource
import com.kotlin.recipebook.data.models.meals.Meal
import com.kotlin.recipebook.data.models.meals.MealCategory
import com.kotlin.recipebook.domain.use_cases.meals.GetMealsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class CategoryMealsViewModel @Inject constructor(
    private val getMealsByCategoryUseCase: GetMealsByCategoryUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _categoryMealsState = MutableStateFlow<CategoryMealsState>(CategoryMealsState.Loading)
    val categoryMealsState: StateFlow<CategoryMealsState> = _categoryMealsState


    private val _categoryName = MutableStateFlow("")
    val categoryName: StateFlow<String> = _categoryName

    private val _mealList = MutableStateFlow<List<MealCategory>>(emptyList())
    val mealList: StateFlow<List<MealCategory>> = _mealList

    init {
        savedStateHandle.get<String>("categoryName")?.let {
            _categoryName.value = it
            getMealsByCategory(it)
        }
    }

    private fun getMealsByCategory(filter: String) {
        getMealsByCategoryUseCase(filter)
            .onEach { result ->
                when(result) {
                    Resource.Loading -> {
                        _categoryMealsState.value = CategoryMealsState.Loading
                    }
                    is Resource.Success -> {
                        _mealList.value = result.data
                        _categoryMealsState.value = CategoryMealsState.Success
                    }
                    is Resource.Failure -> {
                        _categoryMealsState.value = CategoryMealsState.Failure(result.exception.localizedMessage)
                    }
                }
            }.launchIn(viewModelScope)
    }
    fun retryAgain() {
        getMealsByCategory(_categoryName.value)
    }

}

sealed class CategoryMealsState {
    object Loading: CategoryMealsState()
    object Success: CategoryMealsState()
    data class Failure(val message: String): CategoryMealsState()
}
