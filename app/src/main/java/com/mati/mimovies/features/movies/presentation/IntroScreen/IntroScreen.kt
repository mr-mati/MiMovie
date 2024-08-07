@file:OptIn(DelicateCoroutinesApi::class)

package com.mati.mimovies.features.movies.presentation.IntroScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mati.mimovies.R
import com.mati.mimovies.ui.theme.BlueLight
import com.mati.mimovies.utils.MovieNavigationItems
import kotlinx.coroutines.DelicateCoroutinesApi

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun IntroScreen(
    navHostController: NavHostController,
) {

    val systemUiController = rememberSystemUiController()
    systemUiController.isNavigationBarVisible = false
    systemUiController.setNavigationBarColor(BlueLight)
    systemUiController.setStatusBarColor(BlueLight)

    val view = LocalView.current
    val insets = WindowInsetsCompat.toWindowInsetsCompat(view.rootWindowInsets)
    val statusBarHeight = with(LocalDensity.current) { insets.getInsets(WindowInsetsCompat.Type.statusBars()).top.toDp() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = statusBarHeight)
            .background(BlueLight),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon), contentDescription = "Logo",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(10.dp)
                .size(200.dp),
            alignment = Alignment.Center,
        )
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.intro))
        LottieAnimation(
            modifier = Modifier
                .size(150.dp)
                .padding(36.dp)
                .align(Alignment.BottomCenter),
            composition = composition,
            alignment = Alignment.BottomCenter,
            iterations = LottieConstants.IterateForever
        )

        getMainScreen(navHostController)

        /*LaunchedEffect(Unit) {
            withContext(Main) {
                Handler(Looper.getMainLooper()).postDelayed({
                    getMainScreen(navHostController)
                }, 5000)
            }
        }*/
    }
}


fun getMainScreen(navHostController: NavHostController) {
    navHostController.navigate(MovieNavigationItems.MovieScreen.route) {
        this.restoreState = true
        popUpTo(MovieNavigationItems.IntroScreen.route) {
            inclusive = true
        }
    }
}