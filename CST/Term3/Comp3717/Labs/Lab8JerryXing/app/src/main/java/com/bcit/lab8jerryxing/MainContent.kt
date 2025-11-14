package com.bcit.lab8jerryxing

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun MainContent() {
    val navController = rememberNavController()
    val colors = listOf(
        0xFFBD7FFFL,
        0xFF002FA7L,
        0xFF173D68L,
        0xFFF7E34BL,
        0xFFFF0000L,
        0xFF00FF00L,
        0xFF0000FFL,
        0xFFFFFFFFL,
        0xFF000000L,
        0xFF808080L
    )
    var selectedColor by remember { mutableStateOf<Long?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MyTopBar(
                selectedColor = selectedColor,
                onHomeClick = { navController.popBackStack() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                Home(
                    colors = colors,
                    onColorClick = { color ->
                        selectedColor = color
                    },
                    onInfoClick = { color ->
                        navController.navigate("info/$color")
                    }
                )
            }
            composable(
                route = "info/{color}",
                arguments = listOf(
                    navArgument("color") { type = NavType.LongType }
                )
            ) { backStackEntry ->
                val color = backStackEntry.arguments?.getLong("color") ?: 0xFF000000L
                Info(color = color)
            }
        }
    }
}
