package com.mati.mimovies.features.movies.ui.searchScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mati.mimovies.features.movies.ui.MovieViewModel

@Composable
fun SearchScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    navHostController: NavHostController,
) {

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Search Screen")
    }


}