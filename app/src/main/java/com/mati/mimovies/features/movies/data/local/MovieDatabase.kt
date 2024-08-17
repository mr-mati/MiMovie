package com.mati.mimovies.features.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mati.mimovies.features.movies.data.local.entity.MovieEntity


@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class MovieDatabase: RoomDatabase() {
    abstract val dao: MovieDao
}