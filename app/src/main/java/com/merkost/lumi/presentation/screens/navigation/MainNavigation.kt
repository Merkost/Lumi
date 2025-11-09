package com.merkost.lumi.presentation.screens.navigation

import MovieDetailsScreen
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.merkost.lumi.presentation.screens.lists.FavoritesScreen
import com.merkost.lumi.presentation.screens.lists.ToWatchScreen
import com.merkost.lumi.presentation.screens.lists.WatchedScreen
import com.merkost.lumi.presentation.screens.movies.MoviesScreen
import com.merkost.lumi.presentation.screens.search.SearchScreen

private const val ANIMATION_DURATION = 300

@Composable
fun MainNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    val upPress: () -> Unit = {
        navController.navigateUp()
    }

    NavHost(
        navController = navController,
        startDestination = Navigation.Movies,
        modifier = modifier,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(ANIMATION_DURATION)
            ) + fadeIn(animationSpec = tween(ANIMATION_DURATION))
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(ANIMATION_DURATION)
            ) + fadeOut(animationSpec = tween(ANIMATION_DURATION))
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(ANIMATION_DURATION)
            ) + fadeIn(animationSpec = tween(ANIMATION_DURATION))
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(ANIMATION_DURATION)
            ) + fadeOut(animationSpec = tween(ANIMATION_DURATION))
        }
    ) {
        composable<Navigation.Movies>(
            enterTransition = { fadeIn(animationSpec = tween(ANIMATION_DURATION)) },
            exitTransition = { fadeOut(animationSpec = tween(ANIMATION_DURATION)) }
        ) {
            MoviesScreen(
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
        }

        composable<Navigation.MovieDetails> {
            val movieDetails = it.toRoute<Navigation.MovieDetails>()
            MovieDetailsScreen(
                movieId = movieDetails.movieId,
                onBackPress = upPress
            )
        }

        composable<Navigation.Search> {
            SearchScreen(
                onMovieClick = { movie ->
                    navController.navigate(Navigation.MovieDetails(movieId = movie.id))
                },
                onBackPress = upPress
            )
        }

        composable<Navigation.Favorites> {
            FavoritesScreen(
                onMovieClick = { movie ->
                    navController.navigate(Navigation.MovieDetails(movieId = movie.id))
                },
                onBackPress = upPress
            )
        }

        composable<Navigation.Watched> {
            WatchedScreen(
                onMovieClick = { movie ->
                    navController.navigate(Navigation.MovieDetails(movieId = movie.id))
                },
                onBackPress = upPress
            )
        }

        composable<Navigation.ToWatch> {
            ToWatchScreen(
                onMovieClick = { movie ->
                    navController.navigate(Navigation.MovieDetails(movieId = movie.id))
                },
                onBackPress = upPress
            )
        }
    }
}