package com.kotlin.recipebook.data.models.meals

import android.util.Log
import com.google.gson.annotations.SerializedName

data class Meal(
    val id: String,
    val name: String,
    val image: String = "",
    val category: String,
    val area: String,
    val video: String,
    val instructions: String,
    val ingredients: List<Ingredient>
)

data class MealCategory (
    @SerializedName("idMeal")
    val id: String = "",

    @SerializedName("strMeal")
    val name: String = "",

    @SerializedName("strMealThumb")
    val image: String = "",
)


data class MealData (
    @SerializedName("idMeal")
    val id: String = "",

    @SerializedName("strMeal")
    val name: String = "",

    @SerializedName("strMealThumb")
    val image: String = "",

    @SerializedName("strCategory")
    val category: String = "",

    @SerializedName("strArea")
    val area: String = "",

    @SerializedName("strYoutube")
    val video: String = "",

    @SerializedName("strInstructions")
    val instructions: String = "",

    @SerializedName("strIngredient1")
    val ingredient1: String?,
    @SerializedName("strIngredient2")
    val ingredient2: String?,
    @SerializedName("strIngredient3")
    val ingredient3: String?,
    @SerializedName("strIngredient4")
    val ingredient4: String?,
    @SerializedName("strIngredient5")
    val ingredient5: String?,
    @SerializedName("strIngredient6")
    val ingredient6: String?,
    @SerializedName("strIngredient7")
    val ingredient7: String?,
    @SerializedName("strIngredient8")
    val ingredient8: String? ,
    @SerializedName("strIngredient9")
    val ingredient9: String? ,
    @SerializedName("strIngredient10")
    val ingredient10: String? ,
    @SerializedName("strIngredient11")
    val ingredient11: String?,
    @SerializedName("strIngredient12")
    val ingredient12: String?,
    @SerializedName("strIngredient13")
    val ingredient13: String?,
    @SerializedName("strIngredient14")
    val ingredient14: String?,
    @SerializedName("strIngredient15")
    val ingredient15: String?,
    @SerializedName("strIngredient16")
    val ingredient16: String?,
    @SerializedName("strIngredient17")
    val ingredient17: String?,
    @SerializedName("strIngredient18")
    val ingredient18: String?,
    @SerializedName("strIngredient19")
    val ingredient19: String?,
    @SerializedName("strIngredient20")
    val ingredient20: String?,

    @SerializedName("strMeasure1")
    val measure1: String?,
    @SerializedName("strMeasure2")
    val measure2: String?,
    @SerializedName("strMeasure3")
    val measure3: String?,
    @SerializedName("strMeasure4")
    val measure4: String?,
    @SerializedName("strMeasure5")
    val measure5: String?,
    @SerializedName("strMeasure6")
    val measure6: String?,
    @SerializedName("strMeasure7")
    val measure7: String?,
    @SerializedName("strMeasure8")
    val measure8: String?,
    @SerializedName("strMeasure9")
    val measure9: String?,
    @SerializedName("strMeasure10")
    val measure10: String?,
    @SerializedName("strMeasure11")
    val measure11: String?,
    @SerializedName("strMeasure12")
    val measure12: String?,
    @SerializedName("strMeasure13")
    val measure13: String?,
    @SerializedName("strMeasure14")
    val measure14: String?,
    @SerializedName("strMeasure15")
    val measure15: String?,
    @SerializedName("strMeasure16")
    val measure16: String?,
    @SerializedName("strMeasure17")
    val measure17: String?,
    @SerializedName("strMeasure18")
    val measure18: String?,
    @SerializedName("strMeasure19")
    val measure19: String?,
    @SerializedName("strMeasure20")
    val measure20: String?,

)


fun MealData.toMeal(): Meal {
    val ingredients = listOf(
        ingredient1.orEmpty(),
        ingredient2.orEmpty(),
        ingredient3.orEmpty(),
        ingredient4.orEmpty(),
        ingredient5.orEmpty(),
        ingredient6.orEmpty(),
        ingredient7.orEmpty(),
        ingredient8.orEmpty(),
        ingredient9.orEmpty(),
        ingredient10.orEmpty(),
        ingredient11.orEmpty(),
        ingredient12.orEmpty(),
        ingredient13.orEmpty(),
        ingredient14.orEmpty(),
        ingredient15.orEmpty(),
        ingredient16.orEmpty(),
        ingredient17.orEmpty(),
        ingredient18.orEmpty(),
        ingredient19.orEmpty(),
        ingredient20.orEmpty()
    ).filter { it != null && it != "" }


    val measures = listOf(
        measure1.orEmpty(),
        measure2.orEmpty(),
        measure3.orEmpty(),
        measure4.orEmpty(),
        measure5.orEmpty(),
        measure6.orEmpty(),
        measure7.orEmpty(),
        measure8.orEmpty(),
        measure9.orEmpty(),
        measure10.orEmpty(),
        measure11.orEmpty(),
        measure12.orEmpty(),
        measure13.orEmpty(),
        measure14.orEmpty(),
        measure15.orEmpty(),
        measure16.orEmpty(),
        measure17.orEmpty(),
        measure18.orEmpty(),
        measure19.orEmpty(),
        measure20.orEmpty()
    ).filter { it != null && it != "" }



    val list = mutableListOf<Ingredient>()

    for (i in ingredients.indices) {
        val ingredient = Ingredient(ingredients[i], measures[i])
        list.add(ingredient)
    }

    return Meal(
        this.id,
        this.name,
        this.image,
        this.category,
        this.area,
        this.video,
        this.instructions,
        list
    )
}

data class Ingredient(
    val name: String,
    val measure: String
)
