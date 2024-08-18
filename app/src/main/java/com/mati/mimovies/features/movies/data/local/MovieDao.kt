package com.mati.mimovies.features.movies.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mati.mimovies.features.movies.data.local.entity.FavoriteEntity
import com.mati.mimovies.features.movies.data.local.entity.WatchEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movies: FavoriteEntity)

    @Delete
    suspend fun deleteMovie(movies: FavoriteEntity)

    @Query("SELECT * FROM favoriteMovie")
    suspend fun getMovieFavoriteList(): List<FavoriteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieFavoriteList(movies: FavoriteEntity)

    @Delete
    suspend fun deleteMovieFavoriteList(movies: FavoriteEntity)

    @Query("SELECT * FROM watchMovie")
    suspend fun getMovieWatchList(): List<WatchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieWatchList(movies: WatchEntity)

    @Delete
    suspend fun deleteMovieWatchList(movies: WatchEntity)

}