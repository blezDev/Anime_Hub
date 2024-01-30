package com.blez.anime_player_compose.feature_dashboard.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.blez.anime_player_compose.R


@Composable
fun ListButton(
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    plusIconColor: Color = Color.White,
    onClicked: () -> Unit
) {
    Column(modifier = modifier.shadow(10.dp).clickable {
        onClicked()
    }) {
        Image(
            imageVector = Icons.Default.Add,
            contentDescription = "Adding to List Button",
            colorFilter = ColorFilter.tint(
                plusIconColor
            ),
            alignment = Alignment.Center,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(R.string.personal_list_text),
            fontWeight = FontWeight.Bold,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = modifier.align(Alignment.CenterHorizontally)

        )
    }
}

@Composable
fun PlayButton(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    textColor: Color = Color.White,
    buttonColor: Color = Color.Red,
    onClicked: () -> Unit
) {

    Button(
        onClick = { onClicked() },
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        modifier = modifier
    ) {
        Text(
            modifier = textModifier,
            text = stringResource(R.string.play_text),
            fontWeight = FontWeight.Bold,
            color = textColor,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun InfoButton(
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    infoIconColor: Color = Color.White,
    onClicked: () -> Unit
) {
    Column(modifier = modifier.shadow(10.dp).clickable {
        onClicked()
    }) {
        Image(
            imageVector = Icons.Outlined.Info,
            contentDescription = "Adding to List Button",
            colorFilter = ColorFilter.tint(
                infoIconColor
            ),
            alignment = Alignment.Center,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(R.string.info_text),
            fontWeight = FontWeight.Bold,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = modifier.align(Alignment.CenterHorizontally)

        )
    }
}


@Composable
fun AnimeCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    episodeNumber: String,
    episodeId: String,
    animeId: String,
    onClicked: () -> Unit,
    textColor: Color = Color.Black,
) {
    Column(modifier.fillMaxSize().clickable { onClicked() }) {
        Box(modifier = modifier.fillMaxWidth()) {
            SubcomposeAsyncImage(
                modifier = modifier
                    .size(230.dp),
                model = imageUrl,
                contentDescription = "$title Image",
                loading = { CircularProgressIndicator() },
                error = {
                    Image(
                        painter = painterResource(id = R.drawable.error_png),
                        contentDescription = "Error in Image Loading"
                    )
                }, contentScale = ContentScale.Crop
            )
            Box(
                modifier = modifier
                    .background(Color.Black)
                    .padding(start = 5.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = episodeNumber.toString(),
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 28.sp,
                    modifier = modifier.padding(5.dp)
                )

            }
        }
        val trunText = if (title.length > 25) {title.substring(0,25) + " ...."} else title
        Text(
            text = trunText,
            color = textColor,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Clip,
            maxLines = 2,
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )


    }

}

@Composable
fun EpisodeCard(
    id: String,
    episodeNumber: String,
    episodeUrl: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    imageUrl: String,
    title : String,
    isFiller : Boolean
) {

    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        Box(
            modifier = modifier
                .fillMaxSize(0.4f)
                .background(Color.Black), contentAlignment = Center
        ) {


            SubcomposeAsyncImage(
                modifier = modifier
                    .size(width =  250.dp, height = 140.dp),
                model = imageUrl,
                contentDescription = "Anime Image",
                loading = { CircularProgressIndicator() },
                error = {
                    Image(
                        painter = painterResource(id = R.drawable.error_png),
                        contentDescription = "Error in Image Loading"
                    )
                }, contentScale = ContentScale.Crop
            )
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Start Button",
                modifier = Modifier.size(65.dp),
                tint = Color.LightGray
            )
        }


        Spacer(modifier = modifier.width(10.dp))
        Column(modifier = modifier.fillMaxHeight()) {
            Text(text = "Episode : $episodeNumber", color = textColor)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = title, color = Color.White,)
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "Filler : $isFiller", color = textColor)

        }

    }
}

@Composable
fun VerticalGrid(composableList: List<@Composable () -> Unit>, itemsPerRow: Int) {
    val components = composableList.toMutableList()
    Column(Modifier.fillMaxWidth()) {
        while (components.isNotEmpty()) {
            val rowComponents: List<@Composable () -> Unit> = components.take(itemsPerRow)
            val listOfSpacers: List<@Composable () -> Unit> = listOfSpacers(itemsPerRow - rowComponents.size)
            RowWithItems(items = rowComponents.plus(listOfSpacers))
            components.removeAll(rowComponents)
        }
    }
}

private fun listOfSpacers(number: Int): List<@Composable () -> Unit> {
    val mutableList = emptyList<@Composable () -> Unit>().toMutableList()
    repeat(number) {
        mutableList.add { Spacer(Modifier) }
    }
    return mutableList.toList()
}

@Composable
private fun RowWithItems(items: List<@Composable () -> Unit>) {
    Row(Modifier.fillMaxWidth()) {
        items.forEach { item ->
            Box(Modifier.weight(1f)) {
                item()
            }
        }
    }
}
