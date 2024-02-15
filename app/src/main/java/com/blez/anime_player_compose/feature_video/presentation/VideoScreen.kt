package com.blez.anime_player_compose.feature_video.presentation

import android.app.ActionBar
import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build
import android.view.ViewGroup
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.OptIn
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.blez.anime_player_compose.R
import com.blez.anime_player_compose.common.util.Constants.PLAYER_SEEK_BACK_INCREMENT
import com.blez.anime_player_compose.common.util.Constants.PLAYER_SEEK_FORWARD_INCREMENT
import com.blez.anime_player_compose.common.util.Screen
import com.blez.anime_player_compose.feature_detail_info.presentation.DetailViewModel
import com.blez.anime_player_compose.feature_video.presentation.components.PlayerController
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@kotlin.OptIn(ExperimentalAnimationApi::class)
@OptIn(UnstableApi::class)
@Composable
fun VideoScreen(
    navController: NavHostController,
    window: Window,
    actionBar: ActionBar?,
    episodeId: String,
    title: String,
    videoViewModel: VideoViewModel = hiltViewModel(),
    detailViewModel: DetailViewModel = hiltViewModel(),
    epiNumber: Int,
    animeId: String
) {

    val episodeNumber = "Episode : ${epiNumber}"
    DisposableEffect(key1 =  Unit){
        videoViewModel.fetchVideo(episodeId)
        detailViewModel.fetchDetails(animeId = animeId )
        onDispose {  }
    }

    val activity = LocalContext.current as Activity
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE






    val videoState by videoViewModel.fetchVideoState.collectAsState()
    val loadComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.video_load))
    val errorComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.error_load))

    val state by detailViewModel.detailState.collectAsState()
    when(state){
        is DetailViewModel.DetailsUIEvent.Failure -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column {
                    LottieAnimation(
                        modifier = Modifier.size(250.dp),
                        composition = errorComposition,
                        iterations = LottieConstants.IterateForever
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(text = "Error..")
                }

            }
        }
        DetailViewModel.DetailsUIEvent.Loading -> {}
        is DetailViewModel.DetailsUIEvent.Success -> {

            when(videoState) {
                is VideoViewModel.VideoUIEvent.Failure -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column {
                            LottieAnimation(
                                modifier = Modifier.size(250.dp),
                                composition = errorComposition,
                                iterations = LottieConstants.IterateForever
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(text = "Error..")
                        }

                    }


                }
                VideoViewModel.VideoUIEvent.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column {
                            LottieAnimation(
                                modifier = Modifier.size(250.dp),
                                composition = loadComposition,
                                iterations = LottieConstants.IterateForever
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(text = "Loading..", fontSize = 24.sp)
                        }

                    }
                }
                is VideoViewModel.VideoUIEvent.Success -> {


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


                    val data = (videoState as VideoViewModel.VideoUIEvent.Success).data
                    val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
                        .setUserAgent(context.getString(R.string.app_name))
                        .setConnectTimeoutMs(DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS)
                        .setReadTimeoutMs(1800000)
                        .setAllowCrossProtocolRedirects(true)

                    dataSourceFactory.createDataSource()


                    //val mediaItem = MediaItem.fromUri("https://www081.vipanicdn.net/streamhls/9701aab79df62c91ccc6614ac056c3e3/ep.1.1704382353.m3u8")

                    val uri_1080p = data.sources.find {
                        it.quality == "1080p"
                    }

                    val mediaItem = MediaItem.Builder()
                        .setUri(uri_1080p?.url ?: "https://www109.vipanicdn.net/streamhls/65be8eec9685db8f181d58395efb8597/ep.1.1704567536.1080.m3u8")
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

                        LaunchedEffect(key1 = currentTime) {
                            delay(1_000)
                            currentTime = exoPlayer.currentPosition.coerceAtLeast(0L)
                        }


                        val aspectRatio =
                            listOf(AspectRatioFrameLayout.RESIZE_MODE_FIT, AspectRatioFrameLayout.RESIZE_MODE_FILL)

                        var aspect by remember {
                            mutableStateOf(AspectRatioFrameLayout.RESIZE_MODE_FILL)
                        }

                        var currentVolume by remember {
                            mutableStateOf(0f)
                        }
                        val episodeData = (state as DetailViewModel.DetailsUIEvent.Success).data
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

                        var muteState by remember() {
                            mutableStateOf(false)
                        }

                        var pauseState by remember {
                            mutableStateOf(false)
                        }
                        PlayerController(
                            title = title,
                            navHostController = navController,
                            onBackPressed = {

                            },
                            isSubtitleEnabled = {

                                /*   if (aspect == AspectRatioFrameLayout.RESIZE_MODE_FIT) {
                                       aspect = AspectRatioFrameLayout.RESIZE_MODE_FILL

                                   } else {
                                       AspectRatioFrameLayout.RESIZE_MODE_FIT
                                   }*/
                            },
                            isVisible = { shouldShowControls },
                            currentTime = { currentTime },
                            startTime = { 0L },
                            onPausedClicked = {
                                if (it) {
                                    exoPlayer.pause()
                                    pauseState = true
                                } else {
                                    exoPlayer.play()
                                    pauseState = false
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
                            onMuteClicked = {
                                if (it) {
                                    currentVolume = exoPlayer.volume
                                    exoPlayer.volume = 0f
                                    muteState = true
                                }
                                if (!it) {
                                    exoPlayer.volume = currentVolume
                                    muteState = false
                                }
                            },
                            muteState = { muteState },
                            episodeNumber = episodeNumber,
                            qualityLists = data.sources,
                            pauseState = { pauseState },
                            episodeIds =  episodeData.episodes,
                            nextVideoClicked = {
                               // val episodeData = episodeData.episodes.filter { it.number > episodeNumber[episodeNumber.length - 1].code }.sortedBy { it.number }.first()
                                try {
                                    val episodeNext = episodeData.episodes.filter { it.number > epiNumber }

                                    navController.navigate(Screen.VideoScreen.passInfo(episodeNext.first().id, title,episodeNext.first().number, animeId = animeId))


                                }catch (e : Exception){
                                    throw e
                                }
                            }
                        )
                    }
                }
            }
        }
    }



}

