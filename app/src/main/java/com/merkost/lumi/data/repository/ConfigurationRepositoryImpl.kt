package com.merkost.lumi.data.repository

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.merkost.lumi.data.api.ConfigurationApi
import com.merkost.lumi.data.mappers.ConfigurationMapper
import com.merkost.lumi.domain.models.ImagesConfiguration
import com.merkost.lumi.domain.repositories.ConfigurationRepository
import com.merkost.lumi.utils.ApiResult
import com.merkost.lumi.utils.Constants
import com.merkost.lumi.utils.safeApiCall
import kotlinx.coroutines.flow.first

class ConfigurationRepositoryImpl(
    private val configApi: ConfigurationApi,
    private val context: Context
) : ConfigurationRepository {

    private val Context.configDatastore by preferencesDataStore(name = "config_prefs")

    private object DataStoreKeys {
        val BASE_URL = stringPreferencesKey("base_url")
        val SECURE_BASE_URL = stringPreferencesKey("secure_base_url")
        val POSTER_SIZES = stringSetPreferencesKey("poster_sizes")
        val BACKDROP_SIZES = stringSetPreferencesKey("backdrop_sizes")
        val LAST_UPDATE_TIME = longPreferencesKey("last_update_time")
    }

    override suspend fun updateConfiguration(): ApiResult<ImagesConfiguration> {
        return when (val result = safeApiCall { configApi.getConfiguration() }) {
            is ApiResult.Success -> {
                val domainConfig = ConfigurationMapper.mapApiToDomain(result.data.images)
                cacheConfig(domainConfig)
                ApiResult.Success(domainConfig)
            }

            is ApiResult.Error -> {
                ApiResult.Error.fromThrowable(result.exception)
            }
        }
    }

    override suspend fun getConfiguration(): ApiResult<ImagesConfiguration> {
        val cachedConfig = getCachedConfig()

        val needsUpdate = needsConfigurationUpdate()

        return if (cachedConfig != null && !needsUpdate) {
            ApiResult.Success(cachedConfig)
        } else {
            updateConfiguration().fold(
                onSuccess = {
                    ApiResult.Success(it)
                },
                onError = { e ->
                    ApiResult.Error.fromThrowable(e.exception)
                }
            )
        }
    }

    private suspend fun getCachedConfig(): ImagesConfiguration? {
        val preferences: Preferences = context.configDatastore.data.first()
        val baseUrl = preferences[DataStoreKeys.BASE_URL] ?: return null
        val secureBaseUrl = preferences[DataStoreKeys.SECURE_BASE_URL] ?: return null
        val posterSizes = preferences[DataStoreKeys.POSTER_SIZES]?.toList() ?: return null
        val backdropSizes = preferences[DataStoreKeys.BACKDROP_SIZES]?.toList() ?: return null

        return ImagesConfiguration(
            baseUrl = baseUrl,
            secureBaseUrl = secureBaseUrl,
            posterSizes = posterSizes,
            backdropSizes = backdropSizes
        )
    }

    private suspend fun cacheConfig(config: ImagesConfiguration) {
        context.configDatastore.edit { preferences ->
            preferences[DataStoreKeys.BASE_URL] = config.baseUrl
            preferences[DataStoreKeys.SECURE_BASE_URL] = config.secureBaseUrl
            preferences[DataStoreKeys.POSTER_SIZES] = config.posterSizes.toSet()
            preferences[DataStoreKeys.BACKDROP_SIZES] = config.backdropSizes.toSet()
            preferences[DataStoreKeys.LAST_UPDATE_TIME] = System.currentTimeMillis()

        }
    }

    private suspend fun needsConfigurationUpdate(): Boolean {
        val preferences: Preferences = context.configDatastore.data.first()
        val lastUpdateTime = preferences[DataStoreKeys.LAST_UPDATE_TIME] ?: 0L
        val currentTimeMillis = System.currentTimeMillis()

        return (currentTimeMillis - lastUpdateTime) > Constants.CONFIG_UPDATE_INTERVAL.inWholeMilliseconds
    }
}