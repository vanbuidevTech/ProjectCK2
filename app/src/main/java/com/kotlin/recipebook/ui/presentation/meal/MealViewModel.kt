package com.kotlin.recipebook.ui.presentation.meal

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.recipebook.core.Resource
import com.kotlin.recipebook.data.models.meals.Meal
import com.kotlin.recipebook.domain.use_cases.meals.GetMealUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(
    private val getMealUseCase: GetMealUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {


    private val _getMealState = MutableStateFlow<GetMealState>(GetMealState.Loading)
    val getMealState: StateFlow<GetMealState> = _getMealState

    init {
        savedStateHandle.get<String>("idMeal")?.let {
            getMeal(it)
        }
    }


    private fun getMeal(id: String) {
        viewModelScope.launch {
            getMealUseCase(id).collect { result ->
                when(result) {
                    is Resource.Failure -> {
                        _getMealState.value = GetMealState.Failure(result.exception.localizedMessage.orEmpty())
                    }
                    is Resource.Loading -> {
                        _getMealState.value = GetMealState.Loading
                    }
                    is Resource.Success -> {
                        _getMealState.value = GetMealState.Success(result.data)
                    }
                }
            }
        }
    }
}

sealed class GetMealState {
    object Loading: GetMealState()
    data class Success(val meal: Meal): GetMealState()
    data class Failure(val message: String): GetMealState()
}
