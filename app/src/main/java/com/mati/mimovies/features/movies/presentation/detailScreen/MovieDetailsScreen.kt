@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.mati.mimovies.features.movies.presentation.detailScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mati.mimovies.R
import com.mati.mimovies.features.movies.data.model.MovieDetail
import com.mati.mimovies.features.movies.data.model.MovieImages
import com.mati.mimovies.features.movies.data.network.ApiService
import com.mati.mimovies.features.movies.presentation.MovieEvent
import com.mati.mimovies.features.movies.presentation.MovieViewModel
import com.mati.mimovies.features.movies.presentation.util.MediaPlayer.VideoPlayer
import com.mati.mimovies.features.profile.presentation.profileScreen.ItemSelection
import com.mati.mimovies.ui.theme.Blue
import com.mati.mimovies.ui.theme.BlueLight
import com.mati.mimovies.utils.ButtonCustom
import com.mati.mimovies.utils.Title
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@SuppressLint("UnrememberedMutableState")
@Composable
fun MovieDetailScreen(
    viewModel: MovieViewModel,
    navHostController: NavHostController,
) {

    val addedToList = mutableStateOf(false)

    val systemUiController = rememberSystemUiController()
    systemUiController.isNavigationBarVisible = false
    systemUiController.setNavigationBarColor(MaterialTheme.colorScheme.primary)
    systemUiController.setStatusBarColor(Color.Transparent)

    val response = viewModel.movieDetails.value.data
    if (response != null) {

        MovieDetailScreenShimmer(false)

        Log.d("main", "MovieDetailScreen: ${response.original_title}")

        val responseImage = viewModel.movieImages.value

        val scrollState = rememberScrollState()
        val scrollStateGenre = rememberScrollState()

        val trailerVideo = remember { mutableStateOf(true) }
        val trailerImages = remember { mutableStateOf(false) }

        val scrollSheetState = rememberScrollState()

        val sheetPlayState = rememberModalBottomSheetState()
        var isSheetPlayOpen by rememberSaveable {
            mutableStateOf(false)
        }
        val sheetDownloadState = rememberModalBottomSheetState()
        var isSheetDownloadOpen by rememberSaveable {
            mutableStateOf(false)
        }

        val context = LocalContext.current
        val currentContext by rememberUpdatedState(context)

        LaunchedEffect(scrollState) {
            snapshotFlow { scrollState.value }
                .map { scrollValue ->
                    scrollValue >= 50.dp.toPx(currentContext)
                }
                .distinctUntilChanged()
                .collect { hide ->
                    systemUiController.isStatusBarVisible = !hide
                }
        }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .verticalScroll(scrollState)
                .padding(bottom = 16.dp)
                .fillMaxSize(),
        ) {
            Header(response) {
                navHostController.popBackStack()
            }
            val isAlreadyFavorite =
                viewModel.state.favoriteList.any { it.id == response.id }

            if (!isAlreadyFavorite) {
                addedToList.value = false
            } else {
                addedToList.value = true
            }
            ToolBox(
                onClickPlay = {
                    isSheetPlayOpen = true
                    if (!isAlreadyFavorite) {
                        viewModel.onEvent(MovieEvent.AddMovieToWatch(response))
                    }
                },
                onClickDownload = { isSheetDownloadOpen = true },
                onAddToFavorite = {
                    if (!isAlreadyFavorite) {
                        viewModel.onEvent(MovieEvent.AddMovieToFavorite(response))
                        viewModel.onEvent(MovieEvent.GetFavoriteList)
                        addedToList.value = true
                    } else {
                        viewModel.onEvent(MovieEvent.DeleteMovieToFavorite(response))
                        viewModel.onEvent(MovieEvent.GetFavoriteList)
                        addedToList.value = false
                    }
                },
                addedToList = addedToList
            )
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 16.dp, bottom = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(scrollStateGenre)
                        .padding(bottom = 4.dp, top = 8.dp, end = 8.dp)
                ) {
                    val genre = response.genres
                    genre.forEach { item ->
                        GenreShowing(item.name)
                    }
                }
            }
            Text(
                text = response.original_title,
                modifier = Modifier.padding(start = 16.dp, end = 8.dp, bottom = 8.dp),
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
            Text(
                text = response.overview,
                modifier = Modifier.padding(start = 16.dp, end = 8.dp, bottom = 8.dp),
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    color = Gray,
                    fontSize = 14.sp,
                    letterSpacing = 0.5.sp
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
            Title("Trailer")
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 3.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                ),
                shape = RoundedCornerShape(8.dp)
            ) {

                Column {
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ItemSelection("Video", trailerVideo.value) {
                            if (!it) {
                                trailerVideo.value = true
                                trailerImages.value = false
                            }
                        }

                        ItemSelection("Images", trailerImages.value) {
                            if (!it) {
                                trailerImages.value = true
                                trailerVideo.value = false
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.onPrimary)
                            .padding(start = 2.dp, end = 2.dp, top = 16.dp, bottom = 26.dp)
                    )
                    if (trailerVideo.value) {
                        Trailer()
                    } else if (trailerImages.value) {
                        PosterList(responseImage.data)
                    }
                }
            }


            Title("Top Cast")
            TopCastList()

            if (isSheetPlayOpen) {
                ModalBottomSheet(containerColor = MaterialTheme.colorScheme.primary,
                    sheetState = sheetPlayState,
                    onDismissRequest = { isSheetPlayOpen = false }) {
                    Column(

                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .verticalScroll(scrollSheetState)

                    ) {

                        Title(text = "English")

                        ButtonCustom(text = "1080 HD", icon = R.drawable.play) {

                        }

                        ButtonCustom(text = "720 HD", icon = R.drawable.play) {

                        }

                        ButtonCustom(text = "480", icon = R.drawable.play) {

                        }

                        Title(text = "Persian")

                        ButtonCustom(text = "1080 HD", icon = R.drawable.play) {

                        }

                        ButtonCustom(text = "720 HD", icon = R.drawable.play) {

                        }

                        ButtonCustom(text = "480", icon = R.drawable.play) {

                        }

                    }
                }
            }


            if (isSheetDownloadOpen) {
                ModalBottomSheet(containerColor = MaterialTheme.colorScheme.primary,
                    sheetState = sheetDownloadState,
                    onDismissRequest = { isSheetDownloadOpen = false }) {
                    Column(

                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .verticalScroll(scrollSheetState)

                    ) {

                        ButtonCustom(text = "1080 HD", icon = R.drawable.ic_download) {

                        }

                        ButtonCustom(text = "720 HD", icon = R.drawable.ic_download) {

                        }

                        ButtonCustom(text = "480", icon = R.drawable.ic_download) {

                        }

                        Title(text = "Subtitle")

                        ButtonCustom(text = "English subtitle", icon = R.drawable.ic_download) {

                        }

                        ButtonCustom(text = "Persian subtitle", icon = R.drawable.ic_download) {

                        }

                    }
                }
            }
        }
    } else if (viewModel.movieDetails.value.isLoading || viewModel.movieDetails.value.error.length >= 3) {
        MovieDetailScreenShimmer(true)
    }
}

