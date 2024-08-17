package com.merkost.lumi.presentation.screens.movies

import androidx.annotation.RawRes
import com.merkost.lumi.domain.models.Movie

sealed class MovieScreenState {
    data object Loading : MovieScreenState()
    data class Success(val movies: List<Movie>) : MovieScreenState()
    data class Error(@RawRes val messageRes: Int) : MovieScreenState()
}