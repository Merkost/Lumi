package com.merkost.lumi.data.repository

import com.merkost.lumi.data.api.MovieDbApi
import com.merkost.lumi.data.mappers.mapApiToDomain
import com.merkost.lumi.domain.models.Movie
import com.merkost.lumi.domain.models.MovieDetails
import com.merkost.lumi.domain.repositories.MovieRepository
import com.merkost.lumi.utils.ApiResult
import com.merkost.lumi.utils.safeApiCall

class MovieRepositoryImpl(
    private val movieDbApi: MovieDbApi,
) : MovieRepository {
    override suspend fun getPopularMovies(): ApiResult<List<Movie>> {
        return safeApiCall {
            movieDbApi.getPopularMovies()
                .results
                .map { it.mapApiToDomain() }
        }
    }

    override suspend fun getMovieDetails(movieId: Int): ApiResult<MovieDetails> {
        return safeApiCall {
            movieDbApi.getMovieDetails(movieId)
                .mapApiToDomain()
        }
    }
}