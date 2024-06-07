package com.kotlin.recipebook.ui.presentation.category_meals

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.kotlin.recipebook.R
import com.kotlin.recipebook.data.models.meals.Meal
import com.kotlin.recipebook.data.models.meals.MealCategory
import com.kotlin.recipebook.ui.presentation.commons.CustomProgressBar
import com.kotlin.recipebook.ui.presentation.commons.ErrorScreen
import com.kotlin.recipebook.ui.presentation.navigation.Screen

@Composable
fun CategoryMealsScreen(
    navController: NavController,
    viewModel: CategoryMealsViewModel = hiltViewModel()
) {
    val state by viewModel.categoryMealsState.collectAsState()
    when(state) {
        is CategoryMealsState.Loading -> CustomProgressBar()
        is CategoryMealsState.Success -> {
            CategoryMealsContent(
                navController = navController
            )
        }
        is CategoryMealsState.Failure -> ErrorScreen((state as CategoryMealsState.Failure).message) {
          viewModel.retryAgain()
        }
    }
}

@Composable
fun CategoryMealsContent(
    navController: NavController,
    viewModel: CategoryMealsViewModel = hiltViewModel()
) {
    val list by viewModel.mealList.collectAsState()
    val categoryName by viewModel.categoryName.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .padding(10.dp)
                    .size(25.dp)
                    .clickable {
                        navController.popBackStack()
                    }
            )
            Text(
                text = categoryName,
                style = MaterialTheme.typography.h5
            )
        }

        LazyColumn {
            items(list) { meal ->
                CardCategoryMealListItem(meal) {
                    navController.navigate(Screen.RecipeDetailScreen.withArgs(meal.id))
                }
            }
        }

    }
}

@Composable
fun CardCategoryMealListItem(
    meal: MealCategory,
    onClick:() -> Unit,
) {
    val context = LocalContext.current
    val source = if (meal.image.isNotEmpty()) {
        rememberImagePainter(
            data = meal.image,
            builder = {
                ImageRequest.Builder(context)
                    .crossfade(true)
            }
        )
    } else {
        painterResource(id = R.drawable.mealimage)
    }
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = source,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp)
            )

            Column(
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = meal.name,
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            }
        }
    }
}



@Composable
@Preview(showBackground = true)
fun PreviewCategoryMealsScreen(){
    CategoryMealsScreen(rememberNavController())
}
