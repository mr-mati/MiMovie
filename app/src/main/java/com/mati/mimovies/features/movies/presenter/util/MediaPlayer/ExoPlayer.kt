package com.mati.mimovies.features.movies.presenter.util.MediaPlayer

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

@Composable
fun VideoPlayer(videoUri: Uri) {
    val context = LocalContext.current
    val player = remember { SimpleExoPlayer.Builder(context).build() }

    val playerView = remember { PlayerView(context) }
    playerView.player = player
    player.setMediaItem(MediaItem.fromUri(videoUri))
    val videoSource = remember {
        ProgressiveMediaSource.Factory(DefaultDataSourceFactory(context))
            .createMediaSource(MediaItem.fromUri(videoUri))
    }
    player.setMediaSource(videoSource)
    player.prepare()


    AndroidView(
        factory = { playerView },
        modifier = Modifier.fillMaxSize()
    )

    DisposableEffect(Unit) {
        onDispose {
            player.release()
        }
    }

}