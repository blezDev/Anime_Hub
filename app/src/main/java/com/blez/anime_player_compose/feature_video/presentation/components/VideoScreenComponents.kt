@file:OptIn(ExperimentalMaterial3Api::class)

package com.blez.anime_player_compose.feature_video.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.VolumeOff
import androidx.compose.material.icons.automirrored.outlined.VolumeUp
import androidx.compose.material.icons.filled.BroadcastOnHome
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.SubtitlesOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.LockOpen
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavHostController
import com.blez.anime_player_compose.R
import com.blez.anime_player_compose.common.util.Constants.PLAYER_SEEK_BACK_INCREMENT
import com.blez.anime_player_compose.common.util.Constants.PLAYER_SEEK_FORWARD_INCREMENT
import com.blez.anime_player_compose.feature_detail_info.domain.model.Gogoanime_Episode
import com.blez.anime_player_compose.feature_video.domain.model.Source
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit


@ExperimentalAnimationApi
@Composable
fun PlayerController(
    modifier: Modifier = Modifier,
    isVisible: () -> Boolean,
    title: String,
    episodeNumber: String,
    navHostController: NavHostController,
    onBackPressed: () -> Unit,
    isSubtitleEnabled: (Boolean) -> Unit,
    currentTime: () -> Long = { 0L },
    startTime: () -> Long = { 0L },
    endTime: () -> Long = { 100L },
    bufferedPercentage: () -> Int = { 0 },
    onPausedClicked: (Boolean) -> Unit,
    onSeekChanged: (timeMs: Float) -> Unit,
    onForwardClicked: (Long) -> Unit,
    onBackwardClicked: (Long) -> Unit,
    onMuteClicked: (Boolean) -> Unit,
    muteState: () -> Boolean = { false },
    pauseState: () -> Boolean = { false },
    qualityLists: List<Source>,
    nextVideoClicked: () -> Unit = {},
    episodeIds: List<Gogoanime_Episode>


) {
    val visible = remember(isVisible()) { isVisible() }
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
        ) {
            TopControl(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth()
                    .padding(16.dp),
                title = title,
                episodeNumber = episodeNumber,
                navController = navHostController,
                onBackPressed = onBackPressed,
                isSubtitleEnabled = isSubtitleEnabled
            )


            CenterControl(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                onPausedClicked = {
                    onPausedClicked(it)
                },
                onForwardClicked = onForwardClicked,
                onBackwardClicked = onBackwardClicked,
                pauseState = pauseState
            )


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
                    .animateEnterExit(
                        enter =
                        slideInVertically(
                            initialOffsetY = { fullHeight: Int ->
                                fullHeight
                            }
                        ),
                        exit =
                        slideOutVertically(
                            targetOffsetY = { fullHeight: Int ->
                                fullHeight
                            }
                        ),
                    ), verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DurationControl(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    currentTime = currentTime,
                    startTime = startTime,
                    endTime = endTime,
                    bufferedPercentage = bufferedPercentage,
                    onSeekChanged = onSeekChanged

                )
                BottomControl(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onMuteClicked = onMuteClicked,
                    muteState = muteState,
                    qualityList = qualityLists,
                    episodeIds = episodeIds,
                    episodeNumber = episodeNumber,
                    nextVideoClicked = {
                        nextVideoClicked()
                    }

                )
            }

        }
    }
}

@Composable
fun TopControl(
    modifier: Modifier = Modifier,
    title: String,
    onBackPressed: () -> Unit,
    navController: NavHostController,
    isSubtitleEnabled: (Boolean) -> Unit,
    episodeNumber: String
) {
    var isSubtitleOn by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            /*      Image(
                      painter = painterResource(id = R.drawable.ic_back_arrow_extended),
                      contentDescription = "Background button",
                      modifier = Modifier
                          .width(50.dp)
                          .height(45.dp)
                          .align(Alignment.CenterVertically)
                          .clickable {
                              onBackPressed()
                              navController.popBackStack()

                          },
                      contentScale = ContentScale.FillBounds
                  )*/
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back button",
                tint = Color.White,
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.CenterVertically)
                    .clickable {
                        onBackPressed()
                        navController.popBackStack()

                    }
            )
            Spacer(modifier = Modifier.width(20.dp))
            val trunText = if (title.length > 25) {
                title.substring(0, 25) + " ...."
            } else title
            Text(
                modifier = Modifier,
                text = trunText,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }
        //End side
        Row(
            modifier = Modifier, verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                modifier = Modifier,
                text = episodeNumber,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            Spacer(modifier = Modifier.width(14.dp))

            if (isSubtitleOn) {
                Icon(
                    modifier = Modifier
                        .size(34.dp)
                        .clickable {
                            isSubtitleOn = !isSubtitleOn
                            isSubtitleEnabled(isSubtitleOn)
                        },
                    imageVector = Icons.Default.Subtitles,
                    contentDescription = "Substitute",
                    tint = Color.White
                )
            } else {
                Icon(
                    modifier = Modifier
                        .size(34.dp)
                        .clickable {
                            isSubtitleOn = !isSubtitleOn
                            isSubtitleEnabled(isSubtitleOn)
                        },
                    imageVector = Icons.Default.SubtitlesOff,
                    contentDescription = "Substitute",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(14.dp))

            Icon(
                modifier = Modifier.size(34.dp),
                imageVector = Icons.Default.BroadcastOnHome,
                contentDescription = "Broadcast Button",
                tint = Color.White
            )

            Spacer(modifier = Modifier.width(14.dp))
            Icon(
                modifier = Modifier.size(34.dp),
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More Option Button",
                tint = Color.White
            )
        }
    }
}


