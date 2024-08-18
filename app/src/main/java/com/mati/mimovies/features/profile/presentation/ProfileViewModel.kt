package com.mati.mimovies.features.profile.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    val favoritesList = mutableStateOf(true)
    val viewedList = mutableStateOf(false)

    val name = mutableStateOf("")
    val number = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")

}