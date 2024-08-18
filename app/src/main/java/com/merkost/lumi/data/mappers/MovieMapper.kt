package com.merkost.lumi.data.mappers

import com.merkost.lumi.data.models.MovieDetailsResponse
import com.merkost.lumi.data.models.MovieDto
import com.merkost.lumi.domain.models.ImagesConfiguration
import com.merkost.lumi.domain.models.Movie
import com.merkost.lumi.domain.models.MovieDetails
import com.merkost.lumi.utils.image.ImageType
import com.merkost.lumi.utils.image.ImageUrlBuilder

fun MovieDto.mapApiToDomain(config: ImagesConfiguration): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        averageRating = this.voteAverage,
        image = ImageUrlBuilder(config).buildImage(ImageType.POSTER, this.posterPath),
    )
}

fun MovieDetailsResponse.mapApiToDomain(config: ImagesConfiguration): MovieDetails {
    return MovieDetails(
        title = this.title,
        overview = this.overview,
        runtime = this.runtime,
        backdropImage = ImageUrlBuilder(config).buildImage(ImageType.BACKDROP, this.backdropPath),
        releaseDate = this.releaseDate,
    )
}