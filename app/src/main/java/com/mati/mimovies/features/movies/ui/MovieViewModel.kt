package com.mati.mimovies.features.movies.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mati.mimovies.common.base.doOnFailure
import com.mati.mimovies.common.base.doOnLoading
import com.mati.mimovies.common.base.doOnSuccess
import com.mati.mimovies.data.model.Movies
import com.mati.mimovies.features.movies.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(val useCase: MovieUseCase) : ViewModel() {

    private val _res: MutableState<MovieState> = mutableStateOf(MovieState())
    val res: State<MovieState> = _res

    val _you: MutableState<MovieState> = mutableStateOf(MovieState())
    val you: State<MovieState> = _you

    val _top: MutableState<MovieState> = mutableStateOf(MovieState())
    val top: State<MovieState> = _top

    private val _movieDetails:MutableState<Movies.Results> = mutableStateOf(Movies.Results())
    val movieDetails:MutableState<Movies.Results> = _movieDetails

    fun setMovie(data:Movies.Results){
        _movieDetails.value = data
    }

    init {
        viewModelScope.launch {
            useCase.getMovies()
                .doOnSuccess {
                    _res.value = MovieState(data = it!!)
                }
                .doOnFailure {
                    _res.value = MovieState(error = it?.message!!)
                }
                .doOnLoading {
                    _res.value = MovieState(isLoading = true)
                }.collect()
        }
    }

    init {
        viewModelScope.launch {
            useCase.getMovieYou()
                .doOnSuccess {
                    _you.value = MovieState(data = it!!)
                }
                .doOnFailure {
                    _you.value = MovieState(error = it?.message!!)
                }
                .doOnLoading {
                    _you.value = MovieState(isLoading = true)
                }.collect()
        }
    }

    init {
        viewModelScope.launch {
            useCase.getTopMovies()
                .doOnSuccess {
                    _top.value = MovieState(data = it!!)
                }
                .doOnFailure {
                    _top.value = MovieState(error = it?.message!!)
                }
                .doOnLoading {
                    _top.value = MovieState(isLoading = true)
                }.collect()
        }
    }

}


data class MovieState(
    val data: List<Movies.Results> = emptyList(),
    val error: String = " ",
    val isLoading: Boolean = false,
)