package com.merkost.lumi.presentation.screens.navigation

import MovieDetailsScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.merkost.lumi.presentation.screens.movies.MoviesScreen

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
            MoviesScreen(onMovieClick = {
                navController.navigate(
                    Navigation.MovieDetails(movieId = it.id)
                )
            })
        }

        composable<Navigation.MovieDetails> {
            MovieDetailsScreen(onBackPress = upPress)
        }
    }
}