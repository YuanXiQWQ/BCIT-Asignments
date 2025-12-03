App name: ArtGalleryJerryXing

Purpose:
A small art gallery app that loads artworks from the public Art Institute of Chicago API and lets the user save favourite artworks locally.

Design:
The app uses a bottom navigation bar with two screens:
1. Online: loads artworks from the API and shows image + title in a scrollable list. Each item has an "Add to favourites" button.
2. Favourites: shows artworks that were saved to the local Room database. Each item has a "Remove from favourites" button.

Coding and separation of concerns:
- Data layer:
  - Room database with ArtworkEntity, ArtworkDao and ArtDatabase
  - ArtworkRepository as the single access point for both the API and the database
- Network layer:
  - Ktor HttpClient with Gson serialization
  - suspend function fetchOnlineArtworks() to call the Art Institute API
- UI layer:
  - ArtworkViewModel as a state holder using viewModelScope and Flow
  - Compose UI with NavigationBar + NavHost and two destinations

Course concepts:
- Week 11: Room, repository pattern, single source of truth
- Week 12: coroutines, suspend functions, Ktor HTTP client, JSON parsing
- Week 13 (optional): ViewModel + Flow + collectAsState in Compose
