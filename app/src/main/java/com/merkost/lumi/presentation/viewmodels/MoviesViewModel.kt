package com.merkost.lumi.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.merkost.lumi.domain.models.Movie
import com.merkost.lumi.domain.repositories.MovieRepository
import com.merkost.lumi.presentation.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow<UiState<List<Movie>>>(UiState.Loading)
    val screenState = _screenState.asStateFlow()

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            _screenState.value = UiState.Loading
            movieRepository.getPopularMovies()
                .fold(
                    onSuccess = { movies ->
                        _screenState.value = UiState.Success(movies)
                    },
                    onError = { e ->
                        _screenState.value = e.toUiState()
                    }
                )
        }
    }

    fun retryLoadingMovies() {
        fetchMovies()
    }
}