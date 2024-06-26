package com.mati.mimovies.features.movies.data.network

import com.mati.mimovies.features.movies.data.model.Movies
import com.mati.mimovies.features.movies.data.model.Person
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/"
        const val BASE_POSTER_URL = "https://image.tmdb.org/t/p/w500/"
    }

    @GET("3/discover/movie?api_key=2c9c2076db9a90f62f31993f6588235e")
    suspend fun getMoviesList(
        @Query("page") page: Int? = 1
    ): Response<Movies>

    @GET("3/movie/top_rated?api_key=2c9c2076db9a90f62f31993f6588235e")
    suspend fun getMovieYou(
        @Query("page") page: Int? = 2
    ): Response<Movies>

    @GET("3/movie/upcoming?api_key=2c9c2076db9a90f62f31993f6588235e")
    suspend fun getMoviesUpcoming(
        @Query("page") page: Int? = 1
    ): Response<Movies>

    @GET("3/movie/top_rated?api_key=2c9c2076db9a90f62f31993f6588235e")
    suspend fun getMoviesTop(
        @Query("page") page: Int? = 1
    ): Response<Movies>

    @GET("3/movie/now_playing?api_key=2c9c2076db9a90f62f31993f6588235e")
    suspend fun getNewShowing(
        @Query("page") page: Int? = 1
    ): Response<Movies>

    @GET("3/person/popular?api_key=2c9c2076db9a90f62f31993f6588235e")
    suspend fun getPersonPopular(
        @Query("page") page: Int? = 1
    ): Response<Person>

    @GET("3/search/movie?api_key=2c9c2076db9a90f62f31993f6588235e")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Response<Movies>

}