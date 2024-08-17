package com.merkost.lumi.data.mappers

import com.merkost.lumi.data.models.MovieDto
import com.merkost.lumi.domain.models.Movie

object MovieMapper {
    fun mapDtoToDomain(dto: MovieDto): Movie {
        return Movie(
            id = dto.id,
            title = dto.title,
            averageRating = dto.voteAverage,
            posterPath = dto.posterPath ?: ""
        )
    }
}