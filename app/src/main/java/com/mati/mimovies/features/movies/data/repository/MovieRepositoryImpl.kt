package com.mati.mimovies.features.movies.data.repository

import com.mati.mimovies.common.base.ApiState
import com.mati.mimovies.common.base.BaseRepository
import com.mati.mimovies.features.movies.data.local.MovieDao
import com.mati.mimovies.features.movies.data.local.entity.FavoriteEntity
import com.mati.mimovies.features.movies.data.local.entity.WatchEntity
import com.mati.mimovies.features.movies.data.model.MovieDetail
import com.mati.mimovies.features.movies.data.model.MovieImages
import com.mati.mimovies.features.movies.data.model.Movies
import com.mati.mimovies.features.movies.data.network.ApiService
import com.mati.mimovies.features.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val dao: MovieDao,
    private val apiService: ApiService,

    ) : MovieRepository, BaseRepository() {

    override suspend fun getMovies(page: Int?): Flow<ApiState<Movies>> = safeApiCall {
        apiService.getMoviesList(page)
    }

    override suspend fun getMovieTrending(): Flow<ApiState<Movies>> = safeApiCall {
        apiService.getMovieTrending()
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

    override suspend fun getNewShowing(page: Int?): Flow<ApiState<Movies>> = safeApiCall {
        apiService.getNewShowing(page)
    }

    override suspend fun searchMovies(name: String): Flow<ApiState<Movies>> = safeApiCall {
        apiService.searchMovies(name, 1)
    }

    override suspend fun getMovieImages(movieId: Long?): Flow<ApiState<MovieImages>> = safeApiCall {
        apiService.getMovieImages(movieId)
    }

    override suspend fun getMovieDetail(movieId: Long?): Flow<ApiState<MovieDetail>> = safeApiCall {
        apiService.getMovieDetail(movieId)
    }

    override suspend fun getMovieFavoriteList(): List<FavoriteEntity> {
        return dao.getMovieFavoriteList()
    }

    override suspend fun insertMovieFavoriteList(movie: MovieDetail) {
        dao.insertMovieFavoriteList(
            FavoriteEntity(
                movie.original_title,
                movie.title,
                movie.overview,
                movie.poster_path,
                movie.backdrop_path,
                movie.id.toInt()
            )
        )
    }

    override suspend fun deleteMovieFavoriteList(movie: MovieDetail) {
        dao.deleteMovieFavoriteList(
            FavoriteEntity(
                movie.original_title,
                movie.title,
                movie.overview,
                movie.poster_path,
                movie.backdrop_path,
                movie.id.toInt()
            )
        )
    }

    override suspend fun getMovieWatchList(): List<WatchEntity> {
        return dao.getMovieWatchList()
    }

    override suspend fun insertMovieWatchList(movie: MovieDetail) {
        dao.insertMovieWatchList(
            WatchEntity(
                movie.original_title,
                movie.title,
                movie.overview,
                movie.poster_path,
                movie.backdrop_path,
                movie.id.toInt()
            )
        )
    }

    override suspend fun deleteMovieWatchList(movie: MovieDetail) {
        dao.deleteMovieWatchList(
            WatchEntity(
                movie.original_title,
                movie.title,
                movie.overview,
                movie.poster_path,
                movie.backdrop_path,
                movie.id.toInt()
            )
        )
    }

}