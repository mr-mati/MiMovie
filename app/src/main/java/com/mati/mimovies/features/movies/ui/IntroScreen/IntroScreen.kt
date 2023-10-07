package com.mati.mimovies.features.movies.ui.IntroScreen

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mati.mimovies.R
import com.mati.mimovies.ui.theme.BlueLight
import com.mati.mimovies.utils.MovieNavigationItems
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.withContext

@Composable
fun IntroScreen(
    navHostController: NavHostController,
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.isNavigationBarVisible = false
    systemUiController.setNavigationBarColor(BlueLight)
    systemUiController.setStatusBarColor(BlueLight)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueLight),
        contentAlignment = Alignment.Center,
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.intro))
        LottieAnimation(
            modifier = Modifier
                .width(250.dp)
                .height(350.dp)
                .align(Alignment.Center)
                .padding(8.dp),
            restartOnPlay = false,
            composition = composition,
            alignment = Alignment.Center,
            iterations = LottieConstants.IterateForever,
        )
        LaunchedEffect(Unit) {
            withContext(Main) {
                Handler(Looper.getMainLooper()).postDelayed({
                    navHostController.navigate(MovieNavigationItems.MovieScreen.route) {
                        popUpTo(MovieNavigationItems.IntroScreen.route) {
                            inclusive = true
                        }
                    }
                }, 3000)
            }
        }
    }
}