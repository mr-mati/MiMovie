@file:OptIn(ExperimentalMaterial3Api::class)

package com.mati.mimovies.features.auth.signIn

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.movieui.core.theme.Shapes
import com.mait.ahanmakan.util.NetworkChecker
import com.mati.mimovies.R
import com.mati.mimovies.ui.theme.Blue
import com.mati.mimovies.utils.MovieNavigationItems

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    navHostController: NavHostController,
) {

    val userID = viewModel.userID
    val password = viewModel.password
    val context = LocalContext.current

    Surface(
        color = MaterialTheme.colorScheme.primary
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                modifier = Modifier.padding(top = 18.dp, bottom = 18.dp),
                text = "Login",
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.height(26.dp))

            MainTextField(
                edtValue = viewModel.userID.value,
                icon = R.drawable.ic_person,
                hint = "userName or email",
                placeholder = "UserID"
            ) {
                viewModel.userID.value = it
            }

            Spacer(modifier = Modifier.height(16.dp))

            PasswordTextField(
                edtValue = viewModel.password.value,
                icon = R.drawable.ic_password,
                hint = "Password"
            ) {
                viewModel.password.value = it
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (userID.value.isNotEmpty() && password.value.isNotEmpty()) {
                        if (Patterns.EMAIL_ADDRESS.matcher(userID.value).matches()) {
                            if (NetworkChecker(context).isInternetConnected) {
                                navHostController.navigate(MovieNavigationItems.IntroScreen.route) {
                                    popUpTo(MovieNavigationItems.IntroScreen.route){
                                        inclusive = true
                                    }
                                }
                            } else {
                            }
                        } else Toast.makeText(
                            context,
                            "فرمت ایمیل اشتباه میباشد",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else Toast.makeText(
                        context,
                        "تمامی فیلد ها را میبایست پر کنید",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                modifier = Modifier.padding(top = 28.dp, bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Login", color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}


@Composable
fun MainTextField(
    edtValue: String,
    icon: Int,
    hint: String,
    placeholder: String,
    onValueChanges: (String) -> Unit,
) {
    OutlinedTextField(
        label = { Text(hint) },
        value = edtValue,
        singleLine = true,
        onValueChange = onValueChanges,
        placeholder = { Text(placeholder) },
        colors = TextFieldDefaults.textFieldColors(
            selectionColors = TextSelectionColors(
                MaterialTheme.colorScheme.secondary,
                MaterialTheme.colorScheme.secondary
            ),
            disabledTextColor = Color.Transparent,
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Gray,
            unfocusedIndicatorColor = Color.White,
            focusedLabelColor = Color.Gray,
            unfocusedLabelColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.Gray,
            disabledIndicatorColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(top = 12.dp),
        shape = Shapes.medium,
        leadingIcon = { Icon(painterResource(icon), null) }
    )
}

@Composable
fun PasswordTextField(
    edtValue: String,
    icon: Int,
    hint: String,
    onValueChanges: (String) -> Unit,
) {

    val passwordVisible = remember { mutableStateOf(false) }

    OutlinedTextField(
        label = { Text(hint) },
        value = edtValue,
        singleLine = true,
        onValueChange = onValueChanges,
        placeholder = { Text(hint) },
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(top = 12.dp),
        shape = Shapes.medium,
        colors = TextFieldDefaults.textFieldColors(
            selectionColors = TextSelectionColors(
                MaterialTheme.colorScheme.secondary,
                MaterialTheme.colorScheme.secondary
            ),
            disabledTextColor = Color.Transparent,
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.Gray,
            unfocusedIndicatorColor = Color.White,
            focusedLabelColor = Color.Gray,
            unfocusedLabelColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.Gray,
            disabledIndicatorColor = Color.White
        ),
        leadingIcon = { Icon(painterResource(icon), null) },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {

            val image =
                if (passwordVisible.value) painterResource(R.drawable.ic_invisible)
                else painterResource(
                    R.drawable.ic_visible
                )
            Icon(
                painter = image,
                contentDescription = null,
                modifier = Modifier.clickable {
                    passwordVisible.value = !passwordVisible.value
                }
            )
        }
    )
}
