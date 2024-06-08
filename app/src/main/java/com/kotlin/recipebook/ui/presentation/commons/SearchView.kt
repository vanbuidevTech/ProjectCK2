package com.kotlin.recipebook.ui.presentation.commons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchView(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        textStyle = TextStyle(color = Color(0XFFc2c2c2), fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = null,
                tint = Color(0XFFc2c2c2),
                modifier = Modifier.size(24.dp)
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            cursorColor = Color(
                0xFF00A5FF
            ),
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = Color(0XFFEDEDED),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .size(56.dp) // Chiều cao của ô tìm kiếm
            .clip(RoundedCornerShape(48.dp)) // Bo tròn các góc với bán kính 12dp
    )
}
