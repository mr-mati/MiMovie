package com.mati.mimovies.features.movies.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.mati.mimovies.features.movies.ui.IntroScreen.IntroScreen
import com.mati.mimovies.features.movies.ui.MovieViewModel
import com.mati.mimovies.features.movies.ui.detailScreen.MovieDetailScreen
import com.mati.mimovies.features.movies.ui.mainScreen.MovieScreen
import com.mati.mimovies.utils.MovieNavigationItems

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MovieNavigation(
    viewModel: MovieViewModel,
) {

    val navHostController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navHostController,
        startDestination = MovieNavigationItems.IntroScreen.route
    ) {
        composable(MovieNavigationItems.IntroScreen.route,
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { -3000 },
                    animationSpec = tween(5000)
                )
            }
            ) {
            IntroScreen(navHostController = navHostController)
        }
        composable(MovieNavigationItems.MovieScreen.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { 50 },
                    animationSpec = tween(3000)
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { 50 },
                    animationSpec = tween(5000)
                )
            }
        ) {
            MovieScreen(viewModel = viewModel, navHostController = navHostController)
        }
        composable(MovieNavigationItems.MovieDetails.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { 3000 },
                    animationSpec = tween(5000)
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { 3000 },
                    animationSpec = tween(5000)
                )
            }
        ) {
            MovieDetailScreen(viewModel, navHostController = navHostController)
        }
    }

}