package com.mati.mimovies.features.movies.domain.repository

import com.mati.mimovies.common.base.ApiState
import com.mati.mimovies.features.movies.data.local.entity.FavoriteEntity
import com.mati.mimovies.features.movies.data.local.entity.WatchEntity
import com.mati.mimovies.features.movies.data.model.MovieDetail
import com.mati.mimovies.features.movies.data.model.MovieImages
import com.mati.mimovies.features.movies.data.model.Movies
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovies(page: Int?): Flow<ApiState<Movies>>

    suspend fun getMovieTrending(): Flow<ApiState<Movies>>

    suspend fun getMovieYou(page: Int?): Flow<ApiState<Movies>>

    suspend fun getMoviesUpcoming(page: Int?): Flow<ApiState<Movies>>

    suspend fun getMoviesTop(page: Int?): Flow<ApiState<Movies>>

    suspend fun getNewShowing(page: Int?): Flow<ApiState<Movies>>

    suspend fun searchMovies(name: String): Flow<ApiState<Movies>>

    suspend fun getMovieImages(movieId: Long?): Flow<ApiState<MovieImages>>

    suspend fun getMovieDetail(movieId: Long?): Flow<ApiState<MovieDetail>>

    suspend fun getMovieFavoriteList(): List<FavoriteEntity>

    suspend fun insertMovieFavoriteList(movie: MovieDetail)

    suspend fun deleteMovieFavoriteList(movie: MovieDetail)

    suspend fun getMovieWatchList(): List<WatchEntity>

    suspend fun insertMovieWatchList(movie: MovieDetail)

    suspend fun deleteMovieWatchList(movie: MovieDetail)
}