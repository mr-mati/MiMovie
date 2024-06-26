package com.mati.mimovies.features.movies.presenter.util.MoviesItem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.mati.mimovies.R
import com.mati.mimovies.features.movies.data.model.Movies
import com.mati.mimovies.features.movies.data.network.ApiService
import com.mati.mimovies.ui.theme.Blue
import com.mati.mimovies.ui.theme.BlueLight

@Composable
fun MoviesItem(
    results: Movies.Results, onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
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

        Row(
            modifier = Modifier.padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                Icons.Default.Star, null,
                tint = Blue,
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = "${results.vote_average}/10 IMDb",
                style = TextStyle(
                    textAlign = TextAlign.Justify,
                    color = BlueLight,
                    fontFamily = FontFamily(Font(R.font.primary_regular)),
                    fontSize = 10.sp
                )
            )
        }

        val displayText = if (results.title?.length!! > 12) {
            results.title.substring(0, 12) + "..."
        } else {
            results.title
        }

        Text(
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
            text = displayText,
            style = TextStyle(
                textAlign = TextAlign.Justify,
                color = Color(0xFF808080),
                fontFamily = FontFamily(Font(R.font.primary_regular)),
                fontSize = 16.sp
            )
        )

    }
}

@Composable
fun MoviesItemEnabled(
    results: Movies.Results, onClick: () -> Unit, enabled: Boolean,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
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

        Row(
            modifier = Modifier.padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                Icons.Default.Star, null,
                tint = Blue,
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = "${results.vote_average}/10 IMDb",
                style = TextStyle(
                    textAlign = TextAlign.Justify,
                    color = BlueLight,
                    fontFamily = FontFamily(Font(R.font.primary_regular)),
                    fontSize = 10.sp
                )
            )
        }

        val displayText = if (results.title?.length!! > 12) {
            results.title.substring(0, 12) + "..."
        } else {
            results.title
        }

        Text(
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
            text = displayText,
            style = TextStyle(
                textAlign = TextAlign.Justify,
                color = Color(0xFF808080),
                fontFamily = FontFamily(Font(R.font.primary_regular)),
                fontSize = 16.sp
            )
        )

    }
}