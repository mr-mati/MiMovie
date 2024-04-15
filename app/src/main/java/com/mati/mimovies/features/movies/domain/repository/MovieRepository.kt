package com.mati.mimovies.features.movies.domain.repository

import com.mati.mimovies.common.base.ApiState
import com.mati.mimovies.data.model.Movies
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovies(): Flow<ApiState<Movies>>

    suspend fun getMovieYou(): Flow<ApiState<Movies>>

    suspend fun getMoviesTop(): Flow<ApiState<Movies>>

    suspend fun searchMovies(name: String): Flow<ApiState<Movies>>

}