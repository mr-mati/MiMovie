package com.mati.mimovies.features.auth.signIn

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {

    val userID = mutableStateOf("")
    val password = mutableStateOf("")

}