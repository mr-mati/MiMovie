package com.mati.mimovies.features.movies.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.mati.mimovies.Greeting
import com.mati.mimovies.ui.theme.MiMoviesTheme

@Composable
fun MovieScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    ){

    Card(
        modifier = Modifier.fillMaxSize(),

    ){}

}


@Preview(showBackground = true)
@Composable
fun MovieScreenPreview() {
    MiMoviesTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Greeting("Android")
        }
    }
}