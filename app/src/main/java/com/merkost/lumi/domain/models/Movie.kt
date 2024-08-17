package com.merkost.lumi.domain.models

data class Movie(
    val id: Int,
    val title: String,
    val averageRating: Double,
    val posterPath: String
)