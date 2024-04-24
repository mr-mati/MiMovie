@file:OptIn(ExperimentalMaterial3Api::class)

package com.mati.mimovies.features.auth.signUp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.movieui.core.theme.Shapes
import com.mati.mimovies.R

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    navHostController: NavHostController,
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.onPrimary
    ) {

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
            painter = painterResource(id = R.drawable.banner_sex_education),
            contentDescription = ""
        )

        Card(
            modifier = Modifier
                .padding(top = 300.dp)
                .fillMaxWidth()
                .fillMaxHeight(),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onPrimary,
            ),
            shape = RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                MainTextField(
                    edtValue = viewModel.name.value,
                    icon = R.drawable.ic_person,
                    hint = "Name"
                ) {
                    viewModel.name.value = it
                }

                Spacer(modifier = Modifier.height(16.dp))

                MainTextField(
                    edtValue = viewModel.email.value,
                    icon = R.drawable.ic_email,
                    hint = "Email"
                ) {
                    viewModel.email.value = it
                }

                Spacer(modifier = Modifier.height(16.dp))

                PasswordTextField(
                    edtValue = viewModel.password.value,
                    icon = R.drawable.ic_password,
                    hint = "Password"
                ) {
                    viewModel.password.value = it
                }

            }

        }

    }
}


@Composable
fun MainTextField(
    edtValue: String,
    icon: Int,
    hint: String,
    onValueChanges: (String) -> Unit,
) {
    OutlinedTextField(
        label = { Text(hint) },
        value = edtValue,
        singleLine = true,
        onValueChange = onValueChanges,
        placeholder = { Text(hint) },
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
