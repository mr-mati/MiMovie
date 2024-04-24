package com.mati.mimovies.utils

sealed class MovieNavigationItems(val route:String){

    object SignUpScreen : MovieNavigationItems("signUpScreen")
    object SignInScreen : MovieNavigationItems("signInScreen")
    object IntroScreen : MovieNavigationItems("introScreen")
    object MovieScreen : MovieNavigationItems("movieScreen")
    object MovieDetails : MovieNavigationItems("movieDetails")
    object SearchScreen : MovieNavigationItems("searchScreen")
    object ProfileScreen : MovieNavigationItems("profileScreen")
    object ProfileEditScreen : MovieNavigationItems("profileEditScreen")

}
