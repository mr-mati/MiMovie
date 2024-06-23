package com.mati.mimovies.features.movies.presenter.moreMovieScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.mati.mimovies.R
import com.mati.mimovies.features.movies.data.model.Movies
import com.mati.mimovies.features.movies.data.network.ApiService
import com.mati.mimovies.features.movies.presenter.MovieViewModel
import com.mati.mimovies.utils.MovieNavigationItems

@Composable
fun MoreMovieScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    navHostController: NavHostController
) {

    val response = viewModel.more
    val gridState = rememberLazyGridState()
    val lastVisibleItemIndex = gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index

    LaunchedEffect(lastVisibleItemIndex) {
        if (lastVisibleItemIndex != null && lastVisibleItemIndex >= response.size - 1) {
            viewModel.getMoreMovies(viewModel.ID.value, page = response.size / 10 + 1, false)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary
    ) {
        Column() {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
                    .align(Alignment.Start),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            navHostController.popBackStack()
                        },
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "Mi Movies",
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontFamily = FontFamily(Font(R.font.primary_bold)),
                        fontSize = 26.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(3),
                state = gridState
            ) {
                itemsIndexed(response) { index, item ->
                    val keyItem = index + item.id!!
                    key(keyItem) {
                        ListMoviesItem(
                            results = item,
                        ) {
                            viewModel.setMovie(item)
                            navHostController.navigate(MovieNavigationItems.MovieDetails.route)
                        }
                    }
                }
            }

        }
    }
}


@Composable
fun ListMoviesItem(
    results: Movies.Results, onGettingClick: () -> Unit,
) {
    Card(modifier = Modifier
        .width(150.dp)
        .height(180.dp)
        .padding(end = 4.dp)
        .clickable() {
            onGettingClick()
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