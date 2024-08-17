package com.merkost.lumi.data.mappers

import com.merkost.lumi.data.models.MovieDto
import com.merkost.lumi.domain.models.ImagesConfiguration
import com.merkost.lumi.domain.models.Movie
import com.merkost.lumi.utils.image.ImageType
import com.merkost.lumi.utils.image.ImageUrlBuilder

object MovieMapper {
    fun mapApiToDomain(movieDto: MovieDto, config: ImagesConfiguration): Movie {
        return Movie(
            id = movieDto.id,
            title = movieDto.title,
            averageRating = movieDto.voteAverage,
            image = ImageUrlBuilder(config).buildImage(ImageType.POSTER, movieDto.posterPath),
        )
    }
}