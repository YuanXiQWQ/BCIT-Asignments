package com.bcit.artgalleryjerryxing.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Immutable domain model representing an artwork fetched from the remote API.
 */
data class Artwork(
    val id: Int,
    val title: String,
    val imageUrl: String?
)

/**
 * Room entity storing the user’s favorite artworks.
 */
@Entity(tableName = "favourite_artworks")
data class ArtworkEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val imageUrl: String?
)

/**
 * Converts a persisted artwork entity back into the domain artwork model for UI consumption.
 * @return An [Artwork] instance.
 */
fun ArtworkEntity.toDomain(): Artwork {
    return Artwork(
        id = id,
        title = title,
        imageUrl = imageUrl
    )
}

/**
 * Converts the domain artwork model into a Room entity for persistence.
 * @return An [ArtworkEntity] instance ready for persistence.
 */
fun Artwork.toEntity(): ArtworkEntity {
    return ArtworkEntity(
        id = id,
        title = title,
        imageUrl = imageUrl
    )
}

/**
 * User profile captured within the app for quick personalization.
 */
data class UserProfile(
    val username: String,
    val email: String
)

/**
 * Singleton Room entity mirroring the cached user profile record.
 */
@Entity(tableName = "user_profile")
data class ProfileEntity(
    @PrimaryKey val id: Int = 0,
    val username: String,
    val email: String
)

/**
 * Maps the persisted profile entity into the domain-level user profile.
 * @return A [UserProfile] instance.
 */
fun ProfileEntity.toDomain(): UserProfile {
    return UserProfile(
        username = username,
        email = email
    )
}

/**
 * Normalizes the domain profile into the singleton Room entity used for caching.
 * @return A [ProfileEntity] instance ready for persistence.
 */
fun UserProfile.toEntity(): ProfileEntity {
    return ProfileEntity(
        id = 0,
        username = username,
        email = email
    )
}
