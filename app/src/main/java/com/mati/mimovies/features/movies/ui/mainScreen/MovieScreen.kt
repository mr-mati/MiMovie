@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class
)

package com.mati.mimovies.features.movies.ui.mainScreen

import MainScreenShimmer
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
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
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mati.mimovies.R
import com.mati.mimovies.data.model.Movies
import com.mati.mimovies.data.network.ApiService
import com.mati.mimovies.features.movies.ui.MovieViewModel
import com.mati.mimovies.ui.theme.BlueLight
import com.mati.mimovies.utils.MovieNavigationItems
import kotlinx.coroutines.delay

@Composable
fun MovieScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    navHostController: NavHostController,
) {
    val scrollState = rememberScrollState()
    val systemUiController = rememberSystemUiController()
    systemUiController.isNavigationBarVisible = false
    systemUiController.setNavigationBarColor(MaterialTheme.colorScheme.primary)
    systemUiController.setStatusBarColor(MaterialTheme.colorScheme.primary)

    var enabled by remember { mutableStateOf(true) }

    Handler(Looper.getMainLooper()).postDelayed({
        enabled = true
    }, 2000)

    val response = viewModel.res.value
    val responseYou = viewModel.you.value
    val responseTop = viewModel.top.value

    if (response.data.isEmpty()) {
        if (response.isLoading) {
            MainScreenShimmer(isVisible = true)
        } else if (response.error.isNotEmpty()) {
            MainScreenShimmer(isVisible = false)
            if (response.data.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Error",
                        fontSize = 24.sp,
                        color = Color.Red
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = response.error,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            navHostController.navigate(MovieNavigationItems.IntroScreen.route) {
                                this.restoreState = true
                                popUpTo(MovieNavigationItems.MovieScreen.route) {
                                    inclusive = true
                                }
                            }
                        },
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        Text(text = "Retry")
                    }
                }
            }
        }
    }
    if (response.data.isNotEmpty()) {
        MainScreenShimmer(isVisible = false)
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(bottom = 16.dp)
        ) {
            TopToolbar(navHostController)
            Categories()
            TitleList("Trending", true)
            TrendList()
            TitleList("For You", true)
            LazyRow {
                items(
                    responseYou.data,
                    key = {
                        it.id!!
                    }
                ) { response ->
                    ListMoviesItem(results = response, {
                        enabled = false
                        viewModel.setMovie(response)
                        navHostController.navigate(MovieNavigationItems.MovieDetails.route)
                    }, enabled = enabled)
                }
            }
            TitleList("Top Rated", true)
            LazyRow {
                items(
                    responseTop.data,
                    key = {
                        it.id!!
                    }
                ) { response ->
                    ListMoviesItem(results = response, {
                        enabled = false
                        viewModel.setMovie(response)
                        navHostController.navigate(MovieNavigationItems.MovieDetails.route)
                    }, enabled = enabled)
                }
            }
            TitleList("New Showing", true)
            Column(

                modifier = Modifier
                    .padding(bottom = 4.dp, top = 8.dp)
            ) {
                repeat(response.data.size) { index ->
                    NewShowing(res = response.data[index], {
                        enabled = false
                        viewModel.setMovie(response.data[index])
                        navHostController.navigate(MovieNavigationItems.MovieDetails.route)
                    }, enabled = enabled)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopToolbar(navHostController: NavHostController) {
    TopAppBar(
        title = {
            Text(
                text = "Mi Movies", fontWeight = FontWeight.Bold, style = TextStyle(
                    color = MaterialTheme.colorScheme.tertiary,
                    fontFamily = FontFamily(Font(R.font.primary_bold)),
                    fontSize = 18.sp
                )
            )
        },
        actions = {
            IconButton(onClick = { navHostController.navigate(MovieNavigationItems.SearchScreen.route) }) {
                Icon(Icons.Default.Search, null)
            }
            IconButton(onClick = { navHostController.navigate(MovieNavigationItems.ProfileScreen.route) }) {
                Icon(Icons.Default.Person, null)
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun TitleList(text: String, action: Boolean) {
    val context = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 18.dp, start = 8.dp)
    )
    {
        Text(
            text = text, fontWeight = FontWeight.Bold, style = TextStyle(
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.tertiary,
            )
        )
        if (action) {
            TextButton(onClick = { Toast.makeText(context, text, Toast.LENGTH_SHORT).show() }) {
                Text(
                    text = "See All", style = TextStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}

@Composable
fun TrendList() {
    val banners = listOf(
        R.drawable.banner_oppenheimer,
        R.drawable.banner_barbie,
        R.drawable.banner_sex_education,
        R.drawable.banner_spider_man
    )

    val pagerState = rememberPagerState(pageCount = { banners.size })
    val bannerIndex = remember { mutableIntStateOf(0) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            bannerIndex.intValue = page
        }
    }

    /// auto scroll
    LaunchedEffect(Unit) {
        while (true) {
            delay(10000)
            tween<Float>(1500)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % banners.size
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .padding(horizontal = 8.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
        ) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(4.dp),
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
                    modifier = Modifier
                        .paint(
                            painterResource(id = R.drawable.bg),
                            contentScale = ContentScale.FillBounds
                        )
                )
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            repeat(banners.size) { index ->
                val height = 8.dp
                val width = if (index == bannerIndex.intValue) 16.dp else 8.dp
                val color =
                    if (index == bannerIndex.intValue) MaterialTheme.colorScheme.secondary else Gray

                Surface(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(width, height)
                        .clip(RoundedCornerShape(20.dp)),
                    color = color,
                ) {
                }
            }
        }
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
            .padding(bottom = 4.dp, top = 16.dp)
    ) {
        repeat(categories.size) { index ->
            Surface(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(
                        start = if (index == 0) 8.dp else 0.dp,
                        end = 12.dp
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .padding(12.dp)
                    .clickable { }
            ) {
                Text(
                    text = categories[index],
                    modifier = Modifier.background(MaterialTheme.colorScheme.primary),
                    style = TextStyle(
                        background = MaterialTheme.colorScheme.primary,
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
    results: Movies.Results, onGettingClick: () -> Unit, enabled: Boolean,
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(180.dp)
            .padding(end = 4.dp)
            .clickable(enabled = enabled) {
                onGettingClick()
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

@Composable
fun NewShowing(
    res: Movies.Results,
    onGettingClick: () -> Unit,
    enabled: Boolean,
) {
    val scrollState = rememberScrollState()
    Card(
        modifier = Modifier
            .clickable(enabled = enabled) { onGettingClick() }
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
                Image(
                    rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("${ApiService.BASE_POSTER_URL}${res.poster_path}")
                            .build()
                    ),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .paint(
                            painterResource(id = R.drawable.bg),
                            contentScale = ContentScale.FillBounds
                        )
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
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                letterSpacing = 0.4.sp
            )
        )
    }
}
