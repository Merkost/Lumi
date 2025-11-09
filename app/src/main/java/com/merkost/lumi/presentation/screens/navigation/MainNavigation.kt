package com.merkost.lumi.presentation.screens.navigation

import MovieDetailsScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.merkost.lumi.presentation.screens.lists.FavoritesScreen
import com.merkost.lumi.presentation.screens.lists.ToWatchScreen
import com.merkost.lumi.presentation.screens.lists.WatchedScreen
import com.merkost.lumi.presentation.screens.movies.MoviesScreen
import com.merkost.lumi.presentation.screens.search.SearchScreen

@Composable
fun MainNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    val upPress: () -> Unit = {
        if (!navController.popBackStack()) {
            navController.navigate(Navigation.Movies) {
                launchSingleTop = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Navigation.Movies,
        modifier = modifier
    ) {
        composable<Navigation.Movies> {
            MoviesScreen(
                onMovieClick = {
                    navController.navigate(
                        Navigation.MovieDetails(movieId = it.id)
                    )
                },
                onSearchClick = {
                    navController.navigate(Navigation.Search)
                },
                onFavoritesClick = {
                    navController.navigate(Navigation.Favorites)
                },
                onWatchedClick = {
                    navController.navigate(Navigation.Watched)
                },
                onToWatchClick = {
                    navController.navigate(Navigation.ToWatch)
                }
            )
        }

        composable<Navigation.MovieDetails> {
            val movieDetails = it.toRoute<Navigation.MovieDetails>()
            MovieDetailsScreen(movieId = movieDetails.movieId, onBackPress = upPress)
        }

        composable<Navigation.Search> {
            SearchScreen(
                onMovieClick = {
                    navController.navigate(
                        Navigation.MovieDetails(movieId = it.id)
                    )
                },
                onBackPress = upPress
            )
        }

        composable<Navigation.Favorites> {
            FavoritesScreen(
                onMovieClick = {
                    navController.navigate(
                        Navigation.MovieDetails(movieId = it.id)
                    )
                },
                onBackPress = upPress
            )
        }

        composable<Navigation.Watched> {
            WatchedScreen(
                onMovieClick = {
                    navController.navigate(
                        Navigation.MovieDetails(movieId = it.id)
                    )
                },
                onBackPress = upPress
            )
        }

        composable<Navigation.ToWatch> {
            ToWatchScreen(
                onMovieClick = {
                    navController.navigate(
                        Navigation.MovieDetails(movieId = it.id)
                    )
                },
                onBackPress = upPress
            )
        }
    }
}