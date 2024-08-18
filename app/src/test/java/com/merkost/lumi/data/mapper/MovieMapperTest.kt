package com.merkost.lumi.data.mapper

import com.merkost.lumi.data.mappers.mapApiToDomain
import com.merkost.lumi.data.models.MovieDto
import com.merkost.lumi.utils.image.ImageUrlBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MovieMapperTest {

    @Test
    fun `MovieDto to Movie mapper works correctly`() {
        val movieDto = MovieDto(
            id = 1,
            title = "Test Movie",
            voteAverage = 8.0,
            posterPath = "/posterPath.jpg"
        )
        val movie = movieDto.mapApiToDomain()

        assertThat(movie.id).isEqualTo(1)
        assertThat(movie.title).isEqualTo("Test Movie")
        assertThat(movie.averageRating).isEqualTo(8.0)
        assertThat(movie.image).isEqualTo(ImageUrlBuilder().buildImage("/posterPath.jpg"))
    }

    @Test
    fun `MovieDto to Movie mapper works correctly with null values`() {
        val movieDto = MovieDto(
            id = 1,
            title = "Test Movie",
            voteAverage = 8.0,
            posterPath = null
        )
        val mappedMovie = movieDto.mapApiToDomain()

        assertThat(mappedMovie.id).isEqualTo(1)
        assertThat(mappedMovie.title).isEqualTo("Test Movie")
        assertThat(mappedMovie.averageRating).isEqualTo(8.0)
        assertThat(mappedMovie.image).isNull()
    }
}