package com.merkost.lumi.domain.models

import java.time.LocalDate

data class MovieDetails(
    val title: String?,
    val overview: String?,
    val backdropImage: Image?,
    val runtime: Int,
    val releaseDate: LocalDate?,
)