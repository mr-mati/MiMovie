package com.mati.mimovies.data.repository

import com.mati.mimovies.common.base.ApiState
import com.mati.mimovies.common.base.BaseRepository
import com.mati.mimovies.data.model.Movies
import com.mati.mimovies.data.network.ApiService
import com.mati.mimovies.features.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(

    private val apiService: ApiService

) : MovieRepository, BaseRepository() {

    override suspend fun getMovies(): Flow<ApiState<Movies>> = safeApiCall {
        apiService.getMoviesList()
    }


}