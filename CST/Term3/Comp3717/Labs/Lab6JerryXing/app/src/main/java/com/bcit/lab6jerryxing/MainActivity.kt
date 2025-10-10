package com.bcit.lab6jerryxing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

/**
 * Jerry Xing, A01354731
 */

/**
 * Main Activity
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Layout()
        }
    }
}

/**
 * Layout Composable
 */
@Composable
fun Layout() {
    // Background (idk what this colour is called, gray maybe)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF73849A)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        // Blue and Red
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ){
            // Blue
            Box(
                modifier = Modifier
                    .size(250.dp, 250.dp)
                    .background(Color(0xFF1C2FBC)),
            )
            // Red
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(Color(0xFFA42318))
            )
        }

        // Purple
        Row(
            modifier = Modifier
                .background(Color(0xFF7A55CC))
                .fillMaxWidth()
                .height(150.dp)
                .padding(end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            // Coral
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color(0xFFD48B89))
            )
            // Green
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(Color(0xFF67ad5b))
            )
            // Yellow
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .background(Color(0xFFE7C253))
            )
        }

        // Brown Lab 6
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color(0xFF7A5724)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Lab 6",
                fontSize = 30.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}