package com.mati.mimovies.features.movies.presenter.util.MediaPlayer

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView

@Composable
fun VideoPlayer(
    uri: String,
    modifier: Modifier = Modifier,
    fullScreen: Boolean,
    onFullScreenToggle: () -> Unit
) {
    val context = LocalContext.current
    var isBuffering by remember { mutableStateOf(true) }

    val cache = remember { CacheManager.getCache(context) }
    val dataSourceFactory = remember { buildDataSourceFactory(context, cache) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(uri))
            setMediaSource(mediaSource)
            prepare()
            playWhenReady = false
        }
    }

    exoPlayer.addListener(object : Player.Listener {
        override fun onIsLoadingChanged(isLoading: Boolean) {
            isBuffering = isLoading
        }

        override fun onPlaybackStateChanged(state: Int) {
            if (state == Player.STATE_READY) {
                isBuffering = false
            } else if (state == Player.STATE_BUFFERING) {
                isBuffering = true
            }
        }
    })

    Box(modifier = modifier) {
        AndroidView(factory = {
            PlayerView(context).apply {
                player = exoPlayer
                useController = true
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
            }
        })

        if (isBuffering) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(42.dp)
                        .align(Alignment.Center),
                    color = MaterialTheme.colorScheme.onPrimary,
                    trackColor = MaterialTheme.colorScheme.surface,
                )
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.stop()
            exoPlayer.release()
        }
    }
}

@Composable
fun VideoPlayerScreen(uri: String) {
    var isFullScreen by remember { mutableStateOf(false) }

    if (isFullScreen) {
        FullScreenVideoPlayer(uri = uri, onExitFullScreen = { isFullScreen = false })
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            VideoPlayer(uri = uri,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f),
                fullScreen = isFullScreen,
                onFullScreenToggle = { isFullScreen = true })
        }
    }
}

@Composable
fun FullScreenVideoPlayer(uri: String, onExitFullScreen: () -> Unit) {
    val context = LocalContext.current as Activity

    DisposableEffect(Unit) {
        val originalOrientation = context.requestedOrientation
        context.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        onDispose {
            context.requestedOrientation = originalOrientation
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        VideoPlayer(
            uri = uri,
            modifier = Modifier.fillMaxSize(),
            fullScreen = true,
            onFullScreenToggle = onExitFullScreen
        )

        IconButton(
            onClick = onExitFullScreen, modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Build, contentDescription = "Exit Fullscreen")
        }
    }
}
