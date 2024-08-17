package com.mati.mimovies.features.movies.presentation

sealed class MovieEvent {
    data class GetMovieDetail(val movieId: Long) : MovieEvent()
    data class GetMovieImage(val movieId: Long) : MovieEvent()
    data class GetMoreMovies(val id: Int, val page: Int, val clear: Boolean) : MovieEvent()
    data class GetSearchMovie(val name: String) : MovieEvent()
}