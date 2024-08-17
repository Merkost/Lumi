package com.merkost.lumi.presentation.screens.movies

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.merkost.lumi.R
import com.merkost.lumi.domain.models.Movie
import com.merkost.lumi.presentation.components.ErrorView
import com.merkost.lumi.presentation.components.LottieLoading
import com.merkost.lumi.presentation.components.LumiTopAppBar
import com.merkost.lumi.presentation.viewmodels.MoviesViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(viewModel: MoviesViewModel = koinViewModel()) {
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
                        modifier = Modifier.size(48.dp)
                    )
                }
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                AnimatedContent(screenState, label = "moviesScreenStateAnim") { state ->
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        when (state) {
                            is MovieScreenState.Loading -> {
                                LottieLoading(
                                    modifier = Modifier.size(226.dp),
                                    resId = R.raw.popcorn_loading
                                )
                            }

                            is MovieScreenState.Error -> {
                                ErrorView(
                                    message = stringResource(state.messageRes),
                                    onRetry = {
                                        viewModel.retryLoadingMovies()
                                    }
                                )
                            }

                            is MovieScreenState.Success -> {
                                MovieGrid(state.movies)
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun MovieGrid(movies: List<Movie>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(movies, key = { it.id }) { movie ->
            MovieItem(movie)
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Box {

            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movie.image?.medium)
                    .crossfade(true)
                    .build(),
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                error = {
                    Image(
                        painter = painterResource(id = R.drawable.no_image_placeholder),
                        contentDescription = "Error loading image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                },

                contentScale = ContentScale.Crop
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(4.dp)
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(
                        color = movie.ratingColor
                    )
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
            ) {
                Text(
                    text = movie.ratingText,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                )
            }

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