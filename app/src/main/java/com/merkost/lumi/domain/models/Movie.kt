package com.merkost.lumi.domain.models

data class Movie(
    val id: Int,
    val title: String = "",
    val averageRating: Double = 0.0,
    val imagePath: String?,
    val isFavorite: Boolean = false,
    val isWatched: Boolean = false,
    val isToWatch: Boolean = false
)