package com.bcit.artgalleryjerryxing.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.bcit.artgalleryjerryxing.data.Artwork
import kotlinx.coroutines.launch

private sealed class BottomDestination(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    object Online : BottomDestination("online", "Online", Icons.Filled.Home)
    object Favourites : BottomDestination("favourites", "Favourites", Icons.Filled.Star)
    object Profile : BottomDestination("profile", "Profile", Icons.Filled.Person)
}

/**
 * Root UI hosting navigation and bottom bar structure for the gallery experience.
 *
 * @param viewModel The shared ViewModel providing UI state and events.
 */
@Composable
fun ArtGalleryApp(viewModel: ArtworkViewModel) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val destinations = listOf(
        BottomDestination.Online,
        BottomDestination.Favourites,
        BottomDestination.Profile
    )

    val errorMessage = viewModel.errorMessage

    LaunchedEffect(Unit) {
        viewModel.loadOnlineArtworks()
        viewModel.loadProfile()
    }

    LaunchedEffect(errorMessage) {
        if (errorMessage != null) {
            snackbarHostState.showSnackbar(errorMessage)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            NavigationBar {
                destinations.forEach { destination ->
                    NavigationBarItem(
                        selected = currentDestination.isCurrentRoute(destination.route),
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = destination.label
                            )
                        },
                        label = {
                            Text(text = destination.label)
                        }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = BottomDestination.Online.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(BottomDestination.Online.route) {
                OnlineArtScreen(viewModel = viewModel)
            }
            composable(BottomDestination.Favourites.route) {
                FavouritesScreen(viewModel = viewModel)
            }
            composable(BottomDestination.Profile.route) {
                ProfileScreen(
                    viewModel = viewModel,
                    snackbarHostState = snackbarHostState
                )
            }
        }
    }
}

/**
 * Helper for comparing navigation destinations when updating the bottom bar selection.
 *
 * @param route The route string to compare against the current destination.
 * @return True if the destination's route matches the given string.
 */
private fun NavDestination?.isCurrentRoute(route: String): Boolean {
    return this?.route == route
}

/**
 * Displays online artwork fetched from the API and allows adding to favourites.
 *
 * @param viewModel The shared ViewModel providing UI state and events.
 */
@Composable
fun OnlineArtScreen(viewModel: ArtworkViewModel) {
    val artworks = viewModel.onlineArtworks
    val isLoading = viewModel.isLoading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Online artworks",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Data from Art Institute of Chicago API",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
        )

        when {
            isLoading && artworks.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Loading.")
                }
            }

            artworks.isNotEmpty() -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(artworks) { artwork ->
                        ArtworkRow(
                            artwork = artwork,
                            buttonText = "Add to favourites",
                            onButtonClick = { viewModel.addToFavourites(artwork) }
                        )
                    }
                }
            }

            else -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = { viewModel.loadOnlineArtworks() }) {
                        Text(text = "Retry")
                    }
                }
            }
        }
    }
}

/**
 * Shows the user’s saved favourites and lets them remove items.
 *
 * @param viewModel The shared ViewModel providing UI state and events.
 */
@Composable
fun FavouritesScreen(viewModel: ArtworkViewModel) {
    val favourites by viewModel.favouriteArtworks.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "My favourites",
            style = MaterialTheme.typography.headlineMedium
        )

        if (favourites.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No favourites yet.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(favourites) { artwork ->
                    ArtworkRow(
                        artwork = artwork,
                        buttonText = "Remove from favourites",
                        onButtonClick = { viewModel.removeFromFavourites(artwork) }
                    )
                }
            }
        }
    }
}

/**
 * Profile editor that loads cached values and exposes save/cancel actions.
 *
 * @param viewModel The shared ViewModel providing profile state and save actions.
 * @param snackbarHostState The Scaffold's SnackbarHostState for showing confirmation messages.
 */
@Composable
fun ProfileScreen(
    viewModel: ArtworkViewModel,
    snackbarHostState: SnackbarHostState
) {
    val scope = rememberCoroutineScope()
    var isEditing by rememberSaveable { mutableStateOf(false) }
    var editableUsername by rememberSaveable { mutableStateOf("") }
    var editableEmail by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(viewModel.profileUsername, viewModel.profileEmail) {
        if (!isEditing) {
            editableUsername = viewModel.profileUsername
            editableEmail = viewModel.profileEmail
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (!isEditing) {
            Text(
                text = "Username",
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = viewModel.profileUsername,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )
            Text(
                text = "Email",
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = viewModel.profileEmail,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
            )
            Button(
                onClick = { isEditing = true }
            ) {
                Text(text = "Edit")
            }
        } else {
            TextField(
                value = editableUsername,
                onValueChange = { editableUsername = it },
                label = { Text(text = "Username") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                value = editableEmail,
                onValueChange = { editableEmail = it },
                label = { Text(text = "Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    viewModel.saveProfile(editableUsername, editableEmail)
                    isEditing = false
                    scope.launch {
                        snackbarHostState.showSnackbar("Profile saved")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Save")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    isEditing = false
                    editableUsername = viewModel.profileUsername
                    editableEmail = viewModel.profileEmail
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Cancel")
            }
        }
    }
}

/**
 * Reusable card that renders artwork details plus a customizable action button.
 *
 * @param artwork The artwork data to display.
 * @param buttonText The text label for the action button.
 * @param onButtonClick The callback invoked when the action button is clicked.
 */
@Composable
fun ArtworkRow(
    artwork: Artwork,
    buttonText: String,
    onButtonClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable { onButtonClick() }
                .padding(8.dp)
        ) {
            if (artwork.imageUrl != null) {
                AsyncImage(
                    model = artwork.imageUrl,
                    contentDescription = artwork.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
            Text(
                text = artwork.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 8.dp)
            )
            Button(
                onClick = onButtonClick,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(text = buttonText)
            }
        }
    }
}
