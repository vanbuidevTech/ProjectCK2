package com.kotlin.recipebook.ui.presentation.home.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.kotlin.recipebook.R
import com.kotlin.recipebook.data.models.categories.Category
import com.kotlin.recipebook.ui.presentation.navigation.Screen
import com.kotlin.recipebook.ui.theme.RecipeBookAppTheme

@Composable
fun CardListItem(
    item: Category,
    onItemClick: () -> Unit
) {
    val context = LocalContext.current
    val source = if (item.image.isNotEmpty()) {
        rememberImagePainter(
            data = item.image,
            builder = {
                crossfade(true)
            }
        )
    } else {
        painterResource(id = R.drawable.mealimage)
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 5.dp,
        modifier = Modifier
            .padding(10.dp)
            .clickable {
                onItemClick()
            }
    ) {
        Box(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .border(
                    width = 1.dp, color = Color(
                        0xFF00A5FF
                    ), shape = RoundedCornerShape(8.dp)
                ),

            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = source,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color.Black.copy(alpha = 0.6f)),
                contentAlignment = Alignment.BottomCenter
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.caption,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
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
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            modifier = Modifier.fillMaxHeight()
        ) {
            items(listCategory) { item ->
                CardListItem(item) {
                    // Handle click event
                }
            }
        }
    }
}
