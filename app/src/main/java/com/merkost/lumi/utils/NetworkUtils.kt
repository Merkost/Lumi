package com.merkost.lumi.utils

import android.util.Log
import com.merkost.lumi.BuildConfig
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(apiCall.invoke())
    } catch (e: Exception) {
        if (BuildConfig.DEBUG) {
            Log.e("SafeApiCall", "Error type - ${e::class.java.simpleName}")
            Log.e("SafeApiCall", e.localizedMessage ?: "An unknown error occurred")
        }
        when (e) {
            is IOException -> ApiResult.Error.NetworkError(e)
            else -> ApiResult.Error.UnknownError(e)
        }
    }
}