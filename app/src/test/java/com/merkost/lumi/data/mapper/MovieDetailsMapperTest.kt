package com.merkost.lumi.data.mapper

import com.merkost.lumi.data.mappers.mapApiToDomain
import com.merkost.lumi.data.models.MovieDetailsResponse
import com.merkost.lumi.utils.image.ImageUrlBuilder
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.time.LocalDate

class MovieDetailsMapperTest {

    @Test
    fun `MovieDetailsResponse to MovieDetails mapper works correctly`() {
        val movieDetailsResponse = MovieDetailsResponse(
            id = 1,
            title = "Test Movie",
            overview = "Test overview.",
            runtime = 120,
            backdropPath = "/backdropPath.jpg",
            releaseDate = "2024-01-01"
        )
        val movieDetails = movieDetailsResponse.mapApiToDomain()

        assertThat(movieDetails.title).isEqualTo("Test Movie")
        assertThat(movieDetails.overview).isEqualTo("Test overview.")
        assertThat(movieDetails.runtime).isEqualTo(120)
        assertThat(movieDetails.backdropImage).isEqualTo(ImageUrlBuilder().buildImage("/backdropPath.jpg"))
        assertThat(movieDetails.releaseDate).isEqualTo(LocalDate.parse("2024-01-01"))
    }

    @Test
    fun `MovieDetailsResponse to MovieDetails mapper works correctly with null values`() {
        val movieDetailsResponse = MovieDetailsResponse(
            id = 1,
            title = "Test Movie",
            overview = null,
            runtime = 120,
            backdropPath = null,
            releaseDate = "2024-01-01"
        )
        val mappedMovieDetails = movieDetailsResponse.mapApiToDomain()

        assertThat(mappedMovieDetails.title).isEqualTo("Test Movie")
        assertThat(mappedMovieDetails.overview).isNull()
        assertThat(mappedMovieDetails.runtime).isEqualTo(120)
        assertThat(mappedMovieDetails.backdropImage).isNull()
        assertThat(mappedMovieDetails.releaseDate).isEqualTo(LocalDate.parse("2024-01-01"))
    }
}