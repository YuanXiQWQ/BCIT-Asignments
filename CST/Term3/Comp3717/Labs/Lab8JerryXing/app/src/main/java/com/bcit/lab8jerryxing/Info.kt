package com.bcit.lab8jerryxing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun Info(color: Long) {
    val hex = java.lang.Long.toHexString(color)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(color)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = hex,
            fontSize = 48.sp
        )

    }
}
