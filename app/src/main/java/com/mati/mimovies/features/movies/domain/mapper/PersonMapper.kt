package com.mati.mimovies.features.movies.domain.mapper

import com.mati.mimovies.common.base.Mapper
import com.mati.mimovies.features.movies.data.model.Movies
import com.mati.mimovies.features.movies.data.model.Person
import javax.inject.Inject

class PersonMapper @Inject constructor() : Mapper<Person?, List<Person.Results>?> {

    override fun fromMap(from: Person?): List<Person.Results>? {
        return from?.results?.map {
            Person.Results(
                id = it.id,
                gender = it.gender,
                name = it.name,
                original_name = it.original_name,
                popularity = it.popularity,
                profile_path = it.profile_path
            )
        }
    }

}