package com.merkost.lumi.data.repository

import com.merkost.lumi.data.api.MovieDbApi
import com.merkost.lumi.data.mappers.MovieMapper
import com.merkost.lumi.domain.models.Movie
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
                        .map { MovieMapper.mapApiToDomain(it, config) }
                }
            }
        }
    }
}