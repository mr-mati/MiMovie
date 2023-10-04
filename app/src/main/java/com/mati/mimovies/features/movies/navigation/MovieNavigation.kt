package com.mati.mimovies.features.movies.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mati.mimovies.features.movies.ui.detailScreen.MovieDetailScreen
import com.mati.mimovies.features.movies.ui.mainScreen.MovieScreen
import com.mati.mimovies.features.movies.ui.MovieViewModel
import com.programming_simplified.movieapp.utils.MovieNavigationItems

@Composable
fun MovieNavigation(
    viewModel: MovieViewModel,
) {

    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = MovieNavigationItems.MovieScreen.route
    ) {
        composable(MovieNavigationItems.MovieScreen.route) {
            MovieScreen(viewModel = viewModel, navHostController = navHostController)
        }
        composable(MovieNavigationItems.MovieDetails.route) {
            MovieDetailScreen(viewModel, navHostController = navHostController)
        }
    }

}