package com.mati.mimovies.features.movies.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mati.mimovies.common.base.doOnFailure
import com.mati.mimovies.common.base.doOnLoading
import com.mati.mimovies.common.base.doOnSuccess
import com.mati.mimovies.features.movies.data.model.MovieDetail
import com.mati.mimovies.features.movies.data.model.MovieImages
import com.mati.mimovies.features.movies.data.model.Movies
import com.mati.mimovies.features.movies.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val useCase: MovieUseCase) : ViewModel() {

    var ID = mutableIntStateOf(1)
    var title = mutableStateOf("")

    private val _res: MutableState<MovieState> = mutableStateOf(MovieState())
    val res: State<MovieState> = _res

    private val _trending: MutableState<MovieState> = mutableStateOf(MovieState())
    val trending: State<MovieState> = _trending

    private val _you: MutableState<MovieState> = mutableStateOf(MovieState())
    val you: State<MovieState> = _you

    private val _upcoming: MutableState<MovieState> = mutableStateOf(MovieState())
    val upcoming: State<MovieState> = _upcoming

    private val _newShowing: MutableState<MovieState> = mutableStateOf(MovieState())
    val newShowing: State<MovieState> = _newShowing

    private val _top: MutableState<MovieState> = mutableStateOf(MovieState())
    val top: State<MovieState> = _top

    val searchBox = mutableStateOf("")
    private val _search: MutableState<MovieState> = mutableStateOf(MovieState())
    val search: State<MovieState> = _search

    private val _more = mutableStateListOf<Movies.Results>()
    val more: List<Movies.Results> = _more

    private val _movieDetails: MutableState<MovieDetailStats> = mutableStateOf(MovieDetailStats())
    val movieDetails: MutableState<MovieDetailStats> = _movieDetails

    private val _movieImages: MutableState<MovieImagesState> = mutableStateOf(MovieImagesState())
    val movieImages: MutableState<MovieImagesState> = _movieImages

    fun getMoreMovies(id: Int, page: Int, clear: Boolean) {

        if (clear) {
            ID.intValue = id
            _more.clear()
        }

        viewModelScope.launch {
            when (ID.intValue) {

                1 -> {
                    useCase.getMovieYou(page)
                        .doOnSuccess { newMovies ->
                            newMovies!!.forEach {
                                _more.add(it)
                            }
                        }.collect()
                }

                2 -> {
                    useCase.getTopMovies(page)
                        .doOnSuccess { newMovies ->
                            newMovies!!.forEach {
                                _more.add(it)
                            }
                        }.collect()
                }

                3 -> {
                    useCase.getMoviesUpcoming(page)
                        .doOnSuccess { newMovies ->
                            newMovies!!.forEach {
                                _more.add(it)
                            }
                        }.collect()
                }

                4 -> {
                    useCase.getMovies(page)
                        .doOnSuccess { newMovies ->
                            newMovies!!.forEach {
                                _more.add(it)
                            }
                        }.collect()
                }

            }
        }
    }

    fun searchMovies(name: String) {
        viewModelScope.launch {
            useCase.searchMovies(name)
                .doOnSuccess {
                    _search.value = MovieState(data = it!!)
                }
                .doOnFailure {
                    _search.value = MovieState(error = it?.message!!)
                }
                .doOnLoading {
                    _search.value = MovieState(isLoading = true)
                }.collect()
        }
    }

    fun setMovie(data: Movies.Results) {
        getMovieDetail(data.id)
        getMovieImage(data.id)
    }

    private fun getMovieDetail(movieId: Long?) {
        viewModelScope.launch {
            useCase.getMovieDetail(movieId)
                .doOnSuccess {
                    _movieDetails.value = MovieDetailStats(data = it)
                }
                .doOnFailure {
                    _movieDetails.value = MovieDetailStats(error = it?.message!!)
                }
                .doOnLoading {
                    _movieDetails.value = MovieDetailStats(isLoading = true)
                }.collect()
        }
    }

    private fun getMovieImage(movieId: Long?) {
        viewModelScope.launch {
            useCase.getMovieImages(movieId)
                .doOnSuccess {
                    _movieImages.value = MovieImagesState(data = it.backdrops)
                }
                .doOnFailure {
                    _movieImages.value = MovieImagesState(error = it?.message!!)
                }
                .doOnLoading {
                    _movieImages.value = MovieImagesState(isLoading = true)
                }.collect()
        }
    }

    init {
        viewModelScope.launch {
            useCase.getMovies(1)
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
            useCase.getMovieTrending()
                .doOnSuccess {
                    _trending.value = MovieState(data = it!!)
                }
                .doOnFailure {
                    _trending.value = MovieState(error = it?.message!!)
                }
                .doOnLoading {
                    _trending.value = MovieState(isLoading = true)
                }.collect()
        }
    }

    init {
        viewModelScope.launch {
            useCase.getMovieYou(2)
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
            useCase.getMoviesUpcoming(1)
                .doOnSuccess {
                    _upcoming.value = MovieState(data = it!!)
                }
                .doOnFailure {
                    _upcoming.value = MovieState(error = it?.message!!)
                }
                .doOnLoading {
                    _upcoming.value = MovieState(isLoading = true)
                }.collect()
        }
    }

    init {
        viewModelScope.launch {
            useCase.getNewShowing(1)
                .doOnSuccess {
                    _newShowing.value = MovieState(data = it!!)
                }
                .doOnFailure {
                    _newShowing.value = MovieState(error = it?.message!!)
                }
                .doOnLoading {
                    _newShowing.value = MovieState(isLoading = true)
                }.collect()
        }
    }

    init {
        viewModelScope.launch {
            useCase.getTopMovies(1)
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
    var data: List<Movies.Results> = emptyList(),
    val error: String = " ",
    val isLoading: Boolean = false,
)

data class MovieDetailStats(
    var data: MovieDetail? = null,
    val error: String = " ",
    val isLoading: Boolean = false,
)

data class MovieImagesState(
    var data: List<MovieImages.Backdrop> = emptyList(),
    val error: String = " ",
    val isLoading: Boolean = false,
)