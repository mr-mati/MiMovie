package com.mati.mimovies.features.movies.domain.usecase

import com.mati.mimovies.common.base.ApiState
import com.mati.mimovies.common.base.map
import com.mati.mimovies.data.model.Movies
import com.mati.mimovies.features.movies.domain.mapper.MovieMapper
import com.mati.mimovies.features.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val repo: MovieRepository,
    private val mapper: MovieMapper,
) {

    suspend fun getMovies(): Flow<ApiState<List<Movies.Results>?>> {
        return repo.getMovies().map { results ->
            results.map {
                mapper.fromMap(it)
            }
        }
    }

    suspend fun getMovieYou(): Flow<ApiState<List<Movies.Results>?>> {
        return repo.getMovieYou().map { results ->
            results.map {
                mapper.fromMap(it)
            }
        }
    }

    suspend fun getMoviesUpcoming(): Flow<ApiState<List<Movies.Results>?>> {
        return repo.getMoviesUpcoming().map { results ->
            results.map {
                mapper.fromMap(it)
            }
        }
    }

    suspend fun getTopMovies(): Flow<ApiState<List<Movies.Results>?>> {
        return repo.getMoviesTop().map { results ->
            results.map {
                mapper.fromMap(it)
            }
        }
    }

    suspend fun searchMovies(name: String): Flow<ApiState<List<Movies.Results>?>> {
        return repo.searchMovies(name).map { results ->
            results.map {
                mapper.fromMap(it)
            }
        }
    }

}