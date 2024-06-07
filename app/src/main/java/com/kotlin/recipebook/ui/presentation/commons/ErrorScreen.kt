package com.kotlin.recipebook.ui.presentation.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kotlin.recipebook.ui.theme.RecipeBookAppTheme

@Composable
fun ErrorScreen(
    title: String = "Ups!",
    message: String = "",
    onClick: () -> Unit
) {
    val errorColor = Color.Red.copy(alpha = 0.4f)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.4f))

        Box(
            modifier = Modifier.padding(10.dp).background(
                color = errorColor,
                shape = CircleShape
            )
        ) {
            Icon(
                Icons.Filled.Close,
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                tint = Color.White
            )
        }


        Spacer(modifier = Modifier.height(15.dp))
        Text(text = title, style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold, color = errorColor))
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = message, style = MaterialTheme.typography.body1)


        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = errorColor
            ),
            elevation = ButtonDefaults.elevation(
                0.dp
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Try again!",
                modifier = Modifier.padding(5.dp),
                color = Color.White
            )
        }
    }
}

@Composable
@Preview
fun PreviewErrorScreen() {
    RecipeBookAppTheme {
       Surface {
            ErrorScreen {

            }
        }
    }
}
