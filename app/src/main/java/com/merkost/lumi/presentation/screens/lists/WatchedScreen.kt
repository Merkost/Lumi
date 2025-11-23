package com.merkost.lumi.presentation.screens.lists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.merkost.lumi.R
import com.merkost.lumi.domain.models.Movie
import com.merkost.lumi.presentation.components.LumiTopAppBar
import com.merkost.lumi.presentation.screens.movies.MovieGrid
import com.merkost.lumi.presentation.viewmodels.WatchedViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchedScreen(
    viewModel: WatchedViewModel = koinViewModel(),
    onMovieClick: (Movie) -> Unit,
    onBackPress: () -> Unit
) {
    val watchedMovies by viewModel.watchedMovies.collectAsState()
    val isEmpty by viewModel.isEmpty.collectAsState()

    Scaffold(
        contentWindowInsets = WindowInsets.statusBars,
        topBar = {
            LumiTopAppBar(
                title = stringResource(R.string.title_watched),
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        },
        content = { innerPadding ->
            if (isEmpty) {
                EmptyState(
                    message = stringResource(R.string.empty_watched),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            } else {
                MovieGrid(
                    movies = watchedMovies,
                    modifier = Modifier.padding(innerPadding),
                    onMovieClick = onMovieClick
                )
            }
        }
    )
}

@Composable
private fun EmptyState(message: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
