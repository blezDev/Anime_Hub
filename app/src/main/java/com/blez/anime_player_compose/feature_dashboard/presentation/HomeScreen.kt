package com.blez.anime_player_compose.feature_dashboard.presentation

import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.blez.anime_player_compose.R
import com.blez.anime_player_compose.common.util.Screen
import com.blez.anime_player_compose.feature_dashboard.domain.model.Result
import com.blez.anime_player_compose.feature_dashboard.domain.model.Zoro_Result
import com.blez.anime_player_compose.feature_dashboard.presentation.component.AnimeCard
import com.blez.anime_player_compose.feature_dashboard.presentation.component.InfoButton
import com.blez.anime_player_compose.feature_dashboard.presentation.component.ListButton
import com.blez.anime_player_compose.feature_dashboard.presentation.component.PlayButton
import com.blez.anime_player_compose.feature_dashboard.presentation.component.VerticalGrid


@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    backgroundColor: Color = Color(24, 18, 43),
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    navController: NavHostController,
    window: Window
) {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
    val image = R.drawable.aot
    val genres = arrayOf("Action", "Drama", "Adventure", "Thriller")
    dashboardViewModel.fetchRecentRelease()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    DisposableEffect(Unit) {
        dashboardViewModel.fetchRecentRelease()
        dashboardViewModel.fetchTopAiring()
        dashboardViewModel.fetchRecentAdded()
        dashboardViewModel.fetchCompletedAnime()
        onDispose {}
    }
    val state by dashboardViewModel.fetchState.collectAsState()
    val trendingState by dashboardViewModel.topAiringState.collectAsState()
    val recentData by dashboardViewModel.fetchRecentAdded.collectAsState()
    val completedAnimeData by dashboardViewModel.fetchCompletedAnime.collectAsState()
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.meowjo))


    LazyColumn(modifier = Modifier.fillMaxSize(), content = {
        item {
            when (trendingState) {
                is DashboardViewModel.AiringUIEvent.Failure -> {

                }

                DashboardViewModel.AiringUIEvent.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column {
                            LottieAnimation(
                                modifier = Modifier.size(250.dp),
                                composition = composition,
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(text = "Loading..")
                        }

                    }
                }

                is DashboardViewModel.AiringUIEvent.Success -> {
                    val data = (trendingState as DashboardViewModel.AiringUIEvent.Success).data
                    val trend = data.results.random()
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.3f)

                        ) {
                            SubcomposeAsyncImage(
                                model = trend.image,
                                contentDescription = "Latest Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxWidth().height(500.dp),
                                loading = { CircularProgressIndicator() },
                                error = {
                                    Image(
                                        painter = painterResource(id = R.drawable.error_png),
                                        contentDescription = "Error in Image Loading"
                                    )
                                },
                            )


                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.5f), contentAlignment = Alignment.BottomCenter

                        ) {
                            Column(modifier = Modifier.padding(bottom = 10.dp)) {
                                /*      Row(
                                          modifier = Modifier
                                              .fillMaxWidth()
                                              .padding(horizontal = 20.dp),
                                          horizontalArrangement = Arrangement.SpaceBetween,
                                      ) {
                                          for (it in if (trend.genres.isEmpty()) genres.toList() else {
                                              if (trend.genres.size > 5) trend.genres.subList(0, 5)
                                                  .toList() else trend.genres.toList()
                                          }) {
                                              Text(
                                                  text = it,
                                                  fontWeight = FontWeight.Bold,
                                                  color = Color.White
                                              )
                                          }
                                      }*/
                                Text(
                                    text = trend.title,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 24.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
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
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.recent_release),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
        item {

            when (state) {
                is DashboardViewModel.UIEvent.Failure -> {

                }

                DashboardViewModel.UIEvent.Loading -> {

                }

                is DashboardViewModel.UIEvent.Success -> {
                    val result = (state as DashboardViewModel.UIEvent.Success)
                    Column(modifier = Modifier.fillMaxWidth()) {
                        LazyRow(content = {
                            items(result.data.results) { it ->
                                AnimeCard(
                                    modifier = Modifier.padding(5.dp),
                                    imageUrl = it.image,
                                    title = it.title,
                                    episodeNumber = it.sub.toString(),
                                    episodeId = it.duration,
                                    animeId = it.id,
                                    textColor = Color.White,
                                    onClicked = {
                                        navController.navigate(
                                            route = Screen.DetailScreen.passAnimeId(
                                                it.id,
                                                it.japaneseTitle
                                            )
                                        )
                                    }
                                )
                            }
                        })
                    }

                }
            }

        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.recent_added),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
        item {
            when (recentData) {
                is DashboardViewModel.RecentAddedUIEvent.Failure -> {

                }

                DashboardViewModel.RecentAddedUIEvent.Loading -> {

                }

                is DashboardViewModel.RecentAddedUIEvent.Success -> {
                    val result = (recentData as DashboardViewModel.RecentAddedUIEvent.Success)

                    Column(modifier = Modifier.fillMaxWidth()) {
                        LazyRow(content = {
                            items(result.data.results) { it ->
                                AnimeCard(
                                    modifier = Modifier.padding(5.dp),
                                    imageUrl = it.image,
                                    title = it.title,
                                    episodeNumber = it.type,
                                    episodeId = it.duration,
                                    animeId = it.id,
                                    textColor = Color.White,
                                    onClicked = {
                                        navController.navigate(
                                            route = Screen.DetailScreen.passAnimeId(
                                                it.id,
                                                it.japaneseTitle
                                            )
                                        )
                                    }
                                )
                            }
                        })
                    }

                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.top_airing),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        item {
            when(trendingState){
                is DashboardViewModel.AiringUIEvent.Failure -> {}
                DashboardViewModel.AiringUIEvent.Loading -> {}
                is DashboardViewModel.AiringUIEvent.Success -> {
                    val result = (trendingState as DashboardViewModel.AiringUIEvent.Success)
                    Column(modifier = Modifier.fillMaxWidth()) {
                        LazyRow(content = {
                            items(result.data.results) { it ->
                                AnimeCard(
                                    modifier = Modifier.padding(5.dp),
                                    imageUrl = it.image,
                                    title = it.title,
                                    episodeNumber = it.type,
                                    episodeId = it.duration,
                                    animeId = it.id,
                                    textColor = Color.White,
                                    onClicked = {
                                        navController.navigate(
                                            route = Screen.DetailScreen.passAnimeId(
                                                it.id,
                                                it.japaneseTitle
                                            )
                                        )
                                    }
                                )
                            }
                        })
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.completed_anime),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
        item {
            when(completedAnimeData){
                is DashboardViewModel.CompletedAnimeUIEvent.Failure -> {}
                DashboardViewModel.CompletedAnimeUIEvent.Loading -> {}
                is DashboardViewModel.CompletedAnimeUIEvent.Success -> {
                    val result = (completedAnimeData as DashboardViewModel.CompletedAnimeUIEvent.Success)
                    Column(modifier = Modifier.fillMaxWidth()) {
                        LazyRow(content = {
                            items(result.data.results) { it ->
                                AnimeCard(
                                    modifier = Modifier.padding(5.dp),
                                    imageUrl = it.image,
                                    title = it.title,
                                    episodeNumber = it.episodes.toString(),
                                    episodeId = it.duration,
                                    animeId = it.id,
                                    textColor = Color.White,
                                    onClicked = {
                                        navController.navigate(
                                            route = Screen.DetailScreen.passAnimeId(
                                                it.id,
                                                it.japaneseTitle
                                            )
                                        )
                                    }
                                )
                            }
                        })
                    }
                }
            }
        }

    })

}


fun LazyListScope.AnimeCards(results: List<Zoro_Result>, navController: NavHostController) {
    items(results.size) {
        AnimeCard(
            modifier = Modifier.padding(2.dp),
            imageUrl = results[it].image,
            title = results[it].title,
            episodeNumber = results[it].sub.toString(),
            episodeId = results[it].duration,
            animeId = results[it].id,
            textColor = Color.White,
            onClicked = {
                navController.navigate(
                    route = Screen.DetailScreen.passAnimeId(
                        results[it].id,
                        results[it].japaneseTitle
                    )
                )
            }
        )
    }
}



