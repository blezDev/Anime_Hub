package com.blez.anime_player_compose.feature_dashboard.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.blez.anime_player_compose.R
import com.blez.anime_player_compose.feature_dashboard.presentation.component.AnimeCard
import com.blez.anime_player_compose.feature_dashboard.presentation.component.InfoButton
import com.blez.anime_player_compose.feature_dashboard.presentation.component.ListButton
import com.blez.anime_player_compose.feature_dashboard.presentation.component.PlayButton

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeScreen(
    backgroundColor: Color = Color(24, 18, 43),
    dashboardViewModel: DashboardViewModel = hiltViewModel()
) {
    val image = R.drawable.aot
    val genres = arrayOf("Action", "Drama", "Adventure", "Thriller")
    dashboardViewModel.fetchRecentRelease()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    DisposableEffect(Unit) {
        dashboardViewModel.fetchRecentRelease()
        dashboardViewModel.fetchTopAiring()
        onDispose {}
    }
    val state by dashboardViewModel.fetchState.collectAsState()
    val trendingState by dashboardViewModel.topAiringState.collectAsState()



    Column {
        when (trendingState) {
            is DashboardViewModel.AiringUIEvent.Failure -> {

            }

            DashboardViewModel.AiringUIEvent.Loading -> {

            }

            is DashboardViewModel.AiringUIEvent.Success -> {
                val data = (trendingState as DashboardViewModel.AiringUIEvent.Success).data
                val trend = data.results.random()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)

                ) {
                    SubcomposeAsyncImage(
                        model = trend.image,
                        contentDescription = "Latest Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                        loading = { CircularProgressIndicator() },
                        error = {
                            Image(
                                painter = painterResource(id = R.drawable.error_png),
                                contentDescription = "Error in Image Loading"
                            )
                        },
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize(), contentAlignment = Alignment.BottomCenter

                    ) {
                        Column(modifier = Modifier.padding(bottom = 10.dp)) {
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                content = {
                                    items(
                                        if (trend.genres.isEmpty()) genres.toList() else {
                                            if (trend.genres.size > 5) trend.genres.subList(0, 5)
                                                .toList() else trend.genres.toList()
                                        }
                                    ) {
                                        Text(
                                            text = it,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }
                                })
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {
                                ListButton() {
                                    //TODO LIST ADD FUNCTIONALITY
                                }
                                PlayButton(
                                    textModifier = Modifier.padding(horizontal = 9.dp),
                                    modifier = Modifier.clip(
                                        RoundedCornerShape(50)
                                    )
                                ) {
                                    //TODO Play FUNCTIONALITY
                                }
                                InfoButton {
                                    //TODO Info FUNCTIONALITY
                                }
                            }
                        }

                    }

                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.recent_release),
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            modifier = Modifier.padding(start = 10.dp)
        )
        when (state) {
            is DashboardViewModel.UIEvent.Failure -> {

            }

            DashboardViewModel.UIEvent.Loading -> {
                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            }

            is DashboardViewModel.UIEvent.Success -> {
                val result = (state as DashboardViewModel.UIEvent.Success)
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    columns = GridCells.Adaptive(150.dp),
                    content = {
                        items((state as DashboardViewModel.UIEvent.Success).data.results.size) {
                            AnimeCard(
                                modifier = Modifier.padding(2.dp),
                                imageUrl = result.data.results[it].image,
                                title = result.data.results[it].title,
                                episodeNumber = result.data.results[it].episodeNumber,
                                episodeId = result.data.results[it].episodeId,
                                animeId = result.data.results[it].id,
                                textColor = Color.White,
                                onClicked = {

                                }
                            )
                        }
                    })
            }
        }

    }

}

