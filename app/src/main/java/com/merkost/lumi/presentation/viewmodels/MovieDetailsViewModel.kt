package com.merkost.lumi.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.merkost.lumi.domain.models.MovieDetails
import com.merkost.lumi.domain.repositories.LocalMovieRepository
import com.merkost.lumi.domain.repositories.MovieRepository
import com.merkost.lumi.presentation.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieId: Int,
    private val movieRepository: MovieRepository,
    private val localMovieRepository: LocalMovieRepository
) : ViewModel() {

    private val _screenState =
        MutableStateFlow<UiState<MovieDetails>>(UiState.Loading)
    val screenState = _screenState.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    private val _isWatched = MutableStateFlow(false)
    val isWatched = _isWatched.asStateFlow()

    private val _isToWatch = MutableStateFlow(false)
    val isToWatch = _isToWatch.asStateFlow()

    init {
        loadMovieDetails()
        observeMovieStatus()
    }

    private fun observeMovieStatus() {
        viewModelScope.launch {
            localMovieRepository.getMovieByIdFlow(movieId).collect { movie ->
                movie?.let {
                    // Check movie status in database
                    // Note: We need to add these flags to MovieEntity
                    // For now, we'll just set them to false
                }
            }
        }
    }

    private fun loadMovieDetails() {
        viewModelScope.launch {
            movieRepository.getMovieDetails(movieId).fold(
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

    fun toggleFavorite() {
        viewModelScope.launch {
            val newValue = !_isFavorite.value
            localMovieRepository.toggleFavorite(movieId, newValue)
            _isFavorite.value = newValue
        }
    }

    fun toggleWatched() {
        viewModelScope.launch {
            val newValue = !_isWatched.value
            localMovieRepository.toggleWatched(movieId, newValue)
            _isWatched.value = newValue
        }
    }

    fun toggleToWatch() {
        viewModelScope.launch {
            val newValue = !_isToWatch.value
            localMovieRepository.toggleToWatch(movieId, newValue)
            _isToWatch.value = newValue
        }
    }
}