package com.merkost.lumi.data.api

import com.merkost.lumi.data.models.ConfigurationResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class ConfigurationApi(
    private val client: HttpClient
) {
    suspend fun getConfiguration(): ConfigurationResponse {
        return client.get("configuration").body()
    }
}