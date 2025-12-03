package com.bcit.artgalleryjerryxing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.bcit.artgalleryjerryxing.ui.ArtGalleryApp
import com.bcit.artgalleryjerryxing.ui.ArtworkViewModel
import com.bcit.artgalleryjerryxing.ui.ArtworkViewModelFactory
import com.bcit.artgalleryjerryxing.ui.theme.ArtGalleryJerryXingTheme

class MainActivity : ComponentActivity() {

    private val appContainer: AppContainer by lazy {
        AppContainer(applicationContext)
    }

    private val artworkViewModel: ArtworkViewModel by viewModels {
        ArtworkViewModelFactory(appContainer.artworkRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtGalleryJerryXingTheme {
                ArtGalleryApp(artworkViewModel)
            }
        }
    }
}
