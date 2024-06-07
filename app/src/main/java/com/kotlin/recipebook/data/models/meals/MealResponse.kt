package com.kotlin.recipebook.data.models.meals

data class MealResponse (
    val meals:List<MealData>,
)


fun List<MealData>.toEmptyMeal(): Meal {
    return Meal("","","","","","","", emptyList())
}
