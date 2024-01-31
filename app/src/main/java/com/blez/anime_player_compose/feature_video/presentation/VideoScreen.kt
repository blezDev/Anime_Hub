package com.blez.anime_player_compose.feature_video.presentation

import android.app.ActionBar
import android.os.Build
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavHostController
import com.blez.anime_player_compose.R
import com.blez.anime_player_compose.feature_video.domain.model.Intro
import com.blez.anime_player_compose.feature_video.domain.model.Source
import com.blez.anime_player_compose.feature_video.domain.model.Subtitle
import com.blez.anime_player_compose.feature_video.domain.model.VideoModel
import com.blez.anime_player_compose.feature_video.presentation.components.PlayerController
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch


@OptIn(UnstableApi::class)
@Composable
fun VideoScreen(
    navController: NavHostController,
    window: Window,
    actionBar: ActionBar?
) {
    val title = "Dark Gathering"
    var backPressHandled by remember { mutableStateOf(false) }
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    val coroutineScope = rememberCoroutineScope()
    BackHandler(enabled = !backPressHandled) {
        backPressHandled = true
        coroutineScope.launch {
            awaitFrame()
            onBackPressedDispatcher?.onBackPressed()
            backPressHandled = false
        }
    }
    val context = LocalContext.current

    //Hides the ugly action bar at the top
    actionBar?.hide()

    //Hide the status bars

    WindowCompat.setDecorFitsSystemWindows(window, false)

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    } else {
        window.insetsController?.apply {
            hide(WindowInsets.Type.statusBars())
            systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }







    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .apply {
                setSeekBackIncrementMs(5000)
                setSeekForwardIncrementMs(5000)
            }
            .build()
            .apply {
                //TODO
            }
    }


    var shouldShowControls by remember { mutableStateOf(true) }

    var isPlaying by remember { mutableStateOf(exoPlayer.isPlaying) }

    var totalDuration by remember { mutableStateOf(0L) }

    var currentTime by remember { mutableStateOf(0L) }

    var bufferedPercentage by remember { mutableStateOf(0) }

    var playbackState by remember { mutableStateOf(exoPlayer.playbackState) }


    val result = VideoModel(
        Intro(
            start = 55, end = 144
        ),
        sources = listOf(
            Source(
                url = "https://ec.netmagcdn.com:2228/hls-playback/f3dc9dd0b43d6219654b8501df3f906339ae8da5f595b9bba63f35016dfa04f34134f118ee82a085aa997fea5ead00f68ab9027d5cd2cc7cf441f84a88990be850051f5a2448dab7f4677114d0dd6bbcd5534329d6ce23c213df600c258d78cdebb25907876ace508df7d7d7bbae31a44e7151f62009c88c80b86df681f1baed96976206c3bee01d69923892699a4b40/master.m3u8",
                isM3U8 = true
            ), Source(
                url = "https://ec.netmagcdn.com:2228/hls-playback/f3dc9dd0b43d6219654b8501df3f906339ae8da5f595b9bba63f35016dfa04f34134f118ee82a085aa997fea5ead00f68ab9027d5cd2cc7cf441f84a88990be850051f5a2448dab7f4677114d0dd6bbcd5534329d6ce23c213df600c258d78cdebb25907876ace508df7d7d7bbae31a44e7151f62009c88c80b86df681f1baed96976206c3bee01d69923892699a4b40/master.m3u8",
                isM3U8 = true,
                quality = "auto"
            )
        ), subtitles = listOf(
            Subtitle(
                lang = "English",
                url = "https://r.scstatics.com/media/cc/ce5bfc5345b69ebbbeb7a85c294537e8/2-gne.vtt"
            ), Subtitle(
                lang = "Thumbnails",
                url = "https://imgb.megaresources.co/_a_preview/c6/c609ac116a3e264e17b51b611b1dc06d/thumbnails/sprite.vtt"
            )
        )
    )
    PlayerController(
        title = title,
        navHostController = navController,
        onBackPressed = {

        },
        isSubtitleEnabled = {

        })

}

