package com.merkost.lumi.domain.repositories

import com.merkost.lumi.domain.models.Movie
import com.merkost.lumi.utils.ApiResult

interface MovieRepository {
    suspend fun getPopularMovies(): ApiResult<List<Movie>>
}