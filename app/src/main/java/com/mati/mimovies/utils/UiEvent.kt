package com.mati.mimovies.utils

sealed class UiEvent {
    data class ShowSnackbar(val message: UiText): UiEvent()
}
