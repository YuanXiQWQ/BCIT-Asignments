package com.bcit.artgalleryjerryxing

import android.content.Context
import androidx.room.Room
import com.bcit.artgalleryjerryxing.data.ArtDatabase
import com.bcit.artgalleryjerryxing.data.ArtworkRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.gson.gson

class AppContainer(context: Context) {

    private val database: ArtDatabase = Room.databaseBuilder(
        context.applicationContext,
        ArtDatabase::class.java,
        "art_gallery_db"
    ).build()

    private val httpClient: HttpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            gson()
        }
    }

    val artworkRepository: ArtworkRepository =
        ArtworkRepository(httpClient, database.artworkDao(), database.profileDao())
}