fun Long.formatMinSec(): String {
    return if (this == 0L) {
        "..."
    } else {
        String.format(
            "%02d:%02d",
            TimeUnit.MILLISECONDS.toMinutes(this),
            TimeUnit.MILLISECONDS.toSeconds(this) -
                    TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(this)
                    )
        )
    }
}


@Composable
fun DurationControl(
    modifier: Modifier = Modifier,
    currentTime: () -> Long = { 0L },
    startTime: () -> Long = { 0L },
    endTime: () -> Long = { 10000L },
    bufferedPercentage: () -> Int = { 0 },
    onSeekChanged: (timeMs: Float) -> Unit = {}
) {

    val duration = remember(endTime()) { endTime() }
    val videoTime = remember(currentTime()) { currentTime() }
    var buffer = remember(bufferedPercentage()) { bufferedPercentage() }

    var timer by remember { mutableStateOf(videoTime) }
    LaunchedEffect(key1 = timer) {
        if (timer > 0) {
            delay(1_000)
            timer = timer + 1
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = videoTime.formatMinSec(), color = Color.White)
        Spacer(modifier = Modifier.width(5.dp))
        Slider(
            modifier = Modifier.weight(1f),
            value = videoTime.toFloat(),
            onValueChange = {
                onSeekChanged(it)

            },
            valueRange = 0f..duration.toFloat(),
            /*   steps = 10,*/
            onValueChangeFinished = {

            },
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTickColor = Color.Red,
                activeTrackColor = Color.Red
            )
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = duration.formatMinSec(),
            color = Color.White
        )
    }
}


@Composable
fun CenterControl(
    modifier: Modifier = Modifier,
    onBackwardClicked: (Long) -> Unit = {},
    onForwardClicked: (Long) -> Unit = {},
    onPausedClicked: (Boolean) -> Unit = {},
    pauseState: () -> Boolean,
) {
    var onPausedEnabled by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = pauseState()) {
        onPausedEnabled = pauseState()
    }


    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {

        ReverseButton(onClicked = {
            onBackwardClicked(it)
        })
        Spacer(modifier = Modifier.width(15.dp))
        if (onPausedEnabled) {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        onPausedEnabled = !onPausedEnabled
                        onPausedClicked(onPausedEnabled)
                    },
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Pause Button",
                tint = Color.White
            )
        } else {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        onPausedEnabled = !onPausedEnabled
                        onPausedClicked(onPausedEnabled)
                    },
                imageVector = Icons.Filled.Pause,
                contentDescription = "Pause Button",
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.width(15.dp))
        ForwardButton(
            onClicked = {
                onForwardClicked(it)
            }
        )
        Spacer(modifier = Modifier.width(15.dp))

    }

}


