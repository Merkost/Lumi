package com.merkost.lumi.data.models

import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    val page: Int,
    val results: List<MovieDto>
)
