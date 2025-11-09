# Lumi - Android Movie App

## Project Overview
Lumi is a modern Android application built with Jetpack Compose that showcases movies using the TMDB (The Movie Database) API. The app follows clean architecture principles and uses modern Android development practices.

## Architecture

### Clean Architecture Layers

The project follows a clean architecture pattern with three main layers:

#### 1. Data Layer (`data/`)
- **api/**: API client implementation using Ktor
  - `MovieDbApi.kt`: Main API interface for TMDB
- **models/**: Data Transfer Objects (DTOs)
  - `MovieDto.kt`: Movie data model from API
  - `MovieDetailsResponse.kt`: Movie details response
  - `MoviesResponse.kt`: Movies list response
  - `ImagesConfigDto.kt`: Image configuration
- **mappers/**: Convert DTOs to domain models
  - `MovieMapper.kt`: Maps API responses to domain models
- **repository/**: Repository implementations
  - `MovieRepositoryImpl.kt`: Implementation of MovieRepository

#### 2. Domain Layer (`domain/`)
- **models/**: Business logic models
  - `Movie.kt`: Core movie model
  - `MovieDetails.kt`: Detailed movie information
  - `Image.kt`: Image model
- **repositories/**: Repository interfaces
  - `MovieRepository.kt`: Defines contract for movie data operations

#### 3. Presentation Layer (`presentation/`)
- **screens/**: UI screens
  - `movies/MoviesScreen.kt`: Popular movies list
  - `movies/MovieDetailsScreen.kt`: Movie details screen
  - `navigation/`: Navigation setup
- **viewmodels/**: ViewModel classes
  - `MoviesViewModel.kt`: Manages movie list state
  - `MovieDetailsViewModel.kt`: Manages movie details state
- **components/**: Reusable UI components
  - Various composable components (Button, Badge, Image, etc.)
- **theme/**: App theming (Color, Theme, Type)
- **base/**: Base classes
  - `ApiResult.kt`: Wrapper for API results
  - `UiState.kt`: UI state management

#### 4. Dependency Injection (`di/`)
- `NetworkModule.kt`: Network dependencies (Ktor client)
- `RepositoryModule.kt`: Repository dependencies
- `ViewModelModule.kt`: ViewModel dependencies

## Technology Stack

### Core Libraries
- **Kotlin** 2.2.21: Latest Kotlin language features
- **Jetpack Compose** (BOM 2025.11.00): Modern declarative UI
- **Material3**: Material Design 3 components
- **Navigation Compose** 2.9.10: Type-safe navigation with animations
  - Kotlin Serialization integration for type safety
  - Smooth slide and fade transitions
  - Proper back stack management

### Dependency Injection
- **Koin** 4.1.1: Lightweight DI framework

### Networking
- **Ktor** 3.3.2: Modern HTTP client
  - ktor-client-android
  - ktor-client-core
  - ktor-client-content-negotiation
  - ktor-client-logging
  - ktor-serialization-kotlinx-json
- **Kotlinx Serialization** 1.9.0: JSON serialization

### Asynchronous Programming
- **Kotlin Coroutines** 1.10.2: Async/await pattern with structured concurrency
- **Lifecycle Runtime KTX** 2.9.4: Lifecycle-aware components

### Image Loading
- **Coil Compose** 2.7.0: Image loading and caching with Compose integration

### Persistence
- **DataStore Preferences** 1.1.7: Modern key-value storage
- **Room** 2.8.3: SQLite database with compile-time verification
  - Type-safe SQL queries
  - Flow-based reactive queries
  - KSP annotation processing

### UI/UX
- **Lottie Compose** 6.7.1: JSON-based animations
- **Material Icons Extended** 1.7.8: Comprehensive icon set

### Testing
- **JUnit** 4.13.2
- **Mockk** 1.13.12: Mocking framework
- **AssertJ** 3.21.0: Fluent assertions
- **Coroutines Test** 1.6.4: Testing coroutines

## Key Features

### Current Features
1. **Popular Movies List**: Displays popular movies from TMDB with grid layout
2. **Movie Details**: Shows detailed information with backdrop images and action buttons
3. **Search Functionality**: Full-text movie search with debounced input (500ms)
4. **Favorites**: Save movies to favorites list with Room database persistence
5. **Watched Movies**: Mark movies as watched and track viewing history
6. **To-Watch List**: Create and manage a watchlist of movies to watch later
7. **Image Loading**: Optimized image loading with Coil and caching
8. **Error Handling**: Proper error states with retry functionality
9. **Loading States**: Elegant loading indicators for async operations
10. **Smooth Animations**: Page transitions with slide and fade effects

### Navigation Architecture
The app uses Navigation Compose 2.9+ with modern best practices:
- **Type-Safe Navigation**: Kotlin serialization for compile-time safety
- **Animated Transitions**:
  - Slide + fade transitions (300ms duration)
  - Different animations for enter/exit and pop operations
  - Custom fade transition for home screen
- **Proper Back Stack Management**: `navigateUp()` for consistent back navigation
- **Launch Modes**: `launchSingleTop` for list screens to prevent duplicates

### Navigation Routes
- `Movies` - Home screen with popular movies
- `MovieDetails(movieId: Int)` - Details screen with parameters
- `Search` - Search screen with real-time results
- `Favorites` - User's favorite movies
- `Watched` - Movies marked as watched
- `ToWatch` - Watchlist of movies to watch later

## Project Structure

```
app/src/main/java/com/merkost/lumi/
├── data/
│   ├── api/
│   │   └── MovieDbApi.kt
│   ├── mappers/
│   │   └── MovieMapper.kt
│   ├── models/
│   │   ├── ImagesConfigDto.kt
│   │   ├── MovieDetailsResponse.kt
│   │   ├── MovieDto.kt
│   │   └── MoviesResponse.kt
│   └── repository/
│       └── MovieRepositoryImpl.kt
├── di/
│   ├── NetworkModule.kt
│   ├── RepositoryModule.kt
│   └── ViewModelModule.kt
├── domain/
│   ├── models/
│   │   ├── Image.kt
│   │   ├── Movie.kt
│   │   └── MovieDetails.kt
│   └── repositories/
│       └── MovieRepository.kt
├── presentation/
│   ├── base/
│   │   ├── ApiResult.kt
│   │   └── UiState.kt
│   ├── components/
│   │   ├── Animation.kt
│   │   ├── Badge.kt
│   │   ├── Button.kt
│   │   ├── ErrorView.kt
│   │   ├── Image.kt
│   │   ├── Lottie.kt
│   │   ├── LumiTopAppBar.kt
│   │   ├── Modifier.kt
│   │   ├── ScreenStateHandler.kt
│   │   └── Text.kt
│   ├── screens/
│   │   ├── movies/
│   │   │   ├── MovieDetailsScreen.kt
│   │   │   └── MoviesScreen.kt
│   │   └── navigation/
│   │       ├── MainNavigation.kt
│   │       └── Navigation.kt
│   ├── theme/
│   │   ├── Color.kt
│   │   ├── Theme.kt
│   │   └── Type.kt
│   ├── viewmodels/
│   │   ├── MovieDetailsViewModel.kt
│   │   └── MoviesViewModel.kt
│   └── MainActivity.kt
└── utils/
```

## Build Configuration

### Gradle Version Catalog (`gradle/libs.versions.toml`)
The project uses Gradle Version Catalog for centralized dependency management.

### API Configuration
The app requires a TMDB API key, configured in `local.properties`:
```
MOVIE_DB_AUTH_TOKEN=your_api_token_here
```

### Build Configuration
- **Min SDK**: 29 (Android 10)
- **Target SDK**: 35
- **Compile SDK**: 35
- **AGP**: 8.7.0-alpha07
- **Java Compatibility**: Java 8

## Development Guidelines

### Code Style
- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Keep functions small and focused
- Use data classes for models

### Architecture Patterns
- **Repository Pattern**: Abstract data sources
- **MVVM**: Separation of UI and business logic
- **Dependency Injection**: Loose coupling with Koin
- **Clean Architecture**: Clear separation of concerns

### State Management
- Use `StateFlow` for reactive state updates
- Handle loading, success, and error states
- Use sealed classes for state representation

### Testing
- Unit tests for ViewModels
- Repository tests with mocked API
- Mapper tests for data transformations

## API Integration

### TMDB API Endpoints Used
- `GET /movie/popular`: Fetch popular movies
- `GET /movie/{id}`: Fetch movie details

### Authentication
- Uses Bearer token authentication
- Token configured in build config

## Future Enhancements

### Immediate Next Steps
1. Add Room database for local persistence
2. Implement search functionality
3. Add favorites/watched/to-watch lists
4. Update dependencies to latest stable versions

### Long-term Goals
- Add movie genres filtering
- Implement pagination for movie lists
- Add movie trailers and videos
- Social sharing features
- Dark/light theme toggle
- Multi-language support

## Resources

- [TMDB API Documentation](https://developer.themoviedb.org/reference/intro/getting-started)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Koin Documentation](https://insert-koin.io/)
- [Ktor Client](https://ktor.io/docs/client.html)

## License
[Add license information]

## Contributors
[Add contributor information]
