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

class ArtworkViewModel(
    private val repository: ArtworkRepository
) : ViewModel() {

    var onlineArtworks by mutableStateOf<List<Artwork>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    val favouriteArtworks: Flow<List<Artwork>> =
        repository.getFavouriteArtworks().map { entities ->
            entities.map { it.toDomain() }
        }

    var profileUsername by mutableStateOf("Guest")
        private set

    var profileEmail by mutableStateOf("guest@my.bcit.ca")
        private set

    private var isProfileLoaded by mutableStateOf(false)

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

    fun addToFavourites(artwork: Artwork) {
        viewModelScope.launch {
            repository.addFavourite(artwork)
        }
    }

    fun removeFromFavourites(artwork: Artwork) {
        viewModelScope.launch {
            repository.removeFavourite(artwork)
        }
    }

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

    fun saveProfile(username: String, email: String) {
        profileUsername = username
        profileEmail = email
        viewModelScope.launch {
            val profile = UserProfile(username = username, email = email)
            repository.saveProfile(profile)
        }
    }
}

class ArtworkViewModelFactory(
    private val repository: ArtworkRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArtworkViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArtworkViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
