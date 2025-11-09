package com.merkost.lumi.presentation.base

import androidx.annotation.StringRes

/**
 * UI state specifically for search functionality
 * Includes additional states for search-specific scenarios
 */
sealed class SearchUiState<out T> {
    /**
     * Initial state before user starts searching
     */
    data object Idle : SearchUiState<Nothing>()

    /**
     * Loading state while search is in progress
     */
    data object Loading : SearchUiState<Nothing>()

    /**
     * Empty state when search returns no results
     */
    data object Empty : SearchUiState<Nothing>()

    /**
     * Success state with search results
     */
    data class Success<out T>(val data: T) : SearchUiState<T>()

    /**
     * Error state when search fails
     */
    data class Error(val message: String, @StringRes val messageRes: Int) : SearchUiState<Nothing>()
}
