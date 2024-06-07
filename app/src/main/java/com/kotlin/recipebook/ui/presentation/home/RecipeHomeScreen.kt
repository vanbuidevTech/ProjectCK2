package com.kotlin.recipebook.ui.presentation.home

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kotlin.recipebook.R
import com.kotlin.recipebook.data.models.categories.Category
import com.kotlin.recipebook.ui.presentation.commons.CustomProgressBar
import com.kotlin.recipebook.ui.presentation.commons.ErrorScreen
import com.kotlin.recipebook.ui.presentation.commons.SearchView
import com.kotlin.recipebook.ui.presentation.home.components.CardListItem
import com.kotlin.recipebook.ui.presentation.navigation.Screen

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
    homeViewModel: RecipeHomeViewModel = hiltViewModel()
) {
    val list by homeViewModel.categoryList.collectAsState()
    val query by homeViewModel.query.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        RecipeAppBar()
        SearchView(value = query) { homeViewModel.onValueChange(it) }
        Spacer(modifier = Modifier.height(20.dp))
        RecipeCategoryList(
            navController = navController,
            list = list
        )
    }
}


@ExperimentalFoundationApi
@Composable
fun RecipeCategoryList(
    navController: NavController,
    list: List<Category>
){
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
fun RecipeAppBar(){
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
            painter = painterResource(id = R.drawable.mealimage),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp),

            )
    }
}




@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun PreviewRecipeHomeScreen() {
    Surface {
       // RecipeHomeScreen()
    }
}
