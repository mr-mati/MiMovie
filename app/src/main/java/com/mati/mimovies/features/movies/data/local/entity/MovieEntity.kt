package com.mati.mimovies.features.movies.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    val orginal_title: String,
    val title: String,
    val overview: String,
    val poster_path: String,
    val backdrop_path: String,
    @PrimaryKey val id: Int? = null
)