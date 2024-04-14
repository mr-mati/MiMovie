package com.mati.mimovies.features.movies.ui.profileScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.movieui.core.theme.Shapes
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mati.mimovies.R
import com.mati.mimovies.data.model.Movies
import com.mati.mimovies.data.network.ApiService
import com.mati.mimovies.features.movies.ui.MovieViewModel
import com.mati.mimovies.utils.MovieNavigationItems

@Composable
fun ProfileScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    navHostController: NavHostController,
) {

    val systemUiController = rememberSystemUiController()
    systemUiController.isNavigationBarVisible = false
    systemUiController.setNavigationBarColor(MaterialTheme.colorScheme.secondary)
    systemUiController.setStatusBarColor(MaterialTheme.colorScheme.secondary)

    val scrollState = rememberScrollState()


    var favoritesList = remember { mutableStateOf(true) }
    var viewedList = remember { mutableStateOf(false) }

    val response = viewModel.res.value
    val responseYou = viewModel.you.value

    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.onPrimary
    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_back), null,
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .size(32.dp)
                .background(Color.Transparent)

        )

        Column(
            modifier = Modifier
                /*.verticalScroll(scrollState)*/
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
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.cast1), contentDescription = null,
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

                    Text(
                        text = "mahd60mm60@gmail.com",
                        fontSize = 14.sp,
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.primary_bold)),
                            fontSize = 14.sp,
                            color = Color.Gray,
                        )
                    )

                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Card(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(start = 12.dp, end = 8.dp)
                        .height(200.dp)
                        .clickable { },
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 3.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                }

                Card(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(start = 8.dp, end = 12.dp)
                        .height(200.dp)
                        .clickable { },
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 3.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                }

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
                        ItemSelection("لیست علاقه مندی", favoritesList.value) {
                            if (!it) {
                                favoritesList.value = true
                                viewedList.value = false
                            }
                        }

                        ItemSelection("لیست مشاهده شده", viewedList.value) {
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

                    Spacer(modifier = Modifier.height(26.dp))

                    if (favoritesList.value) {
                        LazyVerticalGrid(
                            modifier = Modifier
                                .scrollable(
                                    orientation = Orientation.Vertical,
                                    state = scrollState,
                                ),
                            columns = GridCells.Fixed(3),
                        ) {
                            items(
                                response.data,
                                key = {
                                    it.id!!
                                }
                            ) { response ->
                                ListMoviesItem(
                                    results = response,
                                ) {
                                    viewModel.setMovie(response)
                                    navHostController.navigate(MovieNavigationItems.MovieDetails.route)
                                }
                            }
                        }
                    } else if (viewedList.value) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3)
                        ) {
                            items(
                                responseYou.data,
                                key = {
                                    it.id!!
                                }
                            ) { responseYou ->
                                ListMoviesItem(
                                    results = responseYou,
                                ) {
                                    viewModel.setMovie(responseYou)
                                    navHostController.navigate(MovieNavigationItems.MovieDetails.route)
                                }
                            }
                        }
                    }

                }

            }


            /*Spacer(modifier = Modifier.height(16.dp))

            Title("Name")

            Spacer(modifier = Modifier.height(6.dp))

            MainTextField(
                edtValue = name.value,
                icon = R.drawable.ic_person,
                hint = "Name"
            ) {  }

            Spacer(modifier = Modifier.height(16.dp))

            Title("Email")

            MainTextField(
                edtValue = email.value,
                icon = R.drawable.ic_email,
                hint = "Email"
            ) {  }

            Spacer(modifier = Modifier.height(6.dp))

            Spacer(modifier = Modifier.height(16.dp))

            Title("Password")

            Spacer(modifier = Modifier.height(6.dp))

            PasswordTextField(
                edtValue = password.value,
                icon = R.drawable.ic_password,
                hint = "رمز عبور"
            ) {  }

            Spacer(modifier = Modifier.height(16.dp))

            Title("Country/Region")

            Spacer(modifier = Modifier.height(12.dp))*/

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

@Composable
fun ListMoviesItem(
    results: Movies.Results, onGettingClick: () -> Unit,
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
fun Title(text: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
    ) {
        Text(
            text = text, fontWeight = FontWeight.Bold, style = TextStyle(
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.tertiary,
            )
        )
    }
}

@Composable
fun rippleIndication() = rememberRipple(bounded = true, radius = 25.dp)
