package com.merkost.lumi.domain.repositories

import com.merkost.lumi.domain.models.Movie
import com.merkost.lumi.domain.models.MovieDetails
import com.merkost.lumi.presentation.base.ApiResult

interface MovieRepository {
    suspend fun getPopularMovies(): ApiResult<List<Movie>>
    suspend fun getMovieDetails(movieId: Int): ApiResult<MovieDetails>
}