package com.bcit.artgalleryjerryxing.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtworkDao {
    @Query("SELECT * FROM favourite_artworks")
    fun getAllArtworks(): Flow<List<ArtworkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtwork(artwork: ArtworkEntity)

    @Query("DELETE FROM favourite_artworks WHERE id = :id")
    suspend fun deleteArtworkById(id: Int)
}

@Dao
interface ProfileDao {
    @Query("SELECT * FROM user_profile WHERE id = 0 LIMIT 1")
    suspend fun getProfile(): ProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProfile(profile: ProfileEntity)
}

@Database(
    entities = [ArtworkEntity::class, ProfileEntity::class],
    version = 2
)
abstract class ArtDatabase : RoomDatabase() {
    abstract fun artworkDao(): ArtworkDao
    abstract fun profileDao(): ProfileDao
}
