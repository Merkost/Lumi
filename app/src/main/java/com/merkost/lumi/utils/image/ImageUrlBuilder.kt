package com.merkost.lumi.utils.image

import com.merkost.lumi.domain.models.Image


class ImageUrlBuilder {

    companion object {
        private const val BASE_URL = "https://image.tmdb.org/t/p/"
        private const val POSTER_SIZE = "w500"
    }

    fun buildImage(path: String?): Image? {
        return if (path != null) {
            Image("$BASE_URL$POSTER_SIZE$path")
        } else {
            null
        }
    }
}