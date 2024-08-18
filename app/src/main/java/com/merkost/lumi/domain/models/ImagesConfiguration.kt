package com.merkost.lumi.domain.models

data class ImagesConfiguration(
    val baseUrl: String,
    val secureBaseUrl: String,
    val posterSizes: List<String>,
    val backdropSizes: List<String>,
)