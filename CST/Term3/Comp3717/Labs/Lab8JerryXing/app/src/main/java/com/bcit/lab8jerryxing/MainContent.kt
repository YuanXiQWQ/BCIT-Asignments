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
        0xFF9C27B0L,
        0xFF3F51B5L,
        0xFF2196F3L,
        0xFF009688L,
        0xFF4CAF50L,
        0xFFFFEB3BL
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
                        selectedColor = color
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
