package com.merkost.lumi.domain.models

data class Movie(
    val id: Int,
    val title: String = "",
    val averageRating: Double = 0.0,
    val image: Image? = null
)