package com.kotlin.recipebook.ui.presentation.home

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kotlin.recipebook.R
import com.kotlin.recipebook.data.models.categories.Category
import com.kotlin.recipebook.ui.presentation.commons.CustomProgressBar
import com.kotlin.recipebook.ui.presentation.commons.ErrorScreen
import com.kotlin.recipebook.ui.presentation.commons.SearchView
import com.kotlin.recipebook.ui.presentation.home.components.CardListItem
import com.kotlin.recipebook.ui.presentation.navigation.Screen
import com.kotlin.recipebook.ui.theme.RecipeBookAppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalAnimationApi::class)
@ExperimentalFoundationApi
@Composable
fun RecipeHomeScreen(
    navController: NavController,
    homeViewModel: RecipeHomeViewModel = hiltViewModel()
) {
    val categoriesState by homeViewModel.categoriesState.collectAsState()
    when (categoriesState) {
        is GetCategoriesState.Loading -> {
            CustomProgressBar()
        }

        is GetCategoriesState.Success -> {
            RecipeHomeContent(
                navController = navController,
            )
        }

        is GetCategoriesState.Failure -> {
            ErrorScreen((categoriesState as GetCategoriesState.Failure).message) {
                homeViewModel.retryAgain()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipeHomeContent(
    navController: NavController,
    homeViewModel: RecipeHomeViewModel = hiltViewModel(),
    list: List<Category> = emptyList(),
    query: String = ""
) {
    val categoryList by homeViewModel.categoryList.collectAsState(initial = list)
    val searchQuery by homeViewModel.query.collectAsState(initial = query)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(180.dp) // Chiều cao của phần nav
                .background(color = Color(0xFF00A5FF)) // Màu của phần nav
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                RecipeAppBar()
                Spacer(modifier = Modifier.height(20.dp))
                SearchView(value = searchQuery) { homeViewModel.onValueChange(it) }
            }
        }
        // Phần dưới: danh sách category
        Spacer(modifier = Modifier.height(20.dp))
        RecipeCategoryList(
            navController = navController,
            list = categoryList
        )
    }
}


@ExperimentalFoundationApi
@Composable
fun RecipeCategoryList(
    navController: NavController,
    list: List<Category>
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2)
    ) {
        items(list) { item ->
            CardListItem(item) {
                navController.navigate(
                    Screen.CategoryMealsScreen.withArgs(item.name)
                )
            }
        }
    }
}

@Composable
fun RecipeAppBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.label_welcome),
                style = MaterialTheme.typography.h6
            )
            Text(
                text = stringResource(id = R.string.label_cooking),
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
        Image(
            painter = painterResource(id = R.drawable.baseline_menu_24),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp),
        )
    }
}

@ExperimentalFoundationApi
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CategoryPreview() {
    RecipeBookAppTheme {
        val listCategory = listOf(
            Category(id = "1", name = "Meal 1", image = "", description = ""),
            Category(id = "2", name = "Meal 2", image = "", description = ""),
            Category(id = "3", name = "Meal 3", image = "", description = ""),
            Category(id = "4", name = "Meal 4", image = "", description = "")
        )
        val navController = rememberNavController()
        RecipeHomeContent(
            navController = navController,
            list = listCategory,
            query = "Food"
        )
    }
}
