package com.merkost.lumi.data.api

import com.merkost.lumi.data.models.MovieDetailsResponse
import com.merkost.lumi.data.models.MoviesResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MovieDbApi(
    private val client: HttpClient
) {
    suspend fun getPopularMovies(page: Int = 1): MoviesResponse {
        return client.get("movie/popular") {
            url {
                parameters.append("page", page.toString())
            }
        }.body()
    }

    suspend fun getMovieDetails(movieId: Int): MovieDetailsResponse {
        return client.get("movie/$movieId").body()
    }
}