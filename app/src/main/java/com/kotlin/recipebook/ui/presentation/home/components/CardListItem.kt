package com.kotlin.recipebook.ui.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.kotlin.recipebook.R
import com.kotlin.recipebook.data.models.categories.Category

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
                ImageRequest.Builder(context)
                    .crossfade(true)
            }
        )
    } else {
        painterResource(id = R.drawable.mealimage)
    }

    Card(
        shape = RoundedCornerShape(5.dp),
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
                .fillMaxWidth(),
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
