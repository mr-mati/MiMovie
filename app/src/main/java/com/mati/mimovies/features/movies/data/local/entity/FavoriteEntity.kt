package com.mati.mimovies.features.movies.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoriteMovie")
data class FavoriteEntity(
    val orginal_title: String,
    val title: String,
    val overview: String,
    val poster_path: String,
    val backdrop_path: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)


@Entity(tableName = "watchMovie")
data class WatchEntity(
    val orginal_title: String,
    val title: String,
    val overview: String,
    val poster_path: String,
    val backdrop_path: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)