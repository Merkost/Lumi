package com.merkost.lumi.utils

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(apiCall.invoke())
    } catch (e: Exception) {
        ApiResult.Error(e)
    }
}