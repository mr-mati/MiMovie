@file:OptIn(ExperimentalMaterial3Api::class)

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mati.mimovies.R
import com.mati.mimovies.features.movies.presenter.mainScreen.Categories
import com.valentinilk.shimmer.shimmer

@Composable
fun MainScreenShimmer(isVisible: Boolean) {
    if (isVisible) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(bottom = 16.dp)
                .fillMaxSize(),
        ) {
            TopToolbarShimmer()
            Categories()
            TitleListShimmer(true)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .shimmer()
                    .padding(4.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 3.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Gray,
                ),
                shape = RoundedCornerShape(8.dp)
            ) {}
            TitleListShimmer(true)
            LazyRow {
                items(
                    count = 5
                ) {
                    ListMovieShimmer()
                }
            }
            TitleListShimmer(true)
            LazyRow {
                items(
                    count = 5
                ) {
                    ListMovieShimmer()
                }
            }
            TitleListShimmer(true)
        }
    }
}

@Composable
fun TopToolbarShimmer() {
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
            IconButton(onClick = { }) {
                Icon(Icons.Default.Search, null)
            }
            IconButton(onClick = { }) {
                Icon(Icons.Default.Person, null)
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun TitleListShimmer(action: Boolean) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 18.dp, start = 8.dp, top = 8.dp, bottom = 8.dp)
    )
    {
        Card(
            modifier = Modifier
                .width(100.dp)
                .shimmer()
                .height(30.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.Gray,
            ),
            shape = RoundedCornerShape(24.dp)
        ) {}
        if (action) {
            Card(
                modifier = Modifier
                    .width(50.dp)
                    .shimmer()
                    .height(30.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 3.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Gray,
                ),
                shape = RoundedCornerShape(24.dp)
            ) {}
        }
    }
}

@Composable
fun ListMovieShimmer() {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(180.dp)
            .shimmer()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray,
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.load))
        LottieAnimation(
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .shimmer()
                .align(Alignment.CenterHorizontally)
                .padding(8.dp),
            composition = composition,
            alignment = Alignment.Center,
            iterations = LottieConstants.IterateForever,
        )
    }
}