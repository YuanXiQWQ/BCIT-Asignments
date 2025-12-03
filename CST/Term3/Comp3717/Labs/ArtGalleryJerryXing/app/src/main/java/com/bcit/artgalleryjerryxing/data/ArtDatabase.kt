package com.bcit.artgalleryjerryxing.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

/**
 * DAO for persisting artwork favourites. Handles inserts, deletes, and collection queries.
 */
@Dao
interface ArtworkDao {
    /**
     * Streams all favourite artworks from the database.
     *
     * @return A flow emitting the list of artwork entities.
     */
    @Query("SELECT * FROM favourite_artworks")
    fun getAllArtworks(): Flow<List<ArtworkEntity>>

    /**
     * Inserts an artwork into the favourites table, replacing it on conflict.
     *
     * @param artwork The entity to insert.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArtwork(artwork: ArtworkEntity)

    /**
     * Deletes a favourite artwork by its unique ID.
     *
     * @param id The primary key of the artwork to delete.
     */
    @Query("DELETE FROM favourite_artworks WHERE id = :id")
    suspend fun deleteArtworkById(id: Int)
}

/**
 * DAO for the user profile table, which currently holds a single cached profile record.
 */
@Dao
interface ProfileDao {
    /**
     * Retrieves the single cached user profile, if it exists.
     *
     * @return The [ProfileEntity] or null if the table is empty.
     */
    @Query("SELECT * FROM user_profile WHERE id = 0 LIMIT 1")
    suspend fun getProfile(): ProfileEntity?

    /**
     * Saves a user profile, replacing the existing one.
     *
     * @param profile The profile entity to save.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProfile(profile: ProfileEntity)
}

/**
 * Room database containing artwork favourites and profile cache tables.
 *
 * The version is bumped as the schema evolves; migrations are added elsewhere when needed.
 */
@Database(
    entities = [ArtworkEntity::class, ProfileEntity::class],
    version = 2
)
abstract class ArtDatabase : RoomDatabase() {
    /**
     * Provides the Data Access Object for artwork favourites.
     *
     * @return The [ArtworkDao] implementation.
     */
    abstract fun artworkDao(): ArtworkDao

    /**
     * Provides the Data Access Object for the user profile.
     *
     * @return The [ProfileDao] implementation.
     */
    abstract fun profileDao(): ProfileDao
}
