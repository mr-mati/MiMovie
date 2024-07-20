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
import com.mati.mimovies.features.auth.AuthenticationScreen
import com.mati.mimovies.features.auth.signIn.SignInViewModel
import com.mati.mimovies.features.auth.signUp.SignUpViewModel
import com.mati.mimovies.features.movies.presentation.IntroScreen.IntroScreen
import com.mati.mimovies.features.movies.presentation.MovieViewModel
import com.mati.mimovies.features.movies.presentation.PersonViewModel
import com.mati.mimovies.features.movies.presentation.detailScreen.MovieDetailScreen
import com.mati.mimovies.features.movies.presentation.mainScreen.MovieScreen
import com.mati.mimovies.features.movies.presentation.moreMovieScreen.MoreMovieScreen
import com.mati.mimovies.features.movies.presentation.morePersonScreen.MorePersonScreen
import com.mati.mimovies.features.movies.presentation.search.SearchScreen
import com.mati.mimovies.features.profile.presentation.ProfileViewModel
import com.mati.mimovies.features.profile.presentation.editProfile.ProfileEditScreen
import com.mati.mimovies.features.profile.presentation.profileScreen.ProfileScreen
import com.mati.mimovies.utils.MovieNavigationItems

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MovieNavigation(
    signUpViewModel: SignUpViewModel,
    signInViewModel: SignInViewModel,
    mainViewModel: MovieViewModel,
    personViewModel: PersonViewModel,
    profileViewModel: ProfileViewModel,
) {

    val navHostController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navHostController,
        startDestination = MovieNavigationItems.MovieScreen.route
    ) {

        composable(MovieNavigationItems.AuthenticationScreen.route) {
            AuthenticationScreen(
                signUpViewModel = signUpViewModel,
                signInViewModel = signInViewModel,
                navHostController = navHostController
            )
        }

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
            MovieScreen(viewModel = mainViewModel, personViewModel = personViewModel, navHostController = navHostController)
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
            },

            ) {
            MovieDetailScreen(viewModel = mainViewModel, navHostController = navHostController)
        }

        composable(MovieNavigationItems.SearchScreen.route) {
            SearchScreen(viewModel = mainViewModel, personViewModel = personViewModel,navHostController = navHostController)
        }

        composable(MovieNavigationItems.ProfileScreen.route) {
            ProfileScreen(viewModel = mainViewModel, navHostController = navHostController)
        }

        composable(MovieNavigationItems.ProfileEditScreen.route) {
            ProfileEditScreen(viewModel = profileViewModel, navHostController = navHostController)
        }

        composable(MovieNavigationItems.MoreMovieScreen.route) {
            MoreMovieScreen(viewModel = mainViewModel, navHostController = navHostController)
        }

        composable(MovieNavigationItems.MorePersonScreen.route) {
            MorePersonScreen(viewModel = personViewModel, navHostController = navHostController)
        }

    }

}