package com.blez.anime_player_compose.feature_detail_info.presentation

import android.view.Window
import android.view.WindowManager
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Downloading
import androidx.compose.material.icons.outlined.PlayCircleFilled
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.blez.anime_player_compose.R
import com.blez.anime_player_compose.feature_dashboard.presentation.component.EpisodeCard
import com.blez.anime_player_compose.feature_detail_info.domain.model.Episode
import com.blez.anime_player_compose.feature_detail_info.presentation.components.ExpandableText
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch


@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel = hiltViewModel(),
    animeId: String,
    navController: NavHostController,
    window: Window
) {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
    DisposableEffect(Unit) {
        detailViewModel.fetchDetails(animeId)
        onDispose {}
    }
    val state by detailViewModel.detailState.collectAsState()
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var backPressHandled by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    BackHandler(enabled = !backPressHandled) {
        backPressHandled = true
        coroutineScope.launch {
            awaitFrame()
            onBackPressedDispatcher?.onBackPressed()
            backPressHandled = false
        }
    }
    when (state) {
        is DetailViewModel.DetailsUIEvent.Failure -> {}
        DetailViewModel.DetailsUIEvent.Loading -> {}
        is DetailViewModel.DetailsUIEvent.Success -> {
            val data = (state as DetailViewModel.DetailsUIEvent.Success).data
            val image = data.image
            val title = data.title
            val genres = data.genres
            val releasedYear = data.releaseDate
            val totalEpisodes = data.totalEpisodes
            val status = data.status
            val description = data.description
            val textColor = Color.White
            val otherName = data.otherName
            val subOrDub = data.subOrDub
            val episodes = data.episodes



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
                            .padding(top = 20.dp, start = 5.dp),
                        contentAlignment = Alignment.TopStart
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_back_arrow_extended),
                            contentDescription = "Background button",
                            modifier = Modifier
                                .width(50.dp)
                                .height(45.dp)
                                .padding(7.dp)
                                .clickable {
                                    backPressHandled = !backPressHandled
                                    navController.popBackStack()

                                },
                            contentScale = ContentScale.FillBounds
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 20.dp, end = 5.dp),
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
                Spacer(
                    modifier = Modifier
                        .background(Color.Red)
                        .fillMaxWidth()
                        .height(5.dp)
                )

                Spacer(modifier = Modifier.height(25.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                ) {
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
                        Text(
                            text = "Status : $status",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Box(
                            modifier = Modifier
                                .border(
                                    width = 2.dp,
                                    color = Color.White,
                                    shape = RectangleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = subOrDub.uppercase(),
                                color = textColor,
                                modifier = Modifier.padding(2.dp)
                            )
                        }

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
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.3f)
                                .clip(RoundedCornerShape(45))
                                .background(color = Color.Red)
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row {
                                Icon(
                                    imageVector = Icons.Outlined.PlayCircleFilled,
                                    contentDescription = "Play Button",
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(text = "Play", color = textColor, fontWeight = FontWeight.Bold)
                            }

                        }


                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .clip(RoundedCornerShape(45))
                                .background(color = Color.Red)
                                .padding(horizontal = 7.dp, vertical = 5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row {
                                Icon(
                                    imageVector = Icons.Outlined.Downloading,
                                    contentDescription = "Download Button",
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "Dowload",
                                    color = textColor,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                        }


                    }
                    Spacer(modifier = Modifier.height(7.dp))
                    Text(text = "Episodes", color = textColor, fontSize = 16.sp)
                    LazyVerticalGrid(columns = GridCells.Adaptive(250.dp), content = {
                        items(episodes.toList()) {
                            EpisodeCard(
                                modifier = Modifier.padding(5.dp),
                                id = it.id,
                                episodeNumber = it.number.toString(),
                                episodeUrl = it.url,
                                imageUrl = image,
                                synopsis = "Episode ${it.number} of $title"
                            )
                        }
                    })
                }

            }
        }
    }


}