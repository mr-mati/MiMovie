package com.mati.mimovies.features.movies.domain.mapper

import com.mati.mimovies.common.base.Mapper
import com.mati.mimovies.data.model.Movies
import javax.inject.Inject

class MovieMapper @Inject constructor() : Mapper<Movies?, List<Movies.Results>?> {


    override fun fromMap(from: Movies?): List<Movies.Results>? {
        return from?.results?.map {
            Movies.Results(
                id = it.id,
                orginal_title = it.orginal_title,
                overview = it.overview,
                poster_path = it.poster_path,
                vote_average = it.vote_average

            )
        }
    }


}