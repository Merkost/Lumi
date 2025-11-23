package com.merkost.lumi.presentation.screens.navigation

import kotlinx.serialization.Serializable

sealed class Navigation {
    @Serializable
    data object Movies : Navigation()

    @Serializable
    data class MovieDetails(val movieId: Int) : Navigation()

    @Serializable
    data object Search : Navigation()

    @Serializable
    data object Favorites : Navigation()

    @Serializable
    data object Watched : Navigation()

    @Serializable
    data object ToWatch : Navigation()
}