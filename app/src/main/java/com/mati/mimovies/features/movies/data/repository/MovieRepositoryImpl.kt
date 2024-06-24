package com.mati.mimovies.features.movies.data.repository

import com.mati.mimovies.common.base.ApiState
import com.mati.mimovies.common.base.BaseRepository
import com.mati.mimovies.features.movies.data.model.Movies
import com.mati.mimovies.features.movies.data.network.ApiService
import com.mati.mimovies.features.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(

    private val apiService: ApiService,

    ) : MovieRepository, BaseRepository() {

    override suspend fun getMovies(page: Int?): Flow<ApiState<Movies>> = safeApiCall {
        apiService.getMoviesList(page)
    }

    override suspend fun getMovieYou(page: Int?): Flow<ApiState<Movies>> = safeApiCall {
        apiService.getMovieYou(page)
    }

    override suspend fun getMoviesUpcoming(page: Int?): Flow<ApiState<Movies>> = safeApiCall {
        apiService.getMoviesUpcoming(page)
    }

    override suspend fun getMoviesTop(page: Int?): Flow<ApiState<Movies>> = safeApiCall {
        apiService.getMoviesTop(page)
    }

    override suspend fun searchMovies(name: String): Flow<ApiState<Movies>> = safeApiCall {
        apiService.searchMovies(name, 1)
    }

}