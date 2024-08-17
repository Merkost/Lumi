package com.merkost.lumi.utils.image

import android.util.Log
import com.merkost.lumi.domain.models.Image
import com.merkost.lumi.domain.models.ImagesConfiguration

class ImageUrlBuilder(
    private val configuration: ImagesConfiguration
) {

    fun buildImage(imageType: ImageType, posterPath: String?): Image? {
        if (posterPath == null) return null

        return kotlin.runCatching {
            val smallImage = buildImageUrl(imageType, posterPath, ImageSizeType.SMALL)
            val mediumImage = buildImageUrl(imageType, posterPath, ImageSizeType.MEDIUM)
            val largeImage = buildImageUrl(imageType, posterPath, ImageSizeType.LARGE)
            val originalImage = buildImageUrl(imageType, posterPath, ImageSizeType.ORIGINAL)

            Image(
                small = smallImage,
                medium = mediumImage,
                large = largeImage,
                original = originalImage
            )
        }.onFailure {
            Log.e("ImageUrlBuilder", "Error building image", it)
        }.getOrNull()
    }

    private fun buildImageUrl(imageType: ImageType, posterPath: String, sizeType: ImageSizeType): String {
        val availableSizes = when (imageType) {
            ImageType.POSTER -> configuration.posterSizes
        }

        val size = getAvailableSize(availableSizes, sizeType)
        return "${configuration.secureBaseUrl}$size$posterPath"
    }

    private fun getAvailableSize(sizes: List<String>, sizeType: ImageSizeType): String {
        return when (sizeType) {
            ImageSizeType.SMALL -> sizes.first()
            ImageSizeType.MEDIUM -> sizes.getOrNull(2) ?: sizes.first()
            ImageSizeType.LARGE -> sizes.getOrNull(4) ?: sizes.first()
            ImageSizeType.ORIGINAL -> sizes.last()
        }
    }
}