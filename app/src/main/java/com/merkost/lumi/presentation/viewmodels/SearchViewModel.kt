package com.merkost.lumi.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.merkost.lumi.domain.models.Movie
import com.merkost.lumi.domain.repositories.MovieRepository
import com.merkost.lumi.presentation.base.UiState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _searchResults = MutableStateFlow<UiState<List<Movie>>>(UiState.Idle)
    val searchResults = _searchResults.asStateFlow()

    init {
        viewModelScope.launch {
            searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isBlank()) {
                        _searchResults.value = UiState.Idle
                    } else {
                        searchMovies(query)
                    }
                }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    private fun searchMovies(query: String) {
        viewModelScope.launch {
            _searchResults.value = UiState.Loading
            movieRepository.searchMovies(query).fold(
                onSuccess = { movies ->
                    _searchResults.value = if (movies.isEmpty()) {
                        UiState.Empty
                    } else {
                        UiState.Success(movies)
                    }
                },
                onError = { error ->
                    _searchResults.value = error.toUiState()
                }
            )
        }
    }

    fun retrySearch() {
        val query = _searchQuery.value
        if (query.isNotBlank()) {
            searchMovies(query)
        }
    }
}
