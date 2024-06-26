package com.mati.mimovies.features.movies.data.repository

import com.mati.mimovies.common.base.ApiState
import com.mati.mimovies.common.base.BaseRepository
import com.mati.mimovies.features.movies.data.model.Person
import com.mati.mimovies.features.movies.data.network.ApiService
import com.mati.mimovies.features.movies.domain.repository.PersonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersonRepositoryImpl @Inject constructor(

    private val apiService: ApiService,

    ) : PersonRepository, BaseRepository() {

    override suspend fun getPersonPopular(page: Int?): Flow<ApiState<Person>> = safeApiCall {
        apiService.getPersonPopular(page)
    }

}