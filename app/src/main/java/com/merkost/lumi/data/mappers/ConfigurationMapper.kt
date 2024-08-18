package com.merkost.lumi.data.mappers

import com.merkost.lumi.data.models.ImagesConfigDto
import com.merkost.lumi.domain.models.ImagesConfiguration

object ConfigurationMapper {
    fun mapApiToDomain(imagesConfig: ImagesConfigDto): ImagesConfiguration {
        return ImagesConfiguration(
            baseUrl = imagesConfig.baseUrl,
            secureBaseUrl = imagesConfig.secureBaseUrl,
            posterSizes = imagesConfig.posterSizes,
            backdropSizes = imagesConfig.backdropSizes
        )
    }
}