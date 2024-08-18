package com.merkost.lumi.data.mappers

import com.merkost.lumi.data.models.MovieDetailsResponse
import com.merkost.lumi.data.models.MovieDto
import com.merkost.lumi.domain.models.Movie
import com.merkost.lumi.domain.models.MovieDetails
import com.merkost.lumi.utils.image.ImageUrlBuilder
import java.time.LocalDate

fun MovieDto.mapApiToDomain(): Movie {
    return Movie(
        id = this.id,
        title = this.title,
        averageRating = this.voteAverage,
        image = ImageUrlBuilder().buildImage(this.posterPath)
    )
}

fun MovieDetailsResponse.mapApiToDomain(): MovieDetails {
    return MovieDetails(
        title = this.title,
        overview = this.overview,
        runtime = this.runtime.takeUnless { it <= 0 },
        backdropImage = ImageUrlBuilder().buildImage(this.backdropPath),
        releaseDate = LocalDate.parse(this.releaseDate),
    )
}