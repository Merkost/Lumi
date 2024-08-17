package com.merkost.lumi.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.merkost.lumi.domain.repositories.MovieRepository
import com.merkost.lumi.presentation.screens.movies.MovieScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow<MovieScreenState>(MovieScreenState.Loading)
    val screenState: StateFlow<MovieScreenState> = _screenState

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            _screenState.value = MovieScreenState.Loading
            movieRepository.getPopularMovies()
                .fold(
                    onSuccess = { movies ->
                        _screenState.value = MovieScreenState.Success(movies)
                    },
                    onError = { e ->
                        _screenState.value = MovieScreenState.Error(e.infoResource)
                    }
                )
        }
    }

    fun retryLoadingMovies() {
        fetchMovies()
    }
}