package com.merkost.lumi.presentation.base

import com.merkost.lumi.R
import com.merkost.lumi.presentation.base.ApiResult.Error.UnknownError

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    sealed class Error(val exception: Throwable) : ApiResult<Nothing>() {


        companion object {
            fun fromThrowable(exception: Throwable): Error {
                return UnknownError(exception)
            }
        }

        fun <T> toUiState(): UiState<T> {
            return UiState.Error(
                message = exception.message.orEmpty(),
                messageRes = infoResource,
            )
        }

        data class NetworkError(val e: Throwable) : Error(e)
        data class UnknownError(val e: Throwable) : Error(e)

        private val infoResource: Int
            get() = when (this) {
                is NetworkError -> R.string.error_network
                is UnknownError -> R.string.error_unknown
            }
    }

    inline fun <R> fold(
        onSuccess: (T) -> R,
        onError: (Error) -> R
    ): R {
        return when (this) {
            is Success -> onSuccess(data)
            is Error -> onError(this)
        }
    }

    companion object {
        fun error(message: String): Error {
            return UnknownError(Exception(message))
        }
    }
}

fun <T> ApiResult<T>.getOrNull(): T? {
    return when (this) {
        is ApiResult.Success -> this.data
        is ApiResult.Error -> null
    }
}