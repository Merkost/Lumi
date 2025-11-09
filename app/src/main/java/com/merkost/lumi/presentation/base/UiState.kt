package com.merkost.lumi.presentation.base

import androidx.annotation.StringRes

sealed class UiState<out T> {
    data object Idle : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
    data object Empty : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String, @StringRes val messageRes: Int) : UiState<Nothing>()
}