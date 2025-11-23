package com.merkost.lumi.presentation.screens.navigation

import MovieDetailsScreen
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.merkost.lumi.domain.models.Movie
import com.merkost.lumi.presentation.screens.lists.FavoritesScreen
import com.merkost.lumi.presentation.screens.lists.ToWatchScreen
import com.merkost.lumi.presentation.screens.lists.WatchedScreen
import com.merkost.lumi.presentation.screens.movies.MoviesScreen
import com.merkost.lumi.presentation.screens.search.SearchScreen

private const val ANIMATION_DURATION_MS = 300

@Composable
fun MainNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Navigation.Movies,
        modifier = modifier,
        enterTransition = { defaultEnterTransition() },
        exitTransition = { defaultExitTransition() },
        popEnterTransition = { defaultPopEnterTransition() },
        popExitTransition = { defaultPopExitTransition() }
    ) {
        moviesDestination(
            onMovieClick = { movie ->
                navController.navigate(Navigation.MovieDetails(movieId = movie.id))
            },
            onSearchClick = {
                navController.navigate(Navigation.Search) {
                    launchSingleTop = true
                }
            },
            onFavoritesClick = {
                navController.navigate(Navigation.Favorites) {
                    launchSingleTop = true
                }
            },
            onWatchedClick = {
                navController.navigate(Navigation.Watched) {
                    launchSingleTop = true
                }
            },
            onToWatchClick = {
                navController.navigate(Navigation.ToWatch) {
                    launchSingleTop = true
                }
            }
        )

        movieDetailsDestination(
            onBackPress = { navController.navigateUp() }
        )

        searchDestination(
            onMovieClick = { movie ->
                navController.navigate(Navigation.MovieDetails(movieId = movie.id))
            },
            onBackPress = { navController.navigateUp() }
        )

        favoritesDestination(
            onMovieClick = { movie ->
                navController.navigate(Navigation.MovieDetails(movieId = movie.id))
            },
            onBackPress = { navController.navigateUp() }
        )

        watchedDestination(
            onMovieClick = { movie ->
                navController.navigate(Navigation.MovieDetails(movieId = movie.id))
            },
            onBackPress = { navController.navigateUp() }
        )

        toWatchDestination(
            onMovieClick = { movie ->
                navController.navigate(Navigation.MovieDetails(movieId = movie.id))
            },
            onBackPress = { navController.navigateUp() }
        )
    }
}

// Navigation 3 Extension Functions for Destinations

private fun NavGraphBuilder.moviesDestination(
    onMovieClick: (Movie) -> Unit,
    onSearchClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onWatchedClick: () -> Unit,
    onToWatchClick: () -> Unit
) {
    composable<Navigation.Movies>(
        enterTransition = { fadeIn(animationSpec = tween(ANIMATION_DURATION_MS)) },
        exitTransition = { fadeOut(animationSpec = tween(ANIMATION_DURATION_MS)) }
    ) {
        MoviesScreen(
            onMovieClick = onMovieClick,
            onSearchClick = onSearchClick,
            onFavoritesClick = onFavoritesClick,
            onWatchedClick = onWatchedClick,
            onToWatchClick = onToWatchClick
        )
    }
}

private fun NavGraphBuilder.movieDetailsDestination(
    onBackPress: () -> Unit
) {
    composable<Navigation.MovieDetails> {
        val route = it.toRoute<Navigation.MovieDetails>()
        MovieDetailsScreen(
            movieId = route.movieId,
            onBackPress = onBackPress
        )
    }
}

private fun NavGraphBuilder.searchDestination(
    onMovieClick: (Movie) -> Unit,
    onBackPress: () -> Unit
) {
    composable<Navigation.Search> {
        SearchScreen(
            onMovieClick = onMovieClick,
            onBackPress = onBackPress
        )
    }
}

private fun NavGraphBuilder.favoritesDestination(
    onMovieClick: (Movie) -> Unit,
    onBackPress: () -> Unit
) {
    composable<Navigation.Favorites> {
        FavoritesScreen(
            onMovieClick = onMovieClick,
            onBackPress = onBackPress
        )
    }
}

private fun NavGraphBuilder.watchedDestination(
    onMovieClick: (Movie) -> Unit,
    onBackPress: () -> Unit
) {
    composable<Navigation.Watched> {
        WatchedScreen(
            onMovieClick = onMovieClick,
            onBackPress = onBackPress
        )
    }
}

private fun NavGraphBuilder.toWatchDestination(
    onMovieClick: (Movie) -> Unit,
    onBackPress: () -> Unit
) {
    composable<Navigation.ToWatch> {
        ToWatchScreen(
            onMovieClick = onMovieClick,
            onBackPress = onBackPress
        )
    }
}

// Navigation 3 Animation Extensions

private fun AnimatedContentTransitionScope<*>.defaultEnterTransition(): EnterTransition {
    return slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Start,
        animationSpec = tween(ANIMATION_DURATION_MS)
    ) + fadeIn(animationSpec = tween(ANIMATION_DURATION_MS))
}

private fun AnimatedContentTransitionScope<*>.defaultExitTransition(): ExitTransition {
    return slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Start,
        animationSpec = tween(ANIMATION_DURATION_MS)
    ) + fadeOut(animationSpec = tween(ANIMATION_DURATION_MS))
}

private fun AnimatedContentTransitionScope<*>.defaultPopEnterTransition(): EnterTransition {
    return slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.End,
        animationSpec = tween(ANIMATION_DURATION_MS)
    ) + fadeIn(animationSpec = tween(ANIMATION_DURATION_MS))
}

private fun AnimatedContentTransitionScope<*>.defaultPopExitTransition(): ExitTransition {
    return slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.End,
        animationSpec = tween(ANIMATION_DURATION_MS)
    ) + fadeOut(animationSpec = tween(ANIMATION_DURATION_MS))
}
