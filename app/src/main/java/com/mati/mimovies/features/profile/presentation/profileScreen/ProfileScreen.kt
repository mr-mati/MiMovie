package com.mati.mimovies.features.profile.presentation.profileScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mati.mimovies.R
import com.mati.mimovies.features.movies.data.local.entity.FavoriteEntity
import com.mati.mimovies.features.movies.data.local.entity.WatchEntity
import com.mati.mimovies.features.movies.data.network.ApiService
import com.mati.mimovies.features.movies.presentation.MovieEvent
import com.mati.mimovies.features.movies.presentation.MovieViewModel
import com.mati.mimovies.features.profile.presentation.ProfileViewModel
import com.mati.mimovies.utils.MovieNavigationItems
import com.mati.mimovies.utils.rippleIndication

@Composable
fun ProfileScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    viewModelProfile: ProfileViewModel = hiltViewModel(),
    navHostController: NavHostController,
) {

    val systemUiController = rememberSystemUiController()
    systemUiController.isNavigationBarVisible = false
    systemUiController.setNavigationBarColor(MaterialTheme.colorScheme.secondary)
    systemUiController.setStatusBarColor(MaterialTheme.colorScheme.secondary)

    val favoritesList = viewModelProfile.favoritesList
    val viewedList = viewModelProfile.viewedList

    val view = LocalView.current
    val insets = WindowInsetsCompat.toWindowInsetsCompat(view.rootWindowInsets)
    val statusBarHeight =
        with(LocalDensity.current) { insets.getInsets(WindowInsetsCompat.Type.statusBars()).top.toDp() }

    val scrollState = rememberScrollState()

    val state = viewModel.state
    val responseFavorite = state.favoriteList
    val responseWatch = state.watchList

    val nestedScrollConnection = object : NestedScrollConnection {
        override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
            // بررسی اینکه آیا اسکرول در حالت عمودی است و ScrollState به انتها نرسیده
            return if (available.y > 0 && scrollState.value != scrollState.maxValue) {
                Offset.Zero // اسکرول نکردن LazyColumn
            } else {
                Offset(0f, available.y) // اجازه اسکرول دادن به LazyColumn
            }
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = statusBarHeight),
        color = MaterialTheme.colorScheme.onPrimary
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                //.nestedScroll(nestedScrollConnection)
                .padding(bottom = 16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 3.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                ),
                shape = RoundedCornerShape(bottomStart = 26.dp, bottomEnd = 26.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp)
                            .align(Alignment.Start),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(32.dp)
                                .clickable(
                                    onClick = {
                                        navHostController.popBackStack()
                                    },
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rippleIndication()
                                ),
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = ""
                        )
                        IconButton(
                            modifier = Modifier
                                .background(Color.Transparent)
                                .size(32.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(32.dp)
                                ),
                            onClick = {
                                navHostController.navigate(MovieNavigationItems.ProfileEditScreen.route)
                            },
                        ) {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.Fit,
                                painter = painterResource(id = R.drawable.ic_edit),
                                contentDescription = ""
                            )
                        }

                    }
                    Image(
                        painter = painterResource(id = R.drawable.cast1),
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp)
                            .padding(8.dp)
                            .clip(CircleShape)
                            .wrapContentSize(align = Alignment.Center)
                            .align(Alignment.CenterHorizontally),
                        contentScale = ContentScale.Crop,
                    )
                    Text(
                        text = "Mr Mati",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold, style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.primary_bold)),
                            fontSize = 26.sp,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                DownloadCard()

                PremiumCard()

            }
            Spacer(modifier = Modifier.height(16.dp))
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
                        ItemSelection("Favorites list", favoritesList.value) {
                            if (!it) {
                                favoritesList.value = true
                                viewedList.value = false
                            }
                        }

                        ItemSelection("Viewed list", viewedList.value) {
                            if (!it) {
                                viewedList.value = true
                                favoritesList.value = false
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
                    if (favoritesList.value) {
                        viewModel.onEvent(MovieEvent.GetFavoriteList)
                        LazyVerticalGrid(
                            modifier = Modifier
                                .heightIn(min = 200.dp, max = 5000.dp),
                            columns = GridCells.Fixed(3),
                        ) {
                            items(
                                responseFavorite,
                                key = {
                                    it.id
                                }
                            ) { responseFavorite ->
                                ItemFavoriteList(
                                    results = responseFavorite,
                                ) {
                                    viewModel.onEvent(MovieEvent.GetMovieDetail(responseFavorite.id.toLong()))
                                    viewModel.onEvent(MovieEvent.GetMovieImage(responseFavorite.id.toLong()))
                                    navHostController.navigate(MovieNavigationItems.MovieDetails.route)
                                }
                            }
                        }
                    } else if (viewedList.value) {
                        viewModel.onEvent(MovieEvent.GetWatchList)
                        LazyVerticalGrid(
                            modifier = Modifier
                                .heightIn(min = 200.dp, max = 5000.dp),
                            columns = GridCells.Fixed(3)
                        ) {
                            items(
                                responseWatch,
                                key = {
                                    it.id
                                }
                            ) { responseWatch ->
                                ItemWatchList(
                                    results = responseWatch,
                                ) {
                                    viewModel.onEvent(MovieEvent.GetMovieDetail(responseWatch.id.toLong()))
                                    viewModel.onEvent(MovieEvent.GetMovieImage(responseWatch.id.toLong()))
                                    navHostController.navigate(MovieNavigationItems.MovieDetails.route)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DownloadCard() {
    Card(
        modifier = Modifier
            .width(200.dp)
            .padding(16.dp)
            .height(180.dp)
            .clickable { },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        shape = RoundedCornerShape(8.dp)
    ) {


        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(52.dp),
                painter = painterResource(id = R.drawable.ic_downloads),
                contentDescription = ""
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 32.dp)
                    .wrapContentSize(align = Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Downloads",
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                )
                Text(
                    text = "3 files in downloads",
                    color = Color.Gray,
                    style = TextStyle(
                        fontSize = 14.sp
                    )
                )
            }
        }
    }
}

@Composable
fun PremiumCard() {
    Card(
        modifier = Modifier
            .width(200.dp)
            .padding(16.dp)
            .height(180.dp)
            .clickable { },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        shape = RoundedCornerShape(8.dp)
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(52.dp),
                painter = painterResource(id = R.drawable.ic_premium),
                contentDescription = ""
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 32.dp)
                    .wrapContentSize(align = Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Premium",
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 8.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                )
                Text(
                    text = "You don't have Premium",
                    color = Color.Gray,
                    style = TextStyle(
                        fontSize = 14.sp
                    )
                )

            }
        }
    }
}


@Composable
fun ItemSelection(text: String, action: Boolean, onClick: (action: Boolean) -> Unit) {
    if (action) {
        Text(
            modifier = Modifier
                .clickable {
                    onClick(action)
                },
            text = text,
            fontSize = 14.sp,
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.primary_bold)),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary,
            )
        )
    } else {
        Text(
            modifier = Modifier
                .clickable {
                    onClick(action)
                },
            text = text,
            fontSize = 14.sp,
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.primary_bold)),
                fontSize = 14.sp,
                color = Color.Gray,
            )
        )
    }
}

@Composable
fun ItemFavoriteList(
    results: FavoriteEntity, onGettingClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(180.dp)
            .padding(end = 4.dp)
            .clickable() {
                onGettingClick()
            }
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray,
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
                .background(color = Color.Gray)
        )
    }
}

@Composable
fun ItemWatchList(
    results: WatchEntity, onGettingClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(180.dp)
            .padding(end = 4.dp)
            .clickable() {
                onGettingClick()
            }
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray,
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
                .background(color = Color.Gray)
        )
    }
}