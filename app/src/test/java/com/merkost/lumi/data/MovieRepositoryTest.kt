package com.merkost.lumi.data

import android.util.Log
import com.merkost.lumi.data.api.MovieDbApi
import com.merkost.lumi.data.models.MovieDetailsResponse
import com.merkost.lumi.data.models.MovieDto
import com.merkost.lumi.data.models.MoviesResponse
import com.merkost.lumi.data.repository.MovieRepositoryImpl
import com.merkost.lumi.utils.ApiResult
import com.merkost.lumi.utils.image.ImageUrlBuilder
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class MovieRepositoryTest {

    private lateinit var movieRepository: MovieRepositoryImpl
    private val imageUrlBuilder = ImageUrlBuilder()
    private val movieDbApi: MovieDbApi = mockk()

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        movieRepository = MovieRepositoryImpl(movieDbApi)
    }

    @Test
    fun `getPopularMovies returns a list of movies when success`() = runTest {
        val mockMoviesResponse = MoviesResponse(
            page = 1,
            results = listOf(
                MovieDto(id = 1, title = "Movie 1"),
                MovieDto(id = 2, title = "Movie 2")
            )
        )

        coEvery { movieDbApi.getPopularMovies(any()) } returns mockMoviesResponse
        val result = movieRepository.getPopularMovies()

        assertThat(result).isInstanceOf(ApiResult.Success::class.java)
        result as ApiResult.Success
        assertThat(result.data).hasSize(2)
        assertThat(result.data).extracting("title").containsExactly("Movie 1", "Movie 2")
    }

    @Test
    fun `getPopularMovies returns an error when failure`() = runTest {
        coEvery { movieDbApi.getPopularMovies(any()) } throws Exception("API error")

        val result = movieRepository.getPopularMovies()

        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        result as ApiResult.Error
        assertThat(result.exception.message).isEqualTo("API error")
    }

    @Test
    fun `getMovieDetails returns movie details when success`() = runTest {
        val mockMovieDetailsResponse = MovieDetailsResponse(
            id = 1,
            title = "Movie 1",
            overview = "Overview",
            backdropPath = "/backdrop.jpg",
            releaseDate = "2022-01-01",
            runtime = 120,
        )

        coEvery { movieDbApi.getMovieDetails(1) } returns mockMovieDetailsResponse

        val result = movieRepository.getMovieDetails(1)

        assertThat(result).isInstanceOf(ApiResult.Success::class.java)
        result as ApiResult.Success
        assertThat(result.data.title).isEqualTo("Movie 1")
        assertThat(result.data.overview).isEqualTo("Overview")
        assertThat(result.data.backdropImage?.medium)
            .isEqualTo(imageUrlBuilder.BASE_URL + imageUrlBuilder.POSTER_SIZE + "/backdrop.jpg")
        assertThat(result.data.releaseDate).isEqualTo("2022-01-01")
        assertThat(result.data.runtime).isEqualTo(120)
    }

    @Test
    fun `getMovieDetails returns an error when failure`() = runTest {
        coEvery { movieDbApi.getMovieDetails(1) } throws Exception("API error")

        val result = movieRepository.getMovieDetails(1)

        assertThat(result).isInstanceOf(ApiResult.Error::class.java)
        result as ApiResult.Error
        assertThat(result.exception.message).isEqualTo("API error")
    }
}