package com.merkost.lumi.domain.repositories

import com.merkost.lumi.domain.models.ImagesConfiguration
import com.merkost.lumi.utils.ApiResult

interface ConfigurationRepository {
    suspend fun getConfiguration(): ApiResult<ImagesConfiguration>
    suspend fun updateConfiguration(): ApiResult<ImagesConfiguration>
}