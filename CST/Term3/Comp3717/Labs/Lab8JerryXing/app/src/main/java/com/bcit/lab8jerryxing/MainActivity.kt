package com.bcit.lab8jerryxing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bcit.lab8jerryxing.ui.theme.Lab8JerryXingTheme

/**
 * Jerry Xing, A01354731
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab8JerryXingTheme {
                MainContent()
            }
        }
    }
}
