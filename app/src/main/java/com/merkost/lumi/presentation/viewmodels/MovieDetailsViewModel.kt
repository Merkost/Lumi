package com.merkost.lumi.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.merkost.lumi.domain.models.MovieDetails
import com.merkost.lumi.domain.repositories.MovieRepository
import com.merkost.lumi.presentation.base.UiState
import com.merkost.lumi.presentation.screens.navigation.Navigation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val movieDetails = savedStateHandle.toRoute<Navigation.MovieDetails>()

    private val _screenState =
        MutableStateFlow<UiState<MovieDetails>>(UiState.Loading)
    val screenState = _screenState.asStateFlow()

    init {
        loadMovieDetails()
    }

    private fun loadMovieDetails() {
        viewModelScope.launch {
            movieRepository.getMovieDetails(movieDetails.movieId).fold(
                onSuccess = { movieDetails ->
                    _screenState.value = UiState.Success(movieDetails)
                },
                onError = { error ->
                    _screenState.value = error.toUiState()
                }
            )
        }
    }

    fun retryLoadingMovieDetails() {
        loadMovieDetails()
    }
}