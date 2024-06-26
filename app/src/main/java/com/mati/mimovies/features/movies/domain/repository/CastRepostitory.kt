package com.mati.mimovies.features.movies.domain.repository

import com.mati.mimovies.common.base.ApiState
import com.mati.mimovies.features.movies.data.model.Person
import kotlinx.coroutines.flow.Flow

interface PersonRepository {

    suspend fun getPersonPopular(page: Int?): Flow<ApiState<Person>>

}