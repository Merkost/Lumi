package com.merkost.lumi.presentation.viewmodels

import com.merkost.lumi.domain.models.Movie
import com.merkost.lumi.domain.repositories.MovieRepository
import com.merkost.lumi.presentation.base.UiState
import com.merkost.lumi.utils.ApiResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesViewModelTest {

    private val movieRepository: MovieRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        coEvery { movieRepository.getPopularMovies() } returns ApiResult.Success(
            listOf(Movie(id = 1, title = "Movie 1"), Movie(id = 2, title = "Movie 2"))
        )
    }

    @Test
    fun `successful data loads in success state`() = runTest(testDispatcher) {
        val viewModel = MoviesViewModel(movieRepository)

        advanceUntilIdle()

        assertThat(viewModel.screenState.value).isInstanceOf(UiState.Success::class.java)
        val successState = viewModel.screenState.value as UiState.Success

        assertThat(successState.data).containsExactly(
            Movie(id = 1, title = "Movie 1"),
            Movie(id = 2, title = "Movie 2")
        )
    }

    @Test
    fun `error results in error state`() = runTest(testDispatcher) {
        coEvery { movieRepository.getPopularMovies() } returns ApiResult.Error.fromThrowable(
            Exception("API Error")
        )
        val viewModel = MoviesViewModel(movieRepository)

        advanceUntilIdle()

        assertThat(viewModel.screenState.value).isInstanceOf(UiState.Error::class.java)
        val errorState = viewModel.screenState.value as UiState.Error
        assertThat(errorState.message).isEqualTo("API Error")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `retrying reloads the data`() = runTest(testDispatcher) {
        coEvery { movieRepository.getPopularMovies() } returns ApiResult.Error.fromThrowable(
            Exception("Initial Error")
        )

        val viewModel = MoviesViewModel(movieRepository)

        advanceUntilIdle()

        assertThat(viewModel.screenState.value).isInstanceOf(UiState.Error::class.java)

        coEvery { movieRepository.getPopularMovies() } returns ApiResult.Success(
            listOf(Movie(id = 1, title = "Movie 1"), Movie(id = 2, title = "Movie 2"))
        )

        viewModel.retryLoadingMovies()

        advanceUntilIdle()

        assertThat(viewModel.screenState.value).isInstanceOf(UiState.Success::class.java)
    }
}