package com.merkost.lumi.domain.repositories

import com.merkost.lumi.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface LocalMovieRepository {
    // Movie operations
    suspend fun insertMovie(movie: Movie, isFavorite: Boolean = false, isWatched: Boolean = false, isToWatch: Boolean = false)
    suspend fun getMovieById(movieId: Int): Movie?
    fun getMovieByIdFlow(movieId: Int): Flow<Movie?>
    fun getAllMovies(): Flow<List<Movie>>
    suspend fun deleteMovie(movieId: Int)

    // Favorites
    fun getFavoriteMovies(): Flow<List<Movie>>
    suspend fun toggleFavorite(movieId: Int, isFavorite: Boolean)

    // Watched
    fun getWatchedMovies(): Flow<List<Movie>>
    suspend fun toggleWatched(movieId: Int, isWatched: Boolean)

    // To Watch
    fun getToWatchMovies(): Flow<List<Movie>>
    suspend fun toggleToWatch(movieId: Int, isToWatch: Boolean)

    // Search
    fun searchMovies(query: String): Flow<List<Movie>>
}
