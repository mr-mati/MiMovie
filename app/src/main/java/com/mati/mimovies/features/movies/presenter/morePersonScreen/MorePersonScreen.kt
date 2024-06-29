package com.mati.mimovies.features.movies.presenter.morePersonScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mati.mimovies.R
import com.mati.mimovies.features.movies.presenter.PersonViewModel
import com.mati.mimovies.features.movies.presenter.util.PersonItem.PersonPopularList

@Composable
fun MorePersonScreen(
    viewModel: PersonViewModel = hiltViewModel(),
    navHostController: NavHostController
) {

    val view = LocalView.current
    val insets = WindowInsetsCompat.toWindowInsetsCompat(view.rootWindowInsets)
    val statusBarHeight =
        with(LocalDensity.current) { insets.getInsets(WindowInsetsCompat.Type.statusBars()).top.toDp() }

    val response = viewModel.morePerson
    val title = viewModel.title.value

    val gridState = rememberLazyGridState()
    val lastVisibleItemIndex = gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index

    LaunchedEffect(lastVisibleItemIndex) {
        if (lastVisibleItemIndex != null && lastVisibleItemIndex >= response.size - 1) {
            viewModel.getMorePerson(page = response.size / 10 + 1, false)
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = statusBarHeight), color = MaterialTheme.colorScheme.primary
    ) {
        Column() {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
                    .align(Alignment.Start),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            navHostController.popBackStack()
                        },
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "Mi Movies",
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.secondary,
                        fontFamily = FontFamily(Font(R.font.primary_bold)),
                        fontSize = 26.sp
                    )
                )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = "($title)",
                    style = TextStyle(
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colorScheme.tertiary,
                        fontFamily = FontFamily(Font(R.font.primary_regular)),
                        fontSize = 14.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(3),
                state = gridState
            ) {
                itemsIndexed(response) { index, item ->
                    val keyItem = index + item.id!!
                    key(keyItem) {
                        PersonPopularList(item)
                    }
                }
            }

        }
    }
}