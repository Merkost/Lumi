package com.merkost.lumi.utils

import com.merkost.lumi.R
import com.merkost.lumi.presentation.base.UiState

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    sealed class Error(val exception: Throwable) : ApiResult<Nothing>() {

        fun <T> toUiState(): UiState<T> {
            return UiState.Error(
                message = exception.message.orEmpty(),
                messageRes = infoResource,
            )
        }

        companion object {
            fun fromThrowable(exception: Throwable): Error {
                return UnknownError(exception)
            }
        }

        data class NetworkError(val e: Throwable) : Error(e)
        data class UnknownError(val e: Throwable) : Error(e)

        val infoResource: Int
            get() = when (this) {
                is NetworkError -> R.string.error_network
                is UnknownError -> R.string.error_unknown
            }
    }

    fun getOrNull(): T? = if (this is Success) data else null

    inline fun <R> fold(
        onSuccess: (T) -> R,
        onError: (Error) -> R
    ): R {
        return when (this) {
            is Success -> onSuccess(data)
            is Error -> onError(this)
        }
    }
}