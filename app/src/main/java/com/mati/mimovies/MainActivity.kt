package com.mati.mimovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.mati.mimovies.features.auth.signIn.SignInViewModel
import com.mati.mimovies.features.auth.signUp.SignUpViewModel
import com.mati.mimovies.features.movies.presentation.MovieViewModel
import com.mati.mimovies.features.movies.presentation.PersonViewModel
import com.mati.mimovies.features.profile.presentation.ProfileViewModel
import com.mati.mimovies.navigation.MovieNavigation
import com.mati.mimovies.ui.theme.MiMoviesTheme
import com.mati.mimovies.utils.UiEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val signUpViewModel: SignUpViewModel by viewModels()
    val signInViewModel: SignInViewModel by viewModels()
    val mainViewModel: MovieViewModel by viewModels()
    val personViewModel: PersonViewModel by viewModels()
    val profileViewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiMoviesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    MovieNavigation(
                        signUpViewModel = signUpViewModel,
                        signInViewModel = signInViewModel,
                        mainViewModel = mainViewModel,
                        personViewModel = personViewModel,
                        profileViewModel = profileViewModel
                    )
                }
            }
        }
    }
}