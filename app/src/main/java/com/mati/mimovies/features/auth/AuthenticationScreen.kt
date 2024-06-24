@file:OptIn(ExperimentalMaterial3Api::class)

package com.mati.mimovies.features.auth

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mati.mimovies.R
import com.mati.mimovies.features.auth.signIn.SignInScreen
import com.mati.mimovies.features.auth.signIn.SignInViewModel
import com.mati.mimovies.features.auth.signUp.SignUpScreen
import com.mati.mimovies.features.auth.signUp.SignUpViewModel

@Composable
fun AuthenticationScreen(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    signInViewModel: SignInViewModel = hiltViewModel(),
    navHostController: NavHostController,
) {
    val sheetSignUpState = rememberModalBottomSheetState()
    var isSheetSignUpOpen by rememberSaveable {
        mutableStateOf(false)
    }

    val sheetSignInState = rememberModalBottomSheetState()
    var isSheetSignInOpen by rememberSaveable {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.tst),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.78f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            Button(modifier = Modifier.fillMaxWidth(0.7f),
                colors = ButtonDefaults.buttonColors(Color.White),
                onClick = { isSheetSignInOpen = true }) {
                Text(
                    modifier = Modifier.padding(4.dp), text = "Login"
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(modifier = Modifier.fillMaxWidth(0.7f),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                onClick = { isSheetSignUpOpen = true }) {
                Text(
                    modifier = Modifier.padding(4.dp), text = "Register", color = Color.White
                )
            }

            if (isSheetSignUpOpen) {
                ModalBottomSheet(containerColor = MaterialTheme.colorScheme.primary,
                    sheetState = sheetSignUpState,
                    onDismissRequest = { isSheetSignUpOpen = false }) {
                    SignUpScreen(navHostController = navHostController, viewModel = signUpViewModel)
                }
            }

            if (isSheetSignInOpen) {
                ModalBottomSheet(containerColor = MaterialTheme.colorScheme.primary,
                    sheetState = sheetSignInState,
                    onDismissRequest = { isSheetSignInOpen = false }) {
                    SignInScreen(navHostController = navHostController, viewModel = signInViewModel)
                }
            }
        }
    }
}