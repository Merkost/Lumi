package com.merkost.lumi.presentation.base

import androidx.annotation.StringRes

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String, @param:StringRes val messageRes: Int) : UiState<Nothing>()
}