@Composable
fun Header(
    response: MovieDetail,
    onBackClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp),
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .shimmer()
                .blur(
                    radiusX = 20.dp,
                    radiusY = 20.dp,
                    edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(16.dp))
                ),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data("${ApiService.BASE_POSTER_URL}${response.poster_path}")
                    .apply(block = fun ImageRequest.Builder.() {
                    }).build()
            )
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
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 42.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .width(200.dp)
                    .height(230.dp),
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
                            .data("${ApiService.BASE_POSTER_URL}${response.poster_path}")
                            .build()
                    ),
                    alignment = Alignment.CenterStart,
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxSize()
                        .paint(
                            painterResource(id = R.drawable.icon),
                            contentScale = ContentScale.FillBounds
                        )
                )
            }
            val title = if (response.title.length > 25) {
                response.title.substring(0, 25) + "..."
            } else {
                response.title
            }
            Text(
                text = title,
                modifier = Modifier.padding(top = 12.dp),
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
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    Icons.Default.Star, tint = Color.Yellow, contentDescription = null
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = "${response.vote_average}/10 IMDb",
                    style = TextStyle(
                        color = BlueLight
                    ),
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .background(Color.Transparent),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
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
                    onBackClick()
                },
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    null,
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color.Transparent)

                )
            }
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.Person, null,
                    tint = White,
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "${response.popularity}",
                    style = TextStyle(
                        textAlign = TextAlign.Justify,
                        color = BlueLight,
                        fontFamily = FontFamily(Font(R.font.primary_regular)),
                        fontSize = 10.sp
                    )
                )
            }
        }
    }
}

