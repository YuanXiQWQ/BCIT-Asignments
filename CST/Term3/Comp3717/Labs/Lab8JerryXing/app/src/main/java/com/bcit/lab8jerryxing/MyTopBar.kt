package com.bcit.lab8jerryxing

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    selectedColor: Long?,
    onHomeClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text("Colors") },
        navigationIcon = {
            IconButton(onClick = onHomeClick) {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = null
                )
            }
        },
        actions = {
            if (selectedColor != null) {
                Icon(
                    Icons.Outlined.Star,
                    contentDescription = null,
                    tint = Color(selectedColor),
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    )
}
