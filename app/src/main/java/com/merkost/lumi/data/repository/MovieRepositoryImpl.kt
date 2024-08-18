package com.merkost.lumi.data.repository

import com.merkost.lumi.data.api.MovieDbApi
import com.merkost.lumi.data.mappers.mapApiToDomain
import com.merkost.lumi.domain.models.Movie
import com.merkost.lumi.domain.models.MovieDetails
import com.merkost.lumi.domain.repositories.ConfigurationRepository
import com.merkost.lumi.domain.repositories.MovieRepository
import com.merkost.lumi.utils.ApiResult
import com.merkost.lumi.utils.safeApiCall

class MovieRepositoryImpl(
    private val movieDbApi: MovieDbApi,
    private val configurationRepository: ConfigurationRepository,
) : MovieRepository {
    override suspend fun getPopularMovies(): ApiResult<List<Movie>> {
        when (val configResult = configurationRepository.getConfiguration()) {
            is ApiResult.Error -> return configResult
            is ApiResult.Success -> {
                val config = configResult.data
                return safeApiCall {
                    movieDbApi.getPopularMovies()
                        .results
                        .map { it.mapApiToDomain(config) }
                }
            }
        }
    }

    override suspend fun getMovieDetails(movieId: Int): ApiResult<MovieDetails> {
        when (val configResult = configurationRepository.getConfiguration()) {
            is ApiResult.Error -> return configResult
            is ApiResult.Success -> {
                val config = configResult.data
                return safeApiCall {
                    movieDbApi.getMovieDetails(movieId)
                        .mapApiToDomain(config)
                }
            }
        }
    }
}