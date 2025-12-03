package com.bcit.artgalleryjerryxing.data

import com.google.gson.annotations.SerializedName
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow

/**
 * Repository that orchestrates data from the remote API and local Room cache.
 *
 * @param httpClient The Ktor client for making network requests.
 * @param artworkDao The DAO for artwork favourite operations.
 * @param profileDao The DAO for profile cache operations.
 */
class ArtworkRepository(
    private val httpClient: HttpClient,
    private val artworkDao: ArtworkDao,
    private val profileDao: ProfileDao
) {
    /**
     * Fetches paged artwork metadata from the Art Institute of Chicago API.
     *
     * @return A list of [Artwork] domain models.
     */
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

    /**
     * Streams the locally cached favourite artworks from Room.
     *
     * @return A flow emitting the list of favourite artwork entities.
     */
    fun getFavouriteArtworks(): Flow<List<ArtworkEntity>> {
        return artworkDao.getAllArtworks()
    }

    /**
     * Saves an artwork as a favourite in the local database.
     *
     * @param artwork The artwork to mark as a favourite.
     */
    suspend fun addFavourite(artwork: Artwork) {
        artworkDao.insertArtwork(artwork.toEntity())
    }

    /**
     * Removes an artwork from the favourites table.
     *
     * @param artwork The artwork to remove from favourites.
     */
    suspend fun removeFavourite(artwork: Artwork) {
        artworkDao.deleteArtworkById(artwork.id)
    }

    /**
     * Returns the cached profile or null if not yet stored.
     *
     * @return The cached [UserProfile] or null.
     */
    suspend fun getProfile(): UserProfile? {
        val entity = profileDao.getProfile()
        return entity?.toDomain()
    }

    /**
     * Persists the provided profile as the single cached user record.
     *
     * @param profile The user profile to cache.
     */
    suspend fun saveProfile(profile: UserProfile) {
        profileDao.saveProfile(profile.toEntity())
    }
}

/**
 * Top-level response wrapper for the Art Institute of Chicago API.
 *
 * @param data The list of artwork items in the response.
 */
data class ArtApiResponse(
    val data: List<ArtApiItem>
)

/**
 * Raw data transfer object for a single artwork from the API.
 *
 * @param id The unique identifier for the artwork.
 * @param title The title of the artwork.
 * @param imageId The identifier for the artwork's image.
 */
data class ArtApiItem(
    val id: Int,
    val title: String?,
    @SerializedName("image_id") val imageId: String?
)
