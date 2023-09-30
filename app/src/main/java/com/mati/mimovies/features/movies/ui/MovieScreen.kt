@file:OptIn(ExperimentalMaterial3Api::class)

package com.mati.mimovies.features.movies.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mati.mimovies.R
import com.mati.mimovies.data.model.Movies
import com.mati.mimovies.data.network.ApiService
import com.mati.mimovies.ui.theme.BackgroundMain
import com.mati.mimovies.ui.theme.Blue
import com.mati.mimovies.ui.theme.BlueLight

@Composable
fun MovieScreen(
    viewModel: MovieViewModel = hiltViewModel(),
) {

    val scrollState = rememberScrollState()
    val systemUiController = rememberSystemUiController()
    systemUiController.isNavigationBarVisible = false
    systemUiController.setNavigationBarColor(BackgroundMain)
    systemUiController.setStatusBarColor(BackgroundMain)

    val response = viewModel.res.value

    if (response.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    if (response.error.isNotEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = response.error)
        }
    }
    if (response.data.isNotEmpty()) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(bottom = 16.dp)
        ) {
            TopToolbar()
            TitleList("Trending", true)
            LazyRow {
                items(
                    response.data,
                    key = {
                        it.id!!
                    }
                ) { response ->
                    TrendList(res = response)
                }
            }
            TitleList("Categories", false)
            Categories()
            TitleList("For You", true)
            LazyRow {
                items(
                    response.data,
                    key = {
                        it.id!!
                    }
                ) { response ->
                    ListMoviesItem(res = response)
                }
            }
            TitleList("Most Visited", true)
            LazyRow {
                items(
                    response.data,
                    key = {
                        it.id!!
                    }
                ) { response ->
                    ListMoviesItem(res = response)
                }
            }
            TitleList("New Showing", true)
            val categories = listOf(
                "Cinematic",
                "Historical"
            )
            Column(
                modifier = Modifier
                    .padding(bottom = 4.dp, top = 8.dp)
            ) {
                repeat(response.data.size) { index ->
                    NewShowing(categories, res = response.data[index])
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopToolbar() {
    TopAppBar(
        title = {
            Text(
                text = "Mi Movies", style = TextStyle(
                    color = Color.White,
                    fontSize = 18.sp
                )
            )
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Search, null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Person, null)
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(BackgroundMain)
    )
}

@Composable
fun TitleList(text: String, action: Boolean) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
    )
    {
        Text(
            text = text, fontWeight = FontWeight.Bold, style = TextStyle(
                fontSize = 24.sp,
                color = Color.White,
            )
        )
        if (action) {
            TextButton(onClick = { /*TODO*/ }) {
                Text(text = "See All")
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TrendList(res: Movies.Results) {
    Card(
        modifier = Modifier
            .width(390.dp)
            .height(200.dp)
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Image(
            rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${ApiService.BASE_POSTER_URL}${res.poster_path}")
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .build()
            ),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun Categories() {
    val categories = listOf(
        "Cinematic",
        "Serials",
        "Actions",
        "Romantic",
        "Comedy",
        "Animation",
        "Social",
        "Historical"
    )
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .padding(bottom = 4.dp, top = 8.dp)
    ) {
        repeat(categories.size) { index ->
            Surface(
                modifier = Modifier
                    .background(BackgroundMain)
                    .padding(
                        start = if (index == 0) 24.dp else 0.dp,
                        end = 12.dp
                    )
                    .border(width = 1.dp, color = Blue, shape = RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
                    .padding(12.dp)
                    .clickable { }
            ) {
                Text(
                    text = categories[index],
                    modifier = Modifier.background(BackgroundMain),
                    style = TextStyle(
                        background = BackgroundMain,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        letterSpacing = 0.4.sp
                    )
                )
            }
        }
    }
}

@Composable
fun ListMoviesItem(
    res: Movies.Results,
) {

    Card(
        modifier = Modifier
            .width(150.dp)
            .height(180.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Image(
            rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${ApiService.BASE_POSTER_URL}${res.poster_path}")
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .build()
            ),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun NewShowing(
    categories: List<String>,
    res: Movies.Results,
) {
    Row(
        modifier = Modifier
            .background(BackgroundMain)
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .width(150.dp)
                .height(180.dp)
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Image(
                rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("${ApiService.BASE_POSTER_URL}${res.poster_path}")
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .build()
                ),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 16.dp),
        ) {
            Text(
                text = "Movie ${res.orginal_title}",
                modifier = Modifier
                    .padding(8.dp),
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp
                ),
            )
            Row(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Icon(
                    Icons.Default.Star,
                    tint = Color.Yellow, contentDescription = null
                )
                Text(
                    text = "${res.vote_average}/10 IMDb",
                    style = TextStyle(
                        color = Color.LightGray
                    ),
                )
            }
            Row(
                modifier = Modifier
                    .padding(bottom = 4.dp, top = 8.dp)
            ) {
                repeat(categories.size) { index ->
                    GenreShowing(categories[index])
                }
            }
        }
    }

}

@Composable
fun GenreShowing(genre: String) {
    Card(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = BlueLight,
        ),
        shape = RoundedCornerShape(22.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(top = 4.dp, bottom = 4.dp, start = 16.dp, end = 16.dp),
            text = genre,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                color = Blue,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                letterSpacing = 0.4.sp
            )
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun MovieScreenPreview() {
    Column(
        modifier = Modifier
            .background(BackgroundMain)
            .padding(bottom = 16.dp)
    ) {
        TopToolbar()
    }

}