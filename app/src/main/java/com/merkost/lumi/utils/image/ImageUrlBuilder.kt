package com.merkost.lumi.utils.image

import com.merkost.lumi.domain.models.Image



class ImageUrlBuilder {

    val BASE_URL = "https://image.tmdb.org/t/p/"
    val POSTER_SIZE = "w500"

    fun buildImage(path: String?): Image? {
        return if (path != null) {
            Image("$BASE_URL$POSTER_SIZE$path")
        } else {
            null
        }
    }
}