package com.merkost.lumi.presentation.screens.movies

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.merkost.lumi.R
import com.merkost.lumi.domain.models.Movie
import com.merkost.lumi.presentation.components.LumiTopAppBar
import com.merkost.lumi.presentation.components.MovieImage
import com.merkost.lumi.presentation.components.RatingBadge
import com.merkost.lumi.presentation.components.ScreenStateHandler
import com.merkost.lumi.presentation.viewmodels.MoviesViewModel
import org.koin.androidx.compose.koinViewModel
import shimmerEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = koinViewModel(),
    onMovieClick: (Movie) -> Unit
) {
    val screenState by viewModel.screenState.collectAsState()
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        contentWindowInsets = WindowInsets.statusBars,
        modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            LumiTopAppBar(
                title = stringResource(id = R.string.title_popular_movies),
                scrollBehavior = scrollBehaviour,
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "App logo",
                        modifier = Modifier
                            .size(64.dp)
                            .fillMaxSize()
                    )
                }
            )
        },
        content = { innerPadding ->
            ScreenStateHandler(
                screenState,
                modifier = Modifier.padding(innerPadding),
                onRetry = viewModel::retryLoadingMovies,
                successContent = { data ->
                    MovieGrid(data, onMovieClick = onMovieClick)
                }
            )
        }
    )
}

@Composable
fun MovieGrid(
    movies: List<Movie>,
    modifier: Modifier = Modifier,
    onMovieClick: (Movie) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movies, key = { it.id }) { movie ->
            MovieItem(modifier = Modifier, movie, onMovieClick = { onMovieClick(movie) })
        }
    }
}

@Composable
fun MovieItem(modifier: Modifier = Modifier, movie: Movie, onMovieClick: () -> Unit) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(320.dp),
        onClick = onMovieClick,
        shape = MaterialTheme.shapes.large,
    ) {
        Box {
            MovieImage(
                modifier = Modifier
                    .fillMaxSize(),
                movieTitle = movie.title,
                imageUrl = movie.image?.large
            )

            RatingBadge(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopEnd),
                rating = movie.averageRating,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .background(
                        color = Color.Black.copy(alpha = 0.5f),
                    )
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .align(Alignment.CenterStart)
                )
            }
        }
    }
}