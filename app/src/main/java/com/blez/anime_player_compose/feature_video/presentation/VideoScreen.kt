package com.blez.anime_player_compose.feature_video.presentation

import android.app.ActionBar
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.OptIn
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.getString
import androidx.core.view.WindowCompat
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaItem.SubtitleConfiguration
import androidx.media3.common.MediaMetadata
import androidx.media3.common.MimeTypes
import androidx.media3.common.Player
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import com.blez.anime_player_compose.R
import com.blez.anime_player_compose.common.util.Constants.PLAYER_SEEK_BACK_INCREMENT
import com.blez.anime_player_compose.common.util.Constants.PLAYER_SEEK_FORWARD_INCREMENT
import com.blez.anime_player_compose.feature_video.domain.model.Headers
import com.blez.anime_player_compose.feature_video.domain.model.Intro
import com.blez.anime_player_compose.feature_video.domain.model.Source
import com.blez.anime_player_compose.feature_video.domain.model.Subtitle
import com.blez.anime_player_compose.feature_video.domain.model.VideoModel
import com.blez.anime_player_compose.feature_video.presentation.components.PlayerController
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch


@kotlin.OptIn(ExperimentalAnimationApi::class)
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


    val result = VideoModel(
        download = "https://gogohd.net/download?id=MjE4NTQz&token=Tc_lksdkzYBdEkXkXhp-pA&expires=1707516216",
        headers = Headers("https://embtaku.pro/embedplus?id=MjE4NTQz&token=Tc_lksdkzYBdEkXkXhp-pA&expires=1707516216"),
        sources = listOf(Source(url = "https://www109.vipanicdn.net/streamhls/65be8eec9685db8f181d58395efb8597/ep.1.1704567536.360.m3u8", isM3U8 = true, quality = "360p"))
    )
    val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
        .setUserAgent(context.getString(R.string.app_name))
        .setConnectTimeoutMs(DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS)
        .setReadTimeoutMs(1800000)
        .setAllowCrossProtocolRedirects(true)

    dataSourceFactory.createDataSource()


    //val mediaItem = MediaItem.fromUri("https://www081.vipanicdn.net/streamhls/9701aab79df62c91ccc6614ac056c3e3/ep.1.1704382353.m3u8")


    val mediaItem = MediaItem.Builder()
        .setUri("https://www109.vipanicdn.net/streamhls/65be8eec9685db8f181d58395efb8597/ep.1.1704567536.1080.m3u8")
        .build()

    val mediaSource: MediaSource =
        HlsMediaSource.Factory(dataSourceFactory)
            .createMediaSource(mediaItem)


    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .apply {
                setSeekBackIncrementMs(PLAYER_SEEK_BACK_INCREMENT)
                setSeekForwardIncrementMs(PLAYER_SEEK_FORWARD_INCREMENT)

            }
            .build()
            .apply {
                setMediaItem(mediaItem)
                setMediaSource(mediaSource)
                prepare()
                playWhenReady = true
            }
    }

    var shouldShowControls by remember { mutableStateOf(false) }

    var isPlaying by remember { mutableStateOf(exoPlayer.isPlaying) }

    var totalDuration by remember { mutableStateOf(0L) }

    var currentTime by remember { mutableStateOf(0L) }

    var bufferedPercentage by remember { mutableStateOf(0) }

    var playbackState by remember { mutableStateOf(exoPlayer.playbackState) }

    Box(modifier = Modifier) {
        DisposableEffect(key1 = Unit) {
            val listener =
                object : Player.Listener {
                    override fun onEvents(
                        player: Player,
                        events: Player.Events
                    ) {
                        super.onEvents(player, events)
                        totalDuration = player.duration.coerceAtLeast(0L)
                        currentTime = player.currentPosition.coerceAtLeast(0L)
                        bufferedPercentage = player.bufferedPercentage
                        isPlaying = player.isPlaying
                        playbackState = player.playbackState
                    }
                }

            exoPlayer.addListener(listener)


            onDispose {
                exoPlayer.removeListener(listener)
                exoPlayer.release()
            }
        }


        val aspectRatio = listOf(AspectRatioFrameLayout.RESIZE_MODE_FIT,AspectRatioFrameLayout.RESIZE_MODE_FILL)

       var aspect by remember {
           mutableStateOf(AspectRatioFrameLayout.RESIZE_MODE_FILL)
       }

        AndroidView(
            modifier = Modifier.clickable {
                shouldShowControls = shouldShowControls.not()
            },
            factory = {
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = false
                    setShowSubtitleButton(true)
                    resizeMode = aspect
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                }
            })



        PlayerController(
            title = title,
            navHostController = navController,
            onBackPressed = {

            },
            isSubtitleEnabled = {

                if (aspect == AspectRatioFrameLayout.RESIZE_MODE_FIT){
                    aspect = AspectRatioFrameLayout.RESIZE_MODE_FILL

                }else{
                    AspectRatioFrameLayout.RESIZE_MODE_FIT
                }
            },
            isVisible = { shouldShowControls },
            currentTime = { currentTime },
            startTime = { 0L },
            onPausedClicked = {
                if (it) {
                    exoPlayer.pause()
                } else {
                    exoPlayer.play()
                }
            },
            endTime = { totalDuration },
            bufferedPercentage = { bufferedPercentage },
            onSeekChanged = { timeMs: Float ->
                exoPlayer.seekTo(timeMs.toLong())
            },
            onForwardClicked = {
                exoPlayer.seekForward()
            },
            onBackwardClicked = {
                exoPlayer.seekBack()
            },

        )
        LaunchedEffect(key1 = exoPlayer.duration) {
            Log.e("TAG", exoPlayer.contentDuration.toString())
        }


    }
}

