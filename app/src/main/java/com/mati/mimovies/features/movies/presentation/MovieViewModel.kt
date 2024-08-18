package com.mati.mimovies.features.movies.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mati.mimovies.common.base.doOnFailure
import com.mati.mimovies.common.base.doOnLoading
import com.mati.mimovies.common.base.doOnSuccess
import com.mati.mimovies.features.movies.data.model.Movies
import com.mati.mimovies.features.movies.domain.usecase.MovieUseCase
import com.mati.mimovies.utils.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val useCase: MovieUseCase) : ViewModel() {

    var ID = mutableIntStateOf(1)
    var title = mutableStateOf("")

    val searchBox = mutableStateOf("")

    var state by mutableStateOf(MovieState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _more = mutableStateListOf<Movies.Results>()
    val more: List<Movies.Results> = _more

    private val _movieDetails: MutableState<MovieDetailStats> = mutableStateOf(MovieDetailStats())
    val movieDetails: MutableState<MovieDetailStats> = _movieDetails

    private val _movieImages: MutableState<MovieImagesState> = mutableStateOf(MovieImagesState())
    val movieImages: MutableState<MovieImagesState> = _movieImages

    fun onEvent(event: MovieEvent) {
        when (event) {
            is MovieEvent.GetMovieDetail -> {
                viewModelScope.launch {
                    useCase.getMovieDetail(event.movieId)
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

            is MovieEvent.GetMovieImage -> {
                viewModelScope.launch {
                    useCase.getMovieImages(event.movieId)
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

            is MovieEvent.GetMoreMovies -> {
                viewModelScope.launch {

                    if (event.clear) {
                        ID.intValue = event.id
                        _more.clear()
                    }
                    when (ID.intValue) {

                        1 -> {
                            useCase.getMovieYou(event.page)
                                .doOnSuccess { newMovies ->
                                    newMovies!!.forEach {
                                        _more.add(it)
                                    }
                                }.collect()
                        }

                        2 -> {
                            useCase.getTopMovies(event.page)
                                .doOnSuccess { newMovies ->
                                    newMovies!!.forEach {
                                        _more.add(it)
                                    }
                                }.collect()
                        }

                        3 -> {
                            useCase.getMoviesUpcoming(event.page)
                                .doOnSuccess { newMovies ->
                                    newMovies!!.forEach {
                                        _more.add(it)
                                    }
                                }.collect()
                        }

                        4 -> {
                            useCase.getMovies(event.page)
                                .doOnSuccess { newMovies ->
                                    newMovies!!.forEach {
                                        _more.add(it)
                                    }
                                }.collect()
                        }

                    }
                }
            }

            is MovieEvent.GetSearchMovie -> {
                viewModelScope.launch {
                    useCase.searchMovies(event.name)
                        .doOnSuccess {
                            state = state.copy(
                                search = checkNotNull(it),
                                isLoading = false
                            )
                        }
                        .doOnFailure {
                            state = state.copy(
                                error = checkNotNull(it?.message),
                                isLoading = false

                            )
                        }
                        .doOnLoading {
                            state = state.copy(
                                isLoading = true
                            )
                        }.collect()
                }
            }

            is MovieEvent.GetFavoriteList -> {
                viewModelScope.launch {
                    state = state.copy(
                        favoriteList = useCase.getMovieFavoriteList()
                    )
                }
            }

            is MovieEvent.AddMovieToFavorite -> {
                viewModelScope.launch {
                    useCase.insertMovieFavoriteList(event.movie)
                }
            }

            is MovieEvent.DeleteMovieToFavorite -> {
                viewModelScope.launch {
                    useCase.deleteMovieFavoriteList(event.movie)
                }
            }

            is MovieEvent.GetWatchList -> {
                viewModelScope.launch {
                    state = state.copy(
                        watchList = useCase.getMovieWatchList()
                    )
                }
            }

            is MovieEvent.AddMovieToWatch -> {
                viewModelScope.launch {
                    useCase.insertMovieWatchList(event.movie)
                }
            }

            is MovieEvent.DeleteMovieToWatch -> {
                viewModelScope.launch {
                    useCase.deleteMovieWatchList(event.movie)
                }
            }

        }
    }

    init {
        viewModelScope.launch {
            useCase.getMovies(1)
                .doOnSuccess {
                    state = state.copy(
                        responseMovie = checkNotNull(it)
                    )
                }
                .doOnFailure {
                    state = state.copy(
                        error = checkNotNull(it?.message)
                    )
                }
                .doOnLoading {
                    state = state.copy(
                        isLoading = true
                    )
                }.collect()
        }
    }

    init {
        viewModelScope.launch {
            useCase.getMovieTrending()
                .doOnSuccess {
                    state = state.copy(
                        trendingMovie = checkNotNull(it)
                    )
                }
                .doOnFailure {
                    state = state.copy(
                        error = checkNotNull(it?.message)
                    )
                }
                .doOnLoading {
                    state = state.copy(
                        isLoading = true
                    )
                }.collect()
        }
    }

    init {
        viewModelScope.launch {
            useCase.getMovieYou(2)
                .doOnSuccess {
                    state = state.copy(
                        youMovie = checkNotNull(it)
                    )
                }
                .doOnFailure {
                    state = state.copy(
                        error = checkNotNull(it?.message)
                    )
                }
                .doOnLoading {
                    state = state.copy(
                        isLoading = true
                    )
                }.collect()
        }
    }

    init {
        viewModelScope.launch {
            useCase.getMoviesUpcoming(1)
                .doOnSuccess {
                    state = state.copy(
                        upcoming = checkNotNull(it)
                    )
                }
                .doOnFailure {
                    state = state.copy(
                        error = checkNotNull(it?.message)
                    )
                }
                .doOnLoading {
                    state = state.copy(
                        isLoading = true
                    )
                }.collect()
        }
    }

    init {
        viewModelScope.launch {
            useCase.getNewShowing(1)
                .doOnSuccess {
                    state = state.copy(
                        newShowing = checkNotNull(it)
                    )
                }
                .doOnFailure {
                    state = state.copy(
                        error = checkNotNull(it?.message)
                    )
                }
                .doOnLoading {
                    state = state.copy(
                        isLoading = true
                    )
                }.collect()
        }
    }

    init {
        viewModelScope.launch {
            useCase.getTopMovies(1)
                .doOnSuccess {
                    state = state.copy(
                        top = checkNotNull(it)
                    )
                }
                .doOnFailure {
                    state = state.copy(
                        error = checkNotNull(it?.message)
                    )
                }
                .doOnLoading {
                    state = state.copy(
                        isLoading = true
                    )
                }.collect()
        }
    }

}