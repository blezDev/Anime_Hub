package com.blez.anime_player_compose.feature_video.presentation.components

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.blez.anime_player_compose.R


@Composable
fun PlayerController(
    modifier: Modifier = Modifier,
    title: String,
    navHostController: NavHostController,
    onBackPressed: () -> Unit,
    isSubtitleEnabled: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        TopControl(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth()
                .padding(16.dp),
            title = title,
            navController = navHostController,
            onBackPressed = onBackPressed,
            isSubtitleEnabled = isSubtitleEnabled
        )


        CenterControl(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        )


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp), verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DurationControl(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)

            )
            BottomControl(
                modifier = Modifier
                    .fillMaxWidth()

            )
        }

    }
}

@Composable
fun TopControl(
    modifier: Modifier = Modifier,
    title: String,
    onBackPressed: () -> Unit,
    navController: NavHostController,
    isSubtitleEnabled: (Boolean) -> Unit
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


@Composable
fun DurationControl(
    modifier: Modifier = Modifier,
    currentTime: Long = 0L,
    startTime: Long = 0L,
    endTime: Long = 100000L
) {
    var sliderValue by remember {
        mutableFloatStateOf(currentTime.toFloat())
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = (sliderValue / 1000).toInt().toString(), color = Color.White)
        Spacer(modifier = Modifier.width(5.dp))
        Slider(
            modifier = Modifier.weight(1f),
            value = sliderValue,
            onValueChange = {
                sliderValue = it
            },
            valueRange = startTime.toFloat()..endTime.toFloat(),
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
            text = (endTime.toFloat() / 1000).toInt().toString(),
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
) {
    var onPausedEnabled by remember {
        mutableStateOf(false)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {

        ReverseButton()
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
        ForwardButton()
        Spacer(modifier = Modifier.width(15.dp))

    }

}


@Composable
fun BottomControl(
    modifier: Modifier = Modifier,
    onLockedUIClicked: (Boolean) -> Unit = {},
    onMuteClicked: (Boolean) -> Unit = {},
    onBackwardClicked: (Long) -> Unit = {},
    onForwardClicked: (Long) -> Unit = {},
    onPausedClicked: (Boolean) -> Unit = {},
    onQualityClicked: (String) -> Unit = {},
    onFullScreenClicked: () -> Unit = {}
) {
    var onLockedEnabled by remember {
        mutableStateOf(false)
    }
    var onMutedEnabled by remember {
        mutableStateOf(false)
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
            Text(text = "AUTO", color = Color.White)
            Spacer(modifier = Modifier.width(30.dp))

        }


    }
}


@Composable
fun ReverseButton(
    modifier: Modifier = Modifier,
    reverseTimer: Long = 5000L,
    onClicked: () -> Long = { 5000L }
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.reverse_svg),
            contentDescription = "Reverse Icon"
        )
        Text(text = (reverseTimer / 1000).toString(), color = Color.White)
    }

}


@Composable
fun ForwardButton(
    modifier: Modifier = Modifier,
    reverseTimer: Long = 5000L,
    onClicked: () -> Long = { 5000L }
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Image(
            painter = painterResource(id = R.drawable.forward_svg),
            contentDescription = "Forward Icon"
        )
        Text(text = (reverseTimer / 1000).toString(), color = Color.White)
    }
}

