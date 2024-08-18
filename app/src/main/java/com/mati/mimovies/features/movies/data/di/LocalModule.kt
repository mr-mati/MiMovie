package com.mati.mimovies.features.movies.data.di

import android.app.Application
import androidx.room.Room
import com.mati.mimovies.features.movies.data.local.MovieDao
import com.mati.mimovies.features.movies.data.local.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideFavoriteDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(
            app,
            MovieDatabase::class.java,
            "Favorite_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.dao
    }

}