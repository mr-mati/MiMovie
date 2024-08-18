package com.mati.mimovies.features.movies.presentation

import com.mati.mimovies.features.movies.data.local.entity.FavoriteEntity
import com.mati.mimovies.features.movies.data.local.entity.WatchEntity
import com.mati.mimovies.features.movies.data.model.MovieDetail
import com.mati.mimovies.features.movies.data.model.MovieImages
import com.mati.mimovies.features.movies.data.model.Movies

data class MovieState(
    val responseMovie: List<Movies.Results> = emptyList(),
    val trendingMovie: List<Movies.Results> = emptyList(),
    val youMovie: List<Movies.Results> = emptyList(),
    val upcoming: List<Movies.Results> = emptyList(),
    val newShowing: List<Movies.Results> = emptyList(),
    val top: List<Movies.Results> = emptyList(),
    val search: List<Movies.Results> = emptyList(),
    val favoriteList: List<FavoriteEntity> = emptyList(),
    val watchList: List<WatchEntity> = emptyList(),
    val error: String = " ",
    val isLoading: Boolean = false
)

data class MovieDetailStats(
    var data: MovieDetail? = null,
    val error: String = " ",
    val isLoading: Boolean = false,
)

data class MovieImagesState(
    var data: List<MovieImages.Backdrop> = emptyList(),
    val error: String = " ",
    val isLoading: Boolean = false,
)
