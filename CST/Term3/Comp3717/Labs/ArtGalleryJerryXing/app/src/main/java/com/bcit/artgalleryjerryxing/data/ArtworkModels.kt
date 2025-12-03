package com.bcit.artgalleryjerryxing.data

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Artwork(
    val id: Int,
    val title: String,
    val imageUrl: String?
)

@Entity(tableName = "favourite_artworks")
data class ArtworkEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val imageUrl: String?
)

fun ArtworkEntity.toDomain(): Artwork {
    return Artwork(
        id = id,
        title = title,
        imageUrl = imageUrl
    )
}

fun Artwork.toEntity(): ArtworkEntity {
    return ArtworkEntity(
        id = id,
        title = title,
        imageUrl = imageUrl
    )
}

data class UserProfile(
    val username: String,
    val email: String
)

@Entity(tableName = "user_profile")
data class ProfileEntity(
    @PrimaryKey val id: Int = 0,
    val username: String,
    val email: String
)

fun ProfileEntity.toDomain(): UserProfile {
    return UserProfile(
        username = username,
        email = email
    )
}

fun UserProfile.toEntity(): ProfileEntity {
    return ProfileEntity(
        id = 0,
        username = username,
        email = email
    )
}
