package com.mati.mimovies.features.movies.data.model

data class MovieImages(
    val backdrops: List<Backdrop>,
    val id: Int,
) {
    data class Backdrop(
        val aspect_ratio: Double? = 0.0,
        val file_path: String? = "",
        val height: Int? = 0,
        val iso_639_1: String? = "",
        val vote_average: Double? = 0.0,
        val vote_count: Int? = 0,
        val width: Int? = 0
    )
}