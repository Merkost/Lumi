package com.merkost.lumi.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.merkost.lumi.data.local.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    // Insert or update movie
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Update
    suspend fun updateMovie(movie: MovieEntity)

    // Get all movies
    @Query("SELECT * FROM movies ORDER BY addedAt DESC")
    fun getAllMovies(): Flow<List<MovieEntity>>

    // Get movie by ID
    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovieById(movieId: Int): MovieEntity?

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieByIdFlow(movieId: Int): Flow<MovieEntity?>

    // Favorites
    @Query("SELECT * FROM movies WHERE isFavorite = 1 ORDER BY addedAt DESC")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("UPDATE movies SET isFavorite = :isFavorite WHERE id = :movieId")
    suspend fun updateFavoriteStatus(movieId: Int, isFavorite: Boolean)

    // Watched
    @Query("SELECT * FROM movies WHERE isWatched = 1 ORDER BY addedAt DESC")
    fun getWatchedMovies(): Flow<List<MovieEntity>>

    @Query("UPDATE movies SET isWatched = :isWatched WHERE id = :movieId")
    suspend fun updateWatchedStatus(movieId: Int, isWatched: Boolean)

    // To Watch
    @Query("SELECT * FROM movies WHERE isToWatch = 1 ORDER BY addedAt DESC")
    fun getToWatchMovies(): Flow<List<MovieEntity>>

    @Query("UPDATE movies SET isToWatch = :isToWatch WHERE id = :movieId")
    suspend fun updateToWatchStatus(movieId: Int, isToWatch: Boolean)

    // Delete movie
    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun deleteMovie(movieId: Int)

    // Clear all movies
    @Query("DELETE FROM movies")
    suspend fun clearAllMovies()

    // Search movies
    @Query("SELECT * FROM movies WHERE title LIKE '%' || :query || '%' ORDER BY addedAt DESC")
    fun searchMovies(query: String): Flow<List<MovieEntity>>
}
