package com.mati.mimovies.features.movies.data.di

import com.mati.mimovies.features.movies.data.repository.MovieRepositoryImpl
import com.mati.mimovies.features.movies.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providerMovieRepository(

        repo: MovieRepositoryImpl

    ):MovieRepository


}