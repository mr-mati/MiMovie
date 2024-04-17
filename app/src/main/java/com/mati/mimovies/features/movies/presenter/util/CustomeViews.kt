package com.mati.mimovies.features.movies.presenter.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonCustom(text: String, icon: Int, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondary)
                .clickable(
                    onClick = { onClick() }
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = text,
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp)
                    .background(Color.Transparent)
            )
            Text(
                text = text,
                style = TextStyle(
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontSize = 18.sp,
                    lineHeight = 24.sp,
                    letterSpacing = 0.5.sp
                )
            )
        }
    }
}

@Composable
fun Title(text: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(horizontal = 18.dp)
    ) {
        Text(
            modifier = Modifier
                .background(Color.Transparent),
            text = text, fontWeight = FontWeight.Bold, style = TextStyle(
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.tertiary,
            )
        )
    }
}

@Composable
fun rippleIndication() = rememberRipple(bounded = true, radius = 25.dp)
