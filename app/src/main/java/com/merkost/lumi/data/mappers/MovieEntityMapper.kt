package com.merkost.lumi.data.mappers

import com.merkost.lumi.data.local.entities.MovieEntity
import com.merkost.lumi.domain.models.Image
import com.merkost.lumi.domain.models.Movie

fun MovieEntity.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        averageRating = averageRating,
        image = posterPath?.let { Image(it) }
    )
}

fun Movie.toEntity(
    overview: String? = null,
    releaseDate: String? = null,
    backdropPath: String? = null,
    isFavorite: Boolean = false,
    isWatched: Boolean = false,
    isToWatch: Boolean = false
): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        averageRating = averageRating,
        posterPath = image?.url,
        backdropPath = backdropPath,
        overview = overview,
        releaseDate = releaseDate,
        isFavorite = isFavorite,
        isWatched = isWatched,
        isToWatch = isToWatch
    )
}