@Composable
fun ToolBox(
    onClickPlay: () -> Unit,
    onClickDownload: () -> Unit,
    onAddToFavorite: () -> Unit,
    addedToList: State<Boolean>
) {
    Column {
        ButtonCustom("Play", R.drawable.play) {
            onClickPlay()
        }
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            ),
            shape = RoundedCornerShape(45.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start = 6.dp, end = 6.dp)
                .background(color = Gray)
        ) {}
        Row(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(modifier = Modifier
                .padding(start = 6.dp, end = 6.dp)
                .width(80.dp)
                .height(60.dp),
                onClick = { onAddToFavorite() }) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!addedToList.value) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = stringResource(id = R.string.add_fev)
                        )
                        Text(
                            text = stringResource(id = R.string.add_fev), style = TextStyle(
                                color = Gray,
                                fontSize = 12.sp,
                            )
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.ic_remove),
                            contentDescription = stringResource(id = R.string.remove_fav)
                        )
                        Text(
                            text = stringResource(id = R.string.remove_fav), style = TextStyle(
                                color = Gray,
                                fontSize = 12.sp,
                            )
                        )
                    }
                }
            }
            IconButton(modifier = Modifier
                .padding(start = 6.dp, end = 6.dp)
                .width(80.dp)
                .height(60.dp),
                onClick = { /*TODO*/ }) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_favorite),
                        contentDescription = "Rate"
                    )
                    Text(
                        text = "Rate", style = TextStyle(
                            textAlign = TextAlign.Center,
                            color = Gray,
                            fontSize = 12.sp,
                        )
                    )
                }
            }
            IconButton(modifier = Modifier
                .padding(start = 6.dp, end = 6.dp)
                .width(80.dp)
                .height(60.dp),
                onClick = { onClickDownload() }) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_download),
                        contentDescription = "download"
                    )
                    Text(
                        text = "Download", style = TextStyle(
                            textAlign = TextAlign.Center,
                            color = Gray,
                            fontSize = 12.sp,
                        )
                    )
                }
            }
            IconButton(modifier = Modifier
                .padding(start = 6.dp, end = 6.dp)
                .width(80.dp)
                .height(60.dp),
                onClick = { /*TODO*/ }) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = "Share"
                    )
                    Text(
                        text = "Share", style = TextStyle(
                            textAlign = TextAlign.Center,
                            color = Gray,
                            fontSize = 12.sp,
                        )
                    )
                }
            }
        }
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            ),
            shape = RoundedCornerShape(45.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start = 16.dp, end = 16.dp)
                .background(color = Gray)
        ) {}
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
        R.drawable.cast1,
        R.drawable.cast2,
        R.drawable.cast3,
        R.drawable.cast4,
        R.drawable.cast5,
        R.drawable.cast6
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
                    defaultElevation = 0.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent,
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
fun Trailer() {
    val showVideoPlayer by remember { mutableStateOf(false) }

    /*LaunchedEffect(Unit) {
        delay(5000)
        showVideoPlayer = true
    }*/

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        if (showVideoPlayer) {
            val videoUri = "https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4"
            VideoPlayer(uri = videoUri, fullScreen = false) {

            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.onPrimary,
                    trackColor = MaterialTheme.colorScheme.surface,
                )
            }
        }
    }
}

@Composable
fun PosterList(
    banners: List<MovieImages.Backdrop>
) {
    val pagerState = rememberPagerState(pageCount = { banners.size })
    val bannerIndex = remember { mutableIntStateOf(0) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            bannerIndex.intValue = page
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(horizontal = 8.dp)
    ) {
        if (banners.isNotEmpty()) {
            HorizontalPager(
                state = pagerState, modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) { index ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(8.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 3.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    val imagePainter = rememberAsyncImagePainter(
                        model = "${ApiService.BASE_POSTER_URL}${banners[index].file_path}",
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.icon),
                        error = painterResource(R.drawable.icon)
                    )
                    Image(
                        painter = imagePainter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
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
                    ) {}
                }
            }
        }
    }
}

fun Dp.toPx(context: android.content.Context): Float {
    return this.value * context.resources.displayMetrics.density
}