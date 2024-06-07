package com.kotlin.recipebook.ui.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.recipebook.core.Resource
import com.kotlin.recipebook.data.models.categories.Category
import com.kotlin.recipebook.domain.use_cases.categories.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecipeHomeViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
): ViewModel() {

    private val _categoriesState = MutableStateFlow<GetCategoriesState>(GetCategoriesState.Loading)
    val categoriesState: StateFlow<GetCategoriesState> = _categoriesState


    private val _categoryList =  MutableStateFlow<List<Category>>(emptyList())
    val categoryList: StateFlow<List<Category>> = _categoryList


    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _originalCategoryList = MutableStateFlow<List<Category>>(emptyList())

    init {
        getCategories()
    }

    private fun getCategories() {
       getCategoriesUseCase()
           .onEach { result ->
               when(result) {
                   is Resource.Loading -> _categoriesState.value = GetCategoriesState.Loading
                   is Resource.Success -> {
                       _categoryList.value = result.data
                       _originalCategoryList.value = result.data
                       _categoriesState.value = GetCategoriesState.Success
                   }
                   is Resource.Failure -> _categoriesState.value = GetCategoriesState.Failure(result.exception.localizedMessage)
               }
           }.catch { exception ->
               _categoriesState.value = GetCategoriesState.Failure(exception.localizedMessage)
           }.stateIn(
               scope = viewModelScope,
               initialValue = Resource.Loading,
               started = SharingStarted.Eagerly
           )
    }

    fun retryAgain() {
        getCategories()
    }


    fun onValueChange(value: String) {
        viewModelScope.launch {
            _query.value = value
            _categoryList.value = _originalCategoryList.value.filter { it.name.contains(value) }
        }
    }


}

sealed class GetCategoriesState {
    object Success: GetCategoriesState()
    object Loading: GetCategoriesState()
    data class Failure(val message: String = ""): GetCategoriesState()
}
