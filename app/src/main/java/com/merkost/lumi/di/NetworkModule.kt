package com.merkost.lumi.di

import android.util.Log
import com.merkost.lumi.BuildConfig
import com.merkost.lumi.data.api.MovieDbApi
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.time.Duration.Companion.seconds


private val TIME_OUT = 8.seconds.inWholeMilliseconds

val networkModule = module {
    single(named("DefaultOkHttpClient")) { DefaultOkHttp }

    single(named("MovieDbHttpClient")) {
        getMovieDbClient(get(named("DefaultOkHttpClient")))
    }

    single<MovieDbApi> { MovieDbApi(get(named("MovieDbHttpClient"))) }
}

private fun getMovieDbClient(defaultClient: HttpClient): HttpClient {
    return defaultClient.config {

        install(DefaultRequest) {
            url {
                takeFrom(BuildConfig.MOVIE_DB_BASE_URL)
            }

            header(HttpHeaders.Accept, ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer ${BuildConfig.MOVIE_DB_AUTH_TOKEN}")
        }

    }
}

internal val DefaultOkHttp = HttpClient(Android) {
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        )

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("Http Ktor Logging ->", message)
                }
            }
            level = if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE
        }

        install(ResponseObserver) {
            onResponse { response ->
                Log.d("HTTP status:", "${response.status.value}")
            }
        }

        install(HttpTimeout) {
            connectTimeoutMillis = TIME_OUT
            socketTimeoutMillis = TIME_OUT
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }
}