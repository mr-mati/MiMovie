package com.mati.mimovies.features.movies.ui.detailScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mati.mimovies.R
import com.mati.mimovies.data.model.Movies
import com.mati.mimovies.data.network.ApiService
import com.mati.mimovies.features.movies.ui.MediaPlayer.VideoPlayerMati
import com.mati.mimovies.features.movies.ui.MovieViewModel
import com.mati.mimovies.ui.theme.Blue
import com.mati.mimovies.ui.theme.BlueLight
import com.programming_simplified.movieapp.utils.MovieNavigationItems

@Composable
fun MovieDetailScreen(
    viewModel: MovieViewModel,
    navHostController: NavHostController,
) {

    val response = viewModel.movieDetails.value
    Log.d("main", "MovieDetailScreen: ${response.orginal_title}")

    val systemUiController = rememberSystemUiController()
    systemUiController.isNavigationBarVisible = false
    systemUiController.setNavigationBarColor(MaterialTheme.colorScheme.primary)
    systemUiController.setStatusBarColor(MaterialTheme.colorScheme.primary)

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .verticalScroll(scrollState)
            .padding(bottom = 16.dp)
            .fillMaxSize(),
    ) {
        Header(response) {
            navHostController.navigate(MovieNavigationItems.MovieScreen.route) {
                popUpTo(MovieNavigationItems.MovieDetails.route) {
                    inclusive = true
                }
            }
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 16.dp, bottom = 16.dp)
        ) {
            val genre = response.genre_ids
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
                    else -> "Null"
                }
            }
            categories.forEach {
                GenreShowing(it)
            }
            /*repeat(categories.size) { i ->
                val context = LocalContext.current
                Toast.makeText(context, "${categories[i]}", Toast.LENGTH_SHORT).show()
                GenreShowing(categories[i])
            }*/
        }
        Text(
            text = "${response.overview}",
            modifier = Modifier.padding(start = 16.dp, end = 8.dp, bottom = 8.dp),
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                fontSize = 14.sp,
                letterSpacing = 0.5.sp
            )
        )
        TitleList("Top Cast")
        TopCastList()
        TitleList("Trailer")
        Trailer()
    }
}

@Composable
fun Header(
    response: Movies.Results,
    onGettingClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp),
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            contentDescription = null,
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = "${ApiService.BASE_POSTER_URL}${response.poster_path ?: ""}")
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                        placeholder(R.drawable.ic_intro)
                    }).build()
            ),
            contentScale = ContentScale.FillBounds,
        )
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(100.dp),
            alignment = Alignment.Center,
            painter = painterResource(id = R.drawable.tst),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
        )
        IconButton(
            modifier = Modifier
                .align(Alignment.Center)
                .background(Color.Transparent),
            onClick = {
                onGettingClick()
            },
        ) {
            Image(
                painter = painterResource(id = R.drawable.play),
                contentDescription = "Play",
                modifier = Modifier
                    .size(32.dp)
                    .background(Color.Transparent)
            )
        }
        IconButton(
            modifier = Modifier
                .padding(16.dp)
                .background(Color.Transparent)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(32.dp)
                ),
            onClick = {
                onGettingClick()
            },
        ) {
            Icon(
                Icons.Default.ArrowBack, null,
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
                    .size(32.dp)
                    .background(Color.Transparent)

            )
        }
        Text(
            text = "${response.title}",
            modifier = Modifier.padding(start = 16.dp, top = 320.dp),
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 26.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp
            )
        )
    }
}

@Composable
fun GenreShowing(genre: String) {
    Card(
        modifier = Modifier.padding(start = 4.dp, end = 4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = BlueLight,
        ),
        shape = RoundedCornerShape(22.dp)
    ) {
        Text(
            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp, start = 16.dp, end = 16.dp),
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

@Composable
fun TopCastList(
) {
    val banners = listOf(
        R.drawable.banner_oppenheimer,
        R.drawable.banner_barbie,
        R.drawable.banner_sex_education,
        R.drawable.banner_spider_man
    )
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .padding(bottom = 4.dp, top = 8.dp)
    ) {
        repeat(banners.size) { index ->
            Card(
                modifier = Modifier
                    .width(130.dp)
                    .height(150.dp)
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 3.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Image(
                    painter = painterResource(id = banners[index]),
                    contentDescription = "Banners",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun Trailer(
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(horizontal = 18.dp)
            .padding(top = 8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        val video =
            "https://s921.ifilo.net/filo/video/TVRRd01TOHdNeTh5Tmc9PQ==/YjNGRWJXRnFWUT09/qt2G-hl4-240.mp4"
        VideoPlayerMati(video)
    }
}

@Composable
fun TitleList(text: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
    ) {
        Text(
            text = text, fontWeight = FontWeight.Bold, style = TextStyle(
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.tertiary,
            )
        )
    }
}