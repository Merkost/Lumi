package com.merkost.lumi.utils.image

import android.util.Log
import com.merkost.lumi.domain.models.Image
import com.merkost.lumi.domain.models.ImagesConfiguration

class ImageUrlBuilder(
    private val configuration: ImagesConfiguration
) {

    fun buildImage(imageType: ImageType, imagePath: String?): Image? {
        if (imagePath == null) return null

        return kotlin.runCatching {
            val smallImage = buildImageUrl(imageType, imagePath, ImageSizeType.SMALL)
            val mediumImage = buildImageUrl(imageType, imagePath, ImageSizeType.MEDIUM)
            val largeImage = buildImageUrl(imageType, imagePath, ImageSizeType.LARGE)
            val originalImage = buildImageUrl(imageType, imagePath, ImageSizeType.ORIGINAL)

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

    private fun buildImageUrl(
        imageType: ImageType,
        posterPath: String,
        sizeType: ImageSizeType
    ): String {
        val availableSizes = when (imageType) {
            ImageType.POSTER -> configuration.posterSizes
            ImageType.BACKDROP -> configuration.backdropSizes
        }

        val size = getAvailableSize(availableSizes, sizeType)
        return "${configuration.secureBaseUrl}$size$posterPath"
    }

    private fun getAvailableSize(sizes: List<String>, sizeType: ImageSizeType): String {
        return when (sizeType) {
            ImageSizeType.SMALL -> sizes.first()
            ImageSizeType.MEDIUM -> sizes.getOrNull(2) ?: sizes.first()
            ImageSizeType.LARGE -> sizes.getOrNull(4) ?: sizes.last()
            ImageSizeType.ORIGINAL -> sizes.last()
        }
    }
}