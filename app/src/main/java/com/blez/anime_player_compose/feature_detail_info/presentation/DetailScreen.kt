package com.blez.anime_player_compose.feature_detail_info.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.blez.anime_player_compose.R
import com.blez.anime_player_compose.feature_dashboard.presentation.component.EpisodeCard
import com.blez.anime_player_compose.feature_detail_info.domain.model.Episode
import com.blez.anime_player_compose.feature_detail_info.presentation.components.ExpandableText
import com.blez.anime_player_compose.feature_detail_info.presentation.components.GenresClip


@Composable
fun DetailScreen() {
    val image = "https://gogocdn.net/cover/oroka-na-tenshi-wa-akuma-to-odoru-1704247848.png"
    val title = "Oroka na Tenshi wa Akuma to Odoru"
    val genres = arrayOf("Action", "Drama", "Adventure", "Thriller")
    val releasedYear = "2024"
    val totalEpisodes = 3
    val status = "Ongoing"
    val description =
        "Akutsu Masatora is a demon who has infiltrated a high school in the human world. His goal is to find candidates to aid Hell in the fight against their natural enemies, the angels of Heaven. Assigned a seat next to Lily Amane, another student who transferred here not long ago, Akutsu falls prey to her beauty and decides to recruit her to his cause.\\n\\nBut there's more to Lily than meets the eye, and Akutsu not only finds himself an accomplice to one of the very enemies he is supposed to defeat, but he also might have developed feelings for her."
    val textColor = Color.White
    val otherName = "The Foolish Angel Dances with Demons, 愚かな天使は悪魔と踊る"

    val episodes = arrayOf(
        Episode(
            "oroka-na-tenshi-wa-akuma-to-odoru-episode-1",
            1,
            "https://gogoanime3.net//oroka-na-tenshi-wa-akuma-to-odoru-episode-1"
        ),
        Episode(
            "oroka-na-tenshi-wa-akuma-to-odoru-episode-2",
            2,
            "https://gogoanime3.net//oroka-na-tenshi-wa-akuma-to-odoru-episode-2"
        ),
        Episode(
            "oroka-na-tenshi-wa-akuma-to-odoru-episode-3",
            3,
            "https://gogoanime3.net//oroka-na-tenshi-wa-akuma-to-odoru-episode-3"
        ),
        Episode(
            "oroka-na-tenshi-wa-akuma-to-odoru-episode-4",
            4,
            "https://gogoanime3.net//oroka-na-tenshi-wa-akuma-to-odoru-episode-4"
        ),
        Episode(
            "oroka-na-tenshi-wa-akuma-to-odoru-episode-5",
            5,
            "https://gogoanime3.net//oroka-na-tenshi-wa-akuma-to-odoru-episode-5"
        ),
    )



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {


        /*  SubcomposeAsyncImage(
              model = image ,
              contentDescription = "Anime Image",
              contentScale = ContentScale.FillBounds,
              modifier = Modifier.fillMaxWidth(0.7f).fillMaxHeight(0.3f).align(Alignment.CenterHorizontally),
              loading = { CircularProgressIndicator() },
              error = {
                  Image(
                      painter = painterResource(id = R.drawable.error_png),
                      contentDescription = "Error in Image Loading"
                  )
              },
          )*/

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
        ) {


            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    model = image,
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

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_back_arrow_extended),
                    contentDescription = "Background button",
                    modifier = Modifier
                        .width(50.dp)
                        .height(45.dp)
                        .padding(7.dp),
                    contentScale = ContentScale.FillBounds
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp, end = 5.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                Image(
                    painter = painterResource(id = R.drawable.share_to),
                    contentDescription = "Background button",
                    modifier = Modifier
                        .width(50.dp)
                        .height(45.dp)
                        .padding(7.dp),
                    contentScale = ContentScale.FillBounds
                )
            }

        }


        Spacer(modifier = Modifier.height(25.dp))
        Row {

            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 19.sp,
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Clip,
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                imageVector = Icons.Filled.Bookmark,
                contentDescription = "Saved Button",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = "Share Button",
                tint = Color.White
            )
        }

        /*     BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                 LazyHorizontalStaggeredGrid(rows = StaggeredGridCells.Adaptive(maxWidth), content ={
                     items(genres.toList()){
                         GenresClip(text = it, modifier = Modifier)
                     }
                 } )
               *//*  LazyHorizontalGrid(
                    modifier = Modifier.fillMaxWidth(),
                    rows = GridCells.Adaptive(maxWidth),
                    content = {

                    })*//*
            }*/
        Spacer(modifier = Modifier.height(7.dp))

        Row {
            Text(text = releasedYear, color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = " | ", color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "${totalEpisodes} Episodes",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = " | ", color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "Status : $status", color = Color.White, fontWeight = FontWeight.Bold)

        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Genre : ${genres.joinToString(", ")}",
            color = textColor,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(7.dp))
        ExpandableText(text = description, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Other Name : $otherName", color = textColor)
        Spacer(modifier = Modifier.height(7.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            ElevatedButton(
                onClick = {},
                colors = ButtonDefaults.buttonColors(Color.Red),
                elevation = ButtonDefaults.buttonElevation(10.dp),
                shape = RoundedCornerShape(35)
            ) {
                Text(text = "Play", modifier = Modifier.padding(horizontal = 10.dp))
            }
            ElevatedButton(
                onClick = {},
                colors = ButtonDefaults.buttonColors(Color.Red),
                elevation = ButtonDefaults.buttonElevation(10.dp),
                shape = RoundedCornerShape(35)
            ) {
                Text(text = "Download")
            }
        }
        Spacer(modifier = Modifier.height(7.dp))
        Text(text = "Episodes", color = textColor, fontSize = 16.sp)
        LazyVerticalGrid(columns = GridCells.Adaptive(250.dp), content ={
            items(episodes.toList()) {
                EpisodeCard(
                    modifier = Modifier.padding(5.dp),
                    id = it.id,
                    episodeNumber = it.number.toString(),
                    episodeUrl = it.url,
                    imageUrl = image
                )
            }
        } )

    }
}