@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun BottomControl(
    modifier: Modifier = Modifier,
    onLockedUIClicked: (Boolean) -> Unit = {},
    onMuteClicked: (Boolean) -> Unit = {},
    onQualityClicked: (String) -> Unit = {},
    onFullScreenClicked: () -> Unit = {},
    muteState: () -> Boolean = { false },
    qualityList: List<Source>? = listOf(),
    nextVideoClicked: () -> Unit,
    episodeIds: List<Gogoanime_Episode>,
    episodeNumber: String
) {
    var onLockedEnabled by remember {
        mutableStateOf(false)
    }

    var onMutedEnabled by remember { mutableStateOf(false) }


    var isExpanded by remember {
        mutableStateOf(false)
    }

    var qualitySelected by remember {
        mutableStateOf("AUTO")
    }


    val options = listOf("1080p", "480p", "360p", "Outing", "Other")

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }



    LaunchedEffect(key1 = muteState()) {
        onMutedEnabled = muteState()
    }


    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {

            if (onLockedEnabled) {
                Icon(
                    modifier = Modifier
                        .size(34.dp)
                        .clickable {
                            onLockedEnabled = !onLockedEnabled
                            onLockedUIClicked(onLockedEnabled)
                        },
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = "UI Locked Button",
                    tint = Color.White
                )
            } else {
                Icon(
                    modifier = Modifier
                        .size(34.dp)
                        .clickable {
                            onLockedEnabled = !onLockedEnabled
                            onLockedUIClicked(onLockedEnabled)
                        },
                    imageVector = Icons.Outlined.LockOpen,
                    contentDescription = "UI Locked Open Button",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
            if (onMutedEnabled) {
                Icon(
                    modifier = Modifier
                        .size(34.dp)
                        .clickable {
                            onMutedEnabled = !onMutedEnabled
                            onMuteClicked(onMutedEnabled)
                        },
                    imageVector = Icons.AutoMirrored.Outlined.VolumeOff,
                    contentDescription = "Volume On Button",
                    tint = Color.White
                )
            } else {
                Icon(
                    modifier = Modifier
                        .size(34.dp)
                        .clickable {
                            onMutedEnabled = !onMutedEnabled
                            onMuteClicked(onMutedEnabled)
                        },
                    imageVector = Icons.AutoMirrored.Outlined.VolumeUp,
                    contentDescription = "Volume Off Button",
                    tint = Color.White
                )
            }

        }


        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.width(15.dp))
            val episodeList = episodeIds.filter { "Episode : ${it.number}" != episodeNumber.last().toString() }
            val list = episodeIds.filter { it.number >   episodeNumber.last().toString().toInt() }
            if (episodeIds.isNotEmpty() && episodeList.isNotEmpty() && list.isNotEmpty()){
                Text(text = "Next", color = Color.White, modifier = Modifier.clickable {
                    nextVideoClicked()
                })
            }


            Spacer(modifier = Modifier.width(15.dp))

            Text(text = "1080p", color = Color.White)


            /* ExposedDropdownMenuBox(
                 expanded = expanded,
                 onExpandedChange = {
                     expanded = !expanded
                 }
             ) {
                 TextField(
                     readOnly = true,
                     value = selectedOptionText,
                     onValueChange = { },
                     label = { Text("1080p") },
                     trailingIcon = {
                         ExposedDropdownMenuDefaults.TrailingIcon(
                             expanded = expanded
                         )
                     },
                     colors = ExposedDropdownMenuDefaults.textFieldColors()
                 )

                 ExposedDropdownMenu(
                     expanded = expanded,
                     onDismissRequest = {
                         expanded = false
                     }
                 ) {
                     options.forEach { selectionOption ->
                         DropdownMenuItem(
                             text = { Text(text = selectionOption) },
                             onClick = {
                                 selectedOptionText = selectionOption
                                 expanded = false
                             },
                         )
                     }
                 }

             }*/


            /*    DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {

                    if (qualityList != null) {
                        for (i in qualityList) {
                            DropdownMenuItem(text = { Text(text = i.quality, color = Color.Black)}, onClick = {
                                qualitySelected = i.quality
                                isExpanded = false
                            })
                        }
                    }


                }*/


            /*       ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { isExpanded = it }) {
                       TextField(
                           value = qualitySelected,
                           onValueChange = {},
                           readOnly = true,
                           colors= TextFieldDefaults.colors(unfocusedTextColor = Color.Black),
                           modifier = Modifier.menuAnchor()
                       )
                       ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false}) {
                           if (qualityList != null) {
                               for (i in qualityList){
                                   DropdownMenuItem(text = { i.quality }, onClick = {
                                       qualitySelected = i.quality
                                       isExpanded = false
                                   })
                               }
                           }

                       }
                   }*/






















            Spacer(modifier = Modifier.width(30.dp))

        }


    }
}


@Composable
fun ReverseButton(
    modifier: Modifier = Modifier,

    onClicked: (Long) -> Unit = { }
) {
    Box(modifier = modifier.clickable {
        onClicked(PLAYER_SEEK_BACK_INCREMENT)
    }, contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.reverse_svg),
            contentDescription = "Reverse Icon"
        )
        Text(text = (PLAYER_SEEK_BACK_INCREMENT / 1000).toString(), color = Color.White)
    }

}


@Composable
fun ForwardButton(
    modifier: Modifier = Modifier,
    onClicked: (Long) -> Unit = { }
) {
    Box(
        modifier = modifier
            .clickable {
                onClicked(PLAYER_SEEK_FORWARD_INCREMENT)
            }, contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.forward_svg),
            contentDescription = "Forward Icon"
        )
        Text(text = (PLAYER_SEEK_FORWARD_INCREMENT / 1000).toString(), color = Color.White)
    }
}






