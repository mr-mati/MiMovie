package com.mati.mimovies.features.movies.domain.repository

import com.mati.mimovies.common.base.ApiState
import com.mati.mimovies.features.movies.data.model.MovieImages
import com.mati.mimovies.features.movies.data.model.Movies
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovies(page: Int?): Flow<ApiState<Movies>>

    suspend fun getMovieYou(page: Int?): Flow<ApiState<Movies>>

    suspend fun getMoviesUpcoming(page: Int?): Flow<ApiState<Movies>>

    suspend fun getMoviesTop(page: Int?): Flow<ApiState<Movies>>

    suspend fun getNewShowing(page: Int?): Flow<ApiState<Movies>>

    suspend fun searchMovies(name: String): Flow<ApiState<Movies>>

    suspend fun getMovieImages(movieId: Long?): Flow<ApiState<MovieImages>>

}