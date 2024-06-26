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
import com.mati.mimovies.features.movies.presenter.IntroScreen.IntroScreen
import com.mati.mimovies.features.movies.presenter.MovieViewModel
import com.mati.mimovies.features.movies.presenter.PersonViewModel
import com.mati.mimovies.features.movies.presenter.detailScreen.MovieDetailScreen
import com.mati.mimovies.features.movies.presenter.mainScreen.MovieScreen
import com.mati.mimovies.features.movies.presenter.moreMovieScreen.MoreMovieScreen
import com.mati.mimovies.features.movies.presenter.search.SearchScreen
import com.mati.mimovies.features.profile.presenter.ProfileViewModel
import com.mati.mimovies.features.profile.presenter.editProfile.ProfileEditScreen
import com.mati.mimovies.features.profile.presenter.profileScreen.ProfileScreen
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
            SearchScreen(viewModel = mainViewModel, navHostController = navHostController)
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

    }

}