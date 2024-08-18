package com.mati.mimovies.features.movies.presentation

import com.mati.mimovies.features.movies.data.model.MovieDetail
import com.mati.mimovies.features.movies.data.model.Movies

sealed class MovieEvent {

    data class GetMovieDetail(val movieId: Long) : MovieEvent()
    data class GetMovieImage(val movieId: Long) : MovieEvent()

    data class GetMoreMovies(val id: Int, val page: Int, val clear: Boolean) : MovieEvent()
    data class GetSearchMovie(val name: String) : MovieEvent()

    object GetFavoriteList : MovieEvent()
    data class AddMovieToFavorite(val movie: MovieDetail) : MovieEvent()
    data class DeleteMovieToFavorite(val movie: MovieDetail) : MovieEvent()

    object GetWatchList : MovieEvent()
    data class AddMovieToWatch(val movie: MovieDetail) : MovieEvent()
    data class DeleteMovieToWatch(val movie: MovieDetail) : MovieEvent()

}