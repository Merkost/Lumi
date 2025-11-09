package com.merkost.lumi.presentation.screens.movies

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlaylistPlay
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = koinViewModel(),
    onMovieClick: (Movie) -> Unit,
    onSearchClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onWatchedClick: () -> Unit = {},
    onToWatchClick: () -> Unit = {}
) {
    val screenState by viewModel.screenState.collectAsState()
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var showMenu by remember { mutableStateOf(false) }

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
                },
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.search)
                        )
                    }
                    Box {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "More options"
                            )
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.title_favorites)) },
                                onClick = {
                                    showMenu = false
                                    onFavoritesClick()
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = null
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.title_watched)) },
                                onClick = {
                                    showMenu = false
                                    onWatchedClick()
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Visibility,
                                        contentDescription = null
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.title_to_watch)) },
                                onClick = {
                                    showMenu = false
                                    onToWatchClick()
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.PlaylistPlay,
                                        contentDescription = null
                                    )
                                }
                            )
                        }
                    }
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
        columns = GridCells.Adaptive(150.dp),
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
            .aspectRatio(3 / 5f),
        onClick = onMovieClick,
        shape = MaterialTheme.shapes.large,
    ) {
        Box {
            MovieImage(
                modifier = Modifier
                    .fillMaxSize(),
                movieTitle = movie.title,
                imageUrl = movie.imagePath
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
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
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