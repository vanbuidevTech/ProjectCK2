package com.kotlin.recipebook.ui.presentation.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kotlin.recipebook.ui.presentation.meal.RecipeDetailScreen
import com.kotlin.recipebook.ui.presentation.category_meals.CategoryMealsScreen
import com.kotlin.recipebook.ui.presentation.home.RecipeHomeScreen


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.RecipeHomeScreen.route) {
        composable(route = Screen.RecipeHomeScreen.route) {
            RecipeHomeScreen(
                navController = navController
            )
        }
        composable(route = Screen.RecipeDetailScreen.route + "/{idMeal}") {
            RecipeDetailScreen(
                navController = navController
            )
        }
        composable(route = Screen.CategoryMealsScreen.route + "/{categoryName}") {
            CategoryMealsScreen(
                navController = navController
            )
        }
    }
}



sealed class Screen(val route: String){
    object RecipeHomeScreen: Screen("recipe_home_screen")
    object RecipeDetailScreen: Screen("recipe_detail_screen")
    object CategoryMealsScreen: Screen("categories_meals_screen")

    fun withArgs(vararg args: String) = buildString {
        append(route)
        args.forEach { arg ->
            append("/$arg")
        }
    }
}
