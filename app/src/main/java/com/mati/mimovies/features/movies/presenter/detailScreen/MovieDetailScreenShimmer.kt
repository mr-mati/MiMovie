package com.mati.mimovies.features.movies.presenter.detailScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mati.mimovies.R
import com.mati.mimovies.utils.Title
import com.valentinilk.shimmer.shimmer

@Composable
fun MovieDetailScreenShimmer(isVisible: Boolean) {

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(bottom = 16.dp)
            .fillMaxSize(),
    ) {
        HeaderShimmer()

        ToolBox(onClickPlay = { },
            onClickDownload = { })
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 16.dp, bottom = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(bottom = 4.dp, top = 8.dp, end = 8.dp)
            ) {
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
                Spacer(modifier = Modifier.width(16.dp))
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
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Title("Trailer")

        CircularProgressIndicator(
            modifier = Modifier
                .width(64.dp)
                .align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.onPrimary,
            trackColor = MaterialTheme.colorScheme.surface,
        )
    }

}


@Composable
fun HeaderShimmer(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp),
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .width(200.dp)
                    .shimmer()
                    .height(230.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 3.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                ),
                shape = RoundedCornerShape(8.dp)
            ) {

            }

            Card(
                modifier = Modifier
                    .width(150.dp)
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
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    Icons.Default.Star, tint = Color.Yellow, contentDescription = null
                )
                Card(
                    modifier = Modifier
                        .width(70.dp)
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
    }
}