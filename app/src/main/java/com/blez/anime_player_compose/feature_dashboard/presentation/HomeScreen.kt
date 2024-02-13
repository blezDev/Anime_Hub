package com.blez.anime_player_compose.feature_dashboard.presentation

import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.blez.anime_player_compose.R
import com.blez.anime_player_compose.common.util.CredManager
import com.blez.anime_player_compose.common.util.Screen
import com.blez.anime_player_compose.feature_dashboard.presentation.component.AnimeCard
import com.blez.anime_player_compose.feature_dashboard.presentation.component.InfoButton
import com.blez.anime_player_compose.feature_dashboard.presentation.component.ListButton
import com.blez.anime_player_compose.feature_dashboard.presentation.component.PlayButton


@Composable
fun HomeScreen(
    backgroundColor: Color = Color(24, 18, 43),
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
    navController: NavHostController,
    window: Window,
    drawerState: DrawerState
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
    val credManager = CredManager(context)


    DisposableEffect(Unit) {
        dashboardViewModel.fetchRecentRelease()
        dashboardViewModel.fetchTopAiring()
        dashboardViewModel.fetchPopular()
        dashboardViewModel.fetchMovies()
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
                                iterations = LottieConstants.IterateForever

                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(text = "Loading..")
                        }

                    }
                }

                is DashboardViewModel.AiringUIEvent.Success -> {
                    val data = (trendingState as DashboardViewModel.AiringUIEvent.Success).data
                    val trend by remember {
                        mutableStateOf(data.results.random())
                    }
                    Box(modifier = Modifier.fillMaxSize()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(0.3f)
                                    .align(Alignment.Center)

                            ) {
                                SubcomposeAsyncImage(
                                    model = trend.image,
                                    contentDescription = "Latest Image",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .wrapContentWidth()
                                        .height(500.dp)
                                        .align(Alignment.Center)
                                        .clickable {
                                            navController.navigate(
                                                Screen.DetailScreen.passAnimeId(
                                                    trend.id,
                                                    trend.title
                                                )
                                            )
                                        },
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
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 10.dp)
                                            .shadow(10.dp),
                                        overflow = TextOverflow.Clip
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
                                            modifier = Modifier
                                                .clip(
                                                    RoundedCornerShape(50)
                                                )
                                                .shadow(10.dp)
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
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.5f)
                                .padding(top = 60.dp, end = 25.dp),
                            contentAlignment = Alignment.TopEnd

                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Button",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(55.dp)
                                    .shadow(10.dp)
                                    .clickable {
                                        navController.navigate(Screen.SearchScreen.route)
                                    }

                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.5f)
                                .padding(top = 60.dp, start = 15.dp),
                            contentAlignment = Alignment.TopStart

                        ) {
                            SubcomposeAsyncImage(
                                model = credManager.getProfilePic()
                                    ?: "https://img.freepik.com/free-vector/illustration-businessman_53876-5856.jpg?w=1060&t=st=1706636886~exp=1706637486~hmac=cd845268ac16219ea6e0c908a80c08b399419c0233e51a6eb97022e7ef0167d3",
                                contentDescription = "Latest Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(55.dp)
                                    .clip(CircleShape)
                                    .shadow(10.dp)
                                    .clickable {

                                    },
                                loading = { CircularProgressIndicator() },
                                error = {
                                    Image(
                                        painter = painterResource(id = R.drawable.error_png),
                                        contentDescription = "Error in Image Loading"
                                    )
                                },
                            )
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
                                    episodeNumber = it.episodeNumber.toString(),
                                    episodeId = it.episodeId,
                                    animeId = it.id,
                                    textColor = Color.White,
                                    onClicked = {
                                        navController.navigate(
                                            route = Screen.DetailScreen.passAnimeId(
                                                it.id,
                                                it.title
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
                text = stringResource(R.string.popular_anime),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
        item {
            when (recentData) {
                is DashboardViewModel.PopularUIEvent.Failure -> {

                }

                DashboardViewModel.PopularUIEvent.Loading -> {

                }

                is DashboardViewModel.PopularUIEvent.Success -> {
                    val result = (recentData as DashboardViewModel.PopularUIEvent.Success)

                    Column(modifier = Modifier.fillMaxWidth()) {
                        LazyRow(content = {
                            result.data?.let {
                                items(it.results) { it ->
                                    AnimeCard(
                                        modifier = Modifier.padding(5.dp),
                                        imageUrl = it.image,
                                        title = it.title,
                                        episodeNumber = it.releaseDate.toString(),
                                        episodeId = it.releaseDate,
                                        animeId = it.id,
                                        textColor = Color.White,
                                        onClicked = {
                                            navController.navigate(
                                                route = Screen.DetailScreen.passAnimeId(
                                                    it.id,
                                                    it.title
                                                )
                                            )
                                        }
                                    )
                                }
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
            when (trendingState) {
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
                                    episodeNumber = "",
                                    episodeId = it.episodeId,
                                    animeId = it.id,
                                    textColor = Color.White,
                                    onClicked = {
                                        navController.navigate(
                                            route = Screen.DetailScreen.passAnimeId(
                                                it.id,
                                                it.title
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
                text = stringResource(R.string.movies),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
        item {
            when (completedAnimeData) {
                is DashboardViewModel.MovieAnimeUIEvent.Failure -> {}
                DashboardViewModel.MovieAnimeUIEvent.Loading -> {}
                is DashboardViewModel.MovieAnimeUIEvent.Success -> {
                    val result =
                        (completedAnimeData as DashboardViewModel.MovieAnimeUIEvent.Success)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp)
                    ) {
                        LazyRow(content = {
                            result.data?.let {
                                items(it.results) { it ->
                                    AnimeCard(
                                        modifier = Modifier.padding(5.dp),
                                        imageUrl = it.image,
                                        title = it.title,
                                        episodeNumber = it.releaseDate.toString(),
                                        episodeId = it.releaseDate,
                                        animeId = it.id,
                                        textColor = Color.White,
                                        onClicked = {
                                            navController.navigate(
                                                route = Screen.DetailScreen.passAnimeId(
                                                    it.id,
                                                    it.title
                                                )
                                            )
                                        }
                                    )
                                }
                            }
                        })
                    }
                }
            }
        }

    })

}


/*fun LazyListScope.AnimeCards(results: List<GogoAnime_Result>, navController: NavHostController) {
    items(results.size) {
        AnimeCard(
            modifier = Modifier.padding(2.dp),
            imageUrl = results[it].image,
            title = results[it].title,
            episodeNumber = results[it].episodeNumber.toString(),
            episodeId = results[it].episodeId,
            animeId = results[it].id,
            textColor = Color.White,
            onClicked = {
                navController.navigate(
                    route = Screen.DetailScreen.passAnimeId(
                        results[it].id,
                        results[it].title
                    )
                )
            }
        )
    }
}*/



