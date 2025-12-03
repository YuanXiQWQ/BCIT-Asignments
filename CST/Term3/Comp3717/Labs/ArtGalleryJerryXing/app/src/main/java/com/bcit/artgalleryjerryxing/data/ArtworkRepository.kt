package com.bcit.artgalleryjerryxing.data

import com.google.gson.annotations.SerializedName
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow

class ArtworkRepository(
    private val httpClient: HttpClient,
    private val artworkDao: ArtworkDao,
    private val profileDao: ProfileDao
) {
    suspend fun fetchOnlineArtworks(): List<Artwork> {
        val response: ArtApiResponse = httpClient.get("https://api.artic.edu/api/v1/artworks") {
            parameter("fields", "id,title,image_id")
            parameter("limit", "20")
        }.body()

        return response.data.mapNotNull { item ->
            val title = item.title ?: return@mapNotNull null
            val imageUrl = item.imageId?.let { id ->
                "https://www.artic.edu/iiif/2/$id/full/843,/0/default.jpg"
            }
            Artwork(
                id = item.id,
                title = title,
                imageUrl = imageUrl
            )
        }
    }

    fun getFavouriteArtworks(): Flow<List<ArtworkEntity>> {
        return artworkDao.getAllArtworks()
    }

    suspend fun addFavourite(artwork: Artwork) {
        artworkDao.insertArtwork(artwork.toEntity())
    }

    suspend fun removeFavourite(artwork: Artwork) {
        artworkDao.deleteArtworkById(artwork.id)
    }

    suspend fun getProfile(): UserProfile? {
        val entity = profileDao.getProfile()
        return entity?.toDomain()
    }

    suspend fun saveProfile(profile: UserProfile) {
        profileDao.saveProfile(profile.toEntity())
    }
}

data class ArtApiResponse(
    val data: List<ArtApiItem>
)

data class ArtApiItem(
    val id: Int,
    val title: String?,
    @SerializedName("image_id") val imageId: String?
)
