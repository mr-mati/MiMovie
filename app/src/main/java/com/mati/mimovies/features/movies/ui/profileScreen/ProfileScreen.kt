package com.mati.mimovies.features.movies.ui.profileScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(
    navHostController: NavHostController,
) {

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Profile Screen")
    }


}