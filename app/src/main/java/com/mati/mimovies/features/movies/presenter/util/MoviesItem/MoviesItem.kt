package com.mati.mimovies.features.movies.presenter.util.MoviesItem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.mati.mimovies.features.movies.data.model.Movies
import com.mati.mimovies.features.movies.data.network.ApiService

@Composable
fun MoviesItem(
    results: Movies.Results, onClick: () -> Unit,
) {
    Card(modifier = Modifier
        .width(150.dp)
        .height(180.dp)
        .padding(end = 4.dp)
        .clickable() {
            onClick()
        }
        .padding(8.dp), elevation = CardDefaults.cardElevation(
        defaultElevation = 3.dp
    ), colors = CardDefaults.cardColors(
        containerColor = Color.Gray,
    ), shape = RoundedCornerShape(8.dp))
    {
        val imagePainter = rememberImagePainter(
            data = "${ApiService.BASE_POSTER_URL}${results.poster_path}",
            builder = {
                crossfade(true)
            }
        )

        Image(
            painter = imagePainter,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Gray)
        )
    }
}

@Composable
fun MoviesItemEnabled(
    results: Movies.Results, onClick: () -> Unit, enabled: Boolean,
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(180.dp)
            .padding(end = 4.dp)
            .clickable(enabled = enabled) {
                onClick()
            }
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Gray,
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Image(
            rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${ApiService.BASE_POSTER_URL}${results.poster_path}")
                    .build()
            ),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Gray)
        )
    }
}