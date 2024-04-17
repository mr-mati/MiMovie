package com.mati.mimovies.navigation

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.mati.mimovies.features.movies.presenter.IntroScreen.IntroScreen
import com.mati.mimovies.features.movies.presenter.MovieViewModel
import com.mati.mimovies.features.movies.presenter.detailScreen.MovieDetailScreen
import com.mati.mimovies.features.movies.presenter.mainScreen.MovieScreen
import com.mati.mimovies.features.profile.presenter.ProfileScreen
import com.mati.mimovies.features.movies.presenter.search.SearchScreen
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
        composable(
            MovieNavigationItems.IntroScreen.route,
        ) {
            IntroScreen(navHostController = navHostController)
        }
        composable(MovieNavigationItems.MovieScreen.route,
            enterTransition = {
                slideInVertically(
                    initialOffsetY = { 50 },
                    animationSpec = tween(1000)
                )
            },
            exitTransition = {
                slideOutVertically(
                    targetOffsetY = { 50 },
                    animationSpec = tween(2500)
                )
            }
        ) {
            MovieScreen(viewModel = viewModel, navHostController = navHostController)
        }

        composable(
            MovieNavigationItems.MovieDetails.route,
            enterTransition = {
                if (navHostController.previousBackStackEntry?.destination?.route == MovieNavigationItems.MovieScreen.route) {
                    slideInVertically(
                        initialOffsetY = { 2500 },
                        animationSpec = tween(2000)
                    )
                } else {
                    null
                }
            },
            exitTransition = {
                if (navHostController.currentBackStackEntry?.destination?.route == MovieNavigationItems.MovieScreen.route) {
                    slideOutVertically(
                        targetOffsetY = { 2500 },
                        animationSpec = tween(1500)
                    )
                } else {
                    null
                }
            }
        ) {
            MovieDetailScreen(viewModel = viewModel, navHostController = navHostController)
        }

        composable(MovieNavigationItems.SearchScreen.route) {
            SearchScreen(viewModel = viewModel, navHostController = navHostController)
        }

        composable(MovieNavigationItems.ProfileScreen.route) {
            ProfileScreen(viewModel = viewModel, navHostController = navHostController)
        }

    }

}