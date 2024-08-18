package com.merkost.lumi.presentation.screens.navigation

import kotlinx.serialization.Serializable

sealed class Navigation {
    @Serializable
    data object Movies : Navigation()

    @Serializable
    data class MovieDetails(val movieId: Int) : Navigation()
}