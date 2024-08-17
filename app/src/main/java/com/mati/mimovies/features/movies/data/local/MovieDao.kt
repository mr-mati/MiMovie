package com.mati.mimovies.features.movies.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.mati.mimovies.features.movies.data.local.entity.MovieEntity
import com.mati.mimovies.features.movies.data.model.Movies

@Dao
interface MovieDao {

    @Insert
    suspend fun insertMovie(movies: MovieEntity)

    @Delete
    suspend fun deleteMovie(movies: MovieEntity)

    @Insert
    suspend fun insertMovieFavoriteList(movies: MovieEntity)

    @Delete
    suspend fun deleteMovieFavoriteList(movies: MovieEntity)

    @Insert
    suspend fun insertMovieWatchList(movies: MovieEntity)

    @Delete
    suspend fun deleteMovieWatchList(movies: MovieEntity)

}