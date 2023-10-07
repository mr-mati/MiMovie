package com.mati.mimovies.utils

sealed class MovieNavigationItems(val route:String){

    object IntroScreen : MovieNavigationItems("introScreen")
    object MovieScreen : MovieNavigationItems("movieScreen")
    object MovieDetails : MovieNavigationItems("movieDetails")

}
