package com.mati.mimovies.features.movies.presentation.util.PersonItem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.mati.mimovies.R
import com.mati.mimovies.features.movies.data.model.Person
import com.mati.mimovies.features.movies.data.network.ApiService
import com.mati.mimovies.ui.theme.Blue
import com.mati.mimovies.ui.theme.BlueLight

@Composable
fun PersonPopularList(
    response: Person.Results,
) {
    Column(
        Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("${ApiService.BASE_POSTER_PERSON}${response.profile_path}")
                    .build()
            ),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(color = Gray),
        )

        Row(
            modifier = Modifier.padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                Icons.Default.Person, null,
                tint = Blue,
            )
            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = "${response.popularity}",
                style = TextStyle(
                    textAlign = TextAlign.Justify,
                    color = BlueLight,
                    fontFamily = FontFamily(Font(R.font.primary_regular)),
                    fontSize = 10.sp
                )
            )
        }

        val displayText = if (response.name?.length!! > 12) {
            response.name.substring(0, 12)
        } else {
            response.name
        }

        Text(
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
            text = displayText,
            style = TextStyle(
                textAlign = TextAlign.Justify,
                color = Color(0xFF808080),
                fontFamily = FontFamily(Font(R.font.primary_regular)),
                fontSize = 16.sp
            )
        )
    }
}