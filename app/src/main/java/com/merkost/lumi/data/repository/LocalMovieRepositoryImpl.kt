package com.merkost.lumi.data.repository

import com.merkost.lumi.data.local.dao.MovieDao
import com.merkost.lumi.data.mappers.toDomain
import com.merkost.lumi.data.mappers.toEntity
import com.merkost.lumi.domain.models.Movie
import com.merkost.lumi.domain.repositories.LocalMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalMovieRepositoryImpl(
    private val movieDao: MovieDao
) : LocalMovieRepository {

    override suspend fun insertMovie(
        movie: Movie,
        isFavorite: Boolean,
        isWatched: Boolean,
        isToWatch: Boolean
    ) {
        movieDao.insertMovie(
            movie.toEntity(
                isFavorite = isFavorite,
                isWatched = isWatched,
                isToWatch = isToWatch
            )
        )
    }

    override suspend fun getMovieById(movieId: Int): Movie? {
        return movieDao.getMovieById(movieId)?.toDomain()
    }

    override fun getMovieByIdFlow(movieId: Int): Flow<Movie?> {
        return movieDao.getMovieByIdFlow(movieId).map { it?.toDomain() }
    }

    override fun getAllMovies(): Flow<List<Movie>> {
        return movieDao.getAllMovies().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun deleteMovie(movieId: Int) {
        movieDao.deleteMovie(movieId)
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return movieDao.getFavoriteMovies().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun toggleFavorite(movieId: Int, isFavorite: Boolean) {
        movieDao.updateFavoriteStatus(movieId, isFavorite)
    }

    override fun getWatchedMovies(): Flow<List<Movie>> {
        return movieDao.getWatchedMovies().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun toggleWatched(movieId: Int, isWatched: Boolean) {
        movieDao.updateWatchedStatus(movieId, isWatched)
    }

    override fun getToWatchMovies(): Flow<List<Movie>> {
        return movieDao.getToWatchMovies().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun toggleToWatch(movieId: Int, isToWatch: Boolean) {
        movieDao.updateToWatchStatus(movieId, isToWatch)
    }

    override fun searchMovies(query: String): Flow<List<Movie>> {
        return movieDao.searchMovies(query).map { entities ->
            entities.map { it.toDomain() }
        }
    }
}
