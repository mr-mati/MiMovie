package com.mati.mimovies.features.movies.domain.usecase

import com.mati.mimovies.common.base.ApiState
import com.mati.mimovies.common.base.map
import com.mati.mimovies.features.movies.data.model.Person
import com.mati.mimovies.features.movies.domain.mapper.PersonMapper
import com.mati.mimovies.features.movies.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PersonUseCase @Inject constructor(
    private val repo: PersonRepository,
    private val mapper: PersonMapper,
) {
    suspend fun getPersonPopular(page: Int?): Flow<ApiState<List<Person.Results>?>> {
        return repo.getPersonPopular(page).map { results ->
            results.map {
                mapper.fromMap(it)
            }
        }
    }
}