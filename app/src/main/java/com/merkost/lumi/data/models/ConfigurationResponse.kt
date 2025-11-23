package com.merkost.lumi.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ConfigurationResponse(
    val images: ImagesConfigDto
)