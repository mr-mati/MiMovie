package com.mati.mimovies.features.movies.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mati.mimovies.common.base.doOnFailure
import com.mati.mimovies.common.base.doOnLoading
import com.mati.mimovies.common.base.doOnSuccess
import com.mati.mimovies.features.movies.data.model.Person
import com.mati.mimovies.features.movies.domain.usecase.PersonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(val useCase: PersonUseCase) : ViewModel() {

    var title =  mutableStateOf("")

    private val _personPopular: MutableState<PersonStats> = mutableStateOf(PersonStats())
    val personPopular: State<PersonStats> = _personPopular

    private val _morePerson = mutableStateListOf<Person.Results>()
    val morePerson: List<Person.Results> = _morePerson

    private val _search: MutableState<PersonStats> = mutableStateOf(PersonStats())
    val search: State<PersonStats> = _search

    fun getMorePerson(page: Int, clear: Boolean) {
        if (clear) {
            _morePerson.clear()
        }
        viewModelScope.launch {
            useCase.getPersonPopular(page)
                .doOnSuccess { newList ->
                    newList!!.forEach {
                        _morePerson.add(it)
                    }
                }.collect()
        }
    }

    init {
        viewModelScope.launch {
            useCase.getPersonPopular(1)
                .doOnSuccess {
                    _personPopular.value = PersonStats(data = it!!)
                }
                .doOnFailure {
                    _personPopular.value = PersonStats(error = it?.message!!)
                }
                .doOnLoading {
                    _personPopular.value = PersonStats(isLoading = true)
                }.collect()
        }
    }

    fun searchPerson(name: String) {
        viewModelScope.launch {
            useCase.searchPerson(name)
                .doOnSuccess {
                    _search.value = PersonStats(data = it!!)
                }
                .doOnFailure {
                    _search.value = PersonStats(error = it?.message!!)
                }
                .doOnLoading {
                    _search.value = PersonStats(isLoading = true)
                }.collect()
        }
    }

}

data class PersonStats(
    var data: List<Person.Results> = emptyList(),
    val error: String = " ",
    val isLoading: Boolean = false,
)