package com.merkost.lumi.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.merkost.lumi.domain.models.Movie
import com.merkost.lumi.domain.repositories.LocalMovieRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class WatchedViewModel(
    private val localMovieRepository: LocalMovieRepository
) : ViewModel() {

    val watchedMovies: StateFlow<List<Movie>> = localMovieRepository
        .getWatchedMovies()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val isEmpty: StateFlow<Boolean> = watchedMovies
        .map { it.isEmpty() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = true
        )
}
