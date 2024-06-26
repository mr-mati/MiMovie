package com.mati.mimovies.features.movies.presenter

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
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

    private val _personPopular: MutableState<PersonStats> = mutableStateOf(PersonStats())
    val personPopular: State<PersonStats> = _personPopular


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


}

data class PersonStats(
    var data: List<Person.Results> = emptyList(),
    val error: String = " ",
    val isLoading: Boolean = false,
)