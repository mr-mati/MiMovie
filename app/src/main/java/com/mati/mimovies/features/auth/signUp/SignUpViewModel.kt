package com.mati.mimovies.features.auth.signUp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {

    val name = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")

}