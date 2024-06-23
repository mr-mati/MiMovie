@file:OptIn(ExperimentalMaterial3Api::class)

package com.mati.mimovies.features.movies.presenter.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Transparent
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
import com.mati.mimovies.features.movies.presenter.mainScreen.GenreShowing
import com.mati.mimovies.utils.MovieNavigationItems

@Composable
fun SearchScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    navHostController: NavHostController,
) {
    val searchBox = viewModel.searchBox
    val response = viewModel.search.value

    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
        ) {
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
                    text = "Mi Movies", fontWeight = FontWeight.Bold, style = TextStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontFamily = FontFamily(Font(R.font.primary_bold)),
                        fontSize = 26.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            SearchBox(searchBox) {
                if (searchBox.value != "" || searchBox.value.isNotEmpty()) {
                    viewModel.searchMovies(searchBox.value)
                }
            }

            if (response.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .padding(top = 64.dp)
                        .align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.onPrimary,
                    trackColor = MaterialTheme.colorScheme.surface,
                )
            } else if (response.data.isNotEmpty()) {
                if (searchBox.value != "" || searchBox.value.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .padding(bottom = 4.dp, top = 8.dp)
                    ) {
                        repeat(response.data.size) { index ->
                            SearchItem(
                                res = response.data[index]
                            ) {
                                viewModel.setMovie(response.data[index])
                                navHostController.navigate(MovieNavigationItems.MovieDetails.route)
                            }
                        }
                    }
                } else {
                    response.data = emptyList()
                    Column(
                        modifier = Modifier
                            .padding(bottom = 4.dp, top = 8.dp)
                    ) {

                    }
                }
            }/* else if (response.error.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(top = 64.dp)
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 4.dp),
                        text = "404 Not Found",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = MaterialTheme.colorScheme.surface
                        )
                    )
                }
            }*/
        }
    }


}

@Composable
fun SearchBox(searchBox: MutableState<String>, onClickSearch: () -> Unit) {

    val showButton = remember { mutableStateOf(false) }

    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 2.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            if (!showButton.value) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.onPrimary)
                        .padding(top = 12.dp, start = 12.dp),
                    text = "search", fontWeight = FontWeight.Bold, style = TextStyle(
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.tertiary,
                    )
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.onPrimary
                    )
                    .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 12.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.surface),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        tint = Color.White,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(start = 10.dp)
                    )
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textStyle = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.tertiary,
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            selectionColors = TextSelectionColors(
                                MaterialTheme.colorScheme.secondary,
                                MaterialTheme.colorScheme.secondary
                            ),
                            disabledTextColor = Transparent,
                            focusedIndicatorColor = Transparent,
                            unfocusedIndicatorColor = Transparent,
                            containerColor = Transparent,
                            disabledIndicatorColor = Transparent
                        ),
                        singleLine = true,
                        value = searchBox.value,
                        onValueChange = {
                            searchBox.value = it
                            if (searchBox.value.length >= 3) {
                                showButton.value = true
                            } else {
                                showButton.value = false
                            }
                        },
                    )
                }
            }
        }
        if (showButton.value) {
            Button(
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                onClick = { onClickSearch() }) {

                Text(
                    text = "search",
                    modifier = Modifier
                        .padding(top = 8.dp, bottom = 8.dp),
                    fontWeight = FontWeight.Bold, style = TextStyle(
                        fontSize = 18.sp,
                    )

                )

            }
        }
    }
}

@Composable
fun SearchItem(
    res: Movies.Results,
    onGettingClick: () -> Unit,
) {
    val scrollState = rememberScrollState()
    Card(
        modifier = Modifier
            .clickable { onGettingClick() }
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxWidth(),
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
                    containerColor = MaterialTheme.colorScheme.tertiary,
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                val imagePainter = rememberImagePainter(
                    data = "${ApiService.BASE_POSTER_URL}${res.poster_path}",
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
                        .background(Gray)
                )
            }
            Column(
                modifier = Modifier
                    .padding(top = 16.dp),
            ) {
                Row {
                    Text(
                        text = "${res.title}",
                        modifier = Modifier
                            .padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.tertiary,
                            fontSize = 16.sp
                        ),
                    )
                    Text(
                        modifier = Modifier
                            .padding(top = 10.dp),
                        text = res.release_date!!.take(4),
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = Color.LightGray
                        ),
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
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
                val genre = res.genre_ids
                val categories = arrayListOf<String>()
                repeat(genre!!.size) { i ->
                    when (genre[i]) {
                        28 -> categories.add("Action")
                        12 -> categories.add("Adventure")
                        16 -> categories.add("Animation")
                        35 -> categories.add("Comedy")
                        80 -> categories.add("Crime")
                        99 -> categories.add("Documentary")
                        18 -> categories.add("Drama")
                        10751 -> categories.add("Family")
                        36 -> categories.add("History")
                        27 -> categories.add("Horror")
                        10402 -> categories.add("Music")
                        9648 -> categories.add("Mystery")
                        878 -> categories.add("Science Fiction")
                        10770 -> categories.add("TV Movie")
                        53 -> categories.add("Thriller")
                        10752 -> categories.add("War")
                        37 -> categories.add("Westernv")
                    }
                }
                Row(
                    modifier = Modifier
                        .horizontalScroll(scrollState)
                        .padding(bottom = 4.dp, top = 8.dp, end = 8.dp)
                ) {
                    categories.forEach {
                        GenreShowing(it)
                    }
                }
            }
        }
    }
}
