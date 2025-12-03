package com.bcit.artgalleryjerryxing.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bcit.artgalleryjerryxing.data.Artwork
import com.bcit.artgalleryjerryxing.data.ArtworkRepository
import com.bcit.artgalleryjerryxing.data.UserProfile
import com.bcit.artgalleryjerryxing.data.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/**
 * ViewModel coordinating online artwork loading, favourites, and profile cache state.
 *
 * @param repository The data repository for fetching and persisting artworks.
 */
class ArtworkViewModel(
    private val repository: ArtworkRepository
) : ViewModel() {

    /**
     * Current page of artwork metadata fetched from the Art Institute API.
     */
    var onlineArtworks by mutableStateOf<List<Artwork>>(emptyList())
        private set

    /**
     * Flag tracking whether a network load is in progress.
     */
    var isLoading by mutableStateOf(false)
        private set

    /**
     * User-visible error text triggered when loading fails.
     */
    var errorMessage by mutableStateOf<String?>(null)
        private set

    /**
     * Flow of favourites converted to domain models for the UI.
     */
    val favouriteArtworks: Flow<List<Artwork>> =
        repository.getFavouriteArtworks().map { entities ->
            entities.map { it.toDomain() }
        }

    /**
     * Cached profile username displayed in the profile screen.
     */
    var profileUsername by mutableStateOf("Guest")
        private set

    /**
     * Cached profile email displayed in the profile screen.
     */
    var profileEmail by mutableStateOf("guest@my.bcit.ca")
        private set

    /**
     * Ensures profile is loaded only once per process lifecycle.
     */
    private var isProfileLoaded by mutableStateOf(false)

    /**
     * Initiates fetching artworks from the remote API if none in flight.
     */
    fun loadOnlineArtworks() {
        if (isLoading) {
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                onlineArtworks = repository.fetchOnlineArtworks()
            } catch (_: Exception) {
                errorMessage = "Failed to load artworks."
            } finally {
                isLoading = false
            }
        }
    }

    /**
     * Persists selection as a favourite artwork.
     *
     * @param artwork The artwork to add to user's favourites.
     */
    fun addToFavourites(artwork: Artwork) {
        viewModelScope.launch {
            repository.addFavourite(artwork)
        }
    }

    /**
     * Removes a favourite from local storage.
     *
     * @param artwork The artwork to remove from user's favourites.
     */
    fun removeFromFavourites(artwork: Artwork) {
        viewModelScope.launch {
            repository.removeFavourite(artwork)
        }
    }

    /**
     * Loads the cached profile and updates state for the Profile screen.
     */
    fun loadProfile() {
        if (isProfileLoaded) {
            return
        }

        viewModelScope.launch {
            val profile = repository.getProfile()
            if (profile != null) {
                profileUsername = profile.username
                profileEmail = profile.email
            }
            isProfileLoaded = true
        }
    }

    /**
     * Updates the cached profile values and persists them.
     *
     * @param username The new username to persist.
     * @param email The new email to persist.
     */
    fun saveProfile(username: String, email: String) {
        profileUsername = username
        profileEmail = email
        viewModelScope.launch {
            val profile = UserProfile(username = username, email = email)
            repository.saveProfile(profile)
        }
    }
}

/**
 * Factory for creating [ArtworkViewModel] instances with a repository dependency.
 *
 * @param repository The repository to be injected into the ViewModel.
 */
class ArtworkViewModelFactory(
    private val repository: ArtworkRepository
) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the given `Class`.
     *
     * @param modelClass A `Class` whose instance is requested.
     * @return A newly created ViewModel.
     * @throws IllegalArgumentException if the modelClass is not assignable from [ArtworkViewModel].
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArtworkViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArtworkViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
