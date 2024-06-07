package com.kotlin.recipebook.ui.presentation.meal

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.kotlin.recipebook.R
import com.kotlin.recipebook.data.models.meals.Ingredient
import com.kotlin.recipebook.data.models.meals.Meal
import com.kotlin.recipebook.ui.presentation.commons.CustomProgressBar
import com.kotlin.recipebook.ui.presentation.commons.ErrorScreen
import com.kotlin.recipebook.ui.theme.RecipeBookAppTheme
import kotlin.math.max
import kotlin.math.min

val AppBarCollapsedHeight = 56.dp
val AppBarExpendedHeight = 200.dp

@Composable
fun RecipeDetailScreen(
    navController: NavController,
    mealViewModel: MealViewModel = hiltViewModel()
) {
    val state by mealViewModel.getMealState.collectAsState()
    when(state) {
        is GetMealState.Loading -> {
            CustomProgressBar()
        }
        is GetMealState.Success -> {
            val scrollState = rememberLazyListState()
            Box {
                RecipeInformation(
                    scrollState = scrollState,
                    meal = (state as GetMealState.Success).meal
                )
                RecipeToolBar(
                    scrollState = scrollState,
                    navController = navController,
                    meal = (state as GetMealState.Success).meal
                )
            }
        }
        is GetMealState.Failure -> {
            ErrorScreen((state as GetMealState.Failure).message) {

            }
        }
    }
}

@Composable
fun RecipeToolBar(
    scrollState: LazyListState,
    navController: NavController,
    meal: Meal
) {

    val imageHeight = AppBarExpendedHeight - AppBarCollapsedHeight

    val maxOffset = with(LocalDensity.current) {
        imageHeight.roundToPx()
    } - LocalWindowInsets.current.systemBars.layoutInsets.top

    val offset = min(scrollState.firstVisibleItemScrollOffset, maxOffset)
    val offsetProgress = max(0f, offset * 3f - 2f * maxOffset) / maxOffset

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


    TopAppBar(
        contentPadding = PaddingValues(),
        backgroundColor = Color.White,
        modifier = Modifier
            .height(AppBarExpendedHeight)
            .offset {
                IntOffset(x = 0, y = -offset)
            },
        elevation = 0.dp
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
                    .graphicsLayer {
                        alpha = 1f - offsetProgress
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = source,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppBarCollapsedHeight),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = meal.name,
                    fontSize = 25.sp,
                    color = Color.Black,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = (16 + 28 * offsetProgress).dp)
                        .scale(1f - 0.25f * offsetProgress)

                )
            }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .statusBarsPadding()
            .height(AppBarCollapsedHeight)
            .padding(horizontal = 16.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = CircleShape
                )
                .padding(3.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp)
                    .padding(3.dp)
                    .clickable {
                        navController.popBackStack()
                    },
                tint = Color.Black
            )
        }
    }
}

@Composable
fun RecipeInformation(
    scrollState: LazyListState,
    meal: Meal
) {
    LazyColumn(contentPadding = PaddingValues(top = AppBarExpendedHeight), state = scrollState) {
        item {
            LazyRow {
               item {
                   RecipeSection(stringResource(id = R.string.label_category), meal.category)
                   RecipeSection(stringResource(id = R.string.label_area), meal.area)
               }
            }
            RecipeInformationDetail(stringResource(id = R.string.label_ingredients), meal.ingredients)
            RecipeDescription(
                meal = meal
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}


@Composable
fun RecipeDescription(meal: Meal) {

    Text(
        text =  meal.instructions,
        modifier = Modifier.padding(10.dp)
    )

   Box(
       modifier = Modifier.fillMaxWidth(),
       contentAlignment = Alignment.Center
   ) {
       VideoButton(meal.video)
   }
}

@Composable
fun VideoButton(
    url: String
) {
    val context = LocalContext.current
    val intent = remember {
        Intent(Intent.ACTION_VIEW, Uri.parse(url))
    }

    Button(
        onClick = {  context.startActivity(intent) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Red
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Play video",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier.padding(5.dp),
                color = Color.White
            )
            Icon(
                Icons.Default.PlayArrow,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Composable
fun RecipeInformationDetail(
    sectionTitle: String,
    list: List<Ingredient>? = null
) {
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        Text(
            text = sectionTitle.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 10.dp)
        )

        list?.forEach { ingredient ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "● ${ingredient.name}",
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(horizontal = 5.dp),
                    color = Color(0XFFB2B2B2)
                )
                Text(
                    text = ingredient.measure,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(horizontal = 5.dp),
                    color = Color(0XFFB2B2B2)
                )
            }
        }

    }
}



@Composable
fun RecipeSection(
    section: String,
    name: String
){
    Box(
        modifier = Modifier
            .padding(15.dp)
    ) {
            Row(
                modifier = Modifier
                    .background(
                        color = Color(0XFF27AE60),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = section,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(5.dp),
                    color = Color.White
                )
                Text(
                    text = "● $name",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(5.dp),
                    color = Color.White
                )
            }
    }

}


@Composable
@Preview(showBackground = true)
fun PreviewMealDetailScreen() {
    RecipeBookAppTheme {
        Surface {
            RecipeDetailScreen(rememberNavController())
        }
    }
}
