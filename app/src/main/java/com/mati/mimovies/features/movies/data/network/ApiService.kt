package com.mati.mimovies.features.movies.data.network

import com.mati.mimovies.features.movies.data.model.Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/"
        const val BASE_POSTER_URL = "https://image.tmdb.org/t/p/w500/"
    }

    @GET("3/discover/movie?api_key=2c9c2076db9a90f62f31993f6588235e")
    suspend fun getMoviesList(): Response<Movies>

    @GET("3/movie/top_rated?api_key=2c9c2076db9a90f62f31993f6588235e&page=2")
    suspend fun getMovieYou(): Response<Movies>

    @GET("3/movie/upcoming?api_key=2c9c2076db9a90f62f31993f6588235e")
    suspend fun getMoviesUpcoming(): Response<Movies>

    @GET("3/movie/top_rated?api_key=2c9c2076db9a90f62f31993f6588235e")
    suspend fun getMoviesTop(): Response<Movies>

    @GET("3/search/movie?api_key=2c9c2076db9a90f62f31993f6588235e")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
    ): Response<Movies>

}