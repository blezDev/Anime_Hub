package com.blez.anime_player_compose.feature_search.presentation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.blez.anime_player_compose.R
import com.blez.anime_player_compose.common.util.Screen
import com.blez.anime_player_compose.feature_dashboard.presentation.DashboardViewModel
import com.blez.anime_player_compose.feature_search.presentation.components.SearchBar
import com.blez.anime_player_compose.feature_search.presentation.components.SearchCard


@Composable
fun SearchScreen(
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel(),
    dashboardViewModel: DashboardViewModel = hiltViewModel(),
) {

    DisposableEffect(Unit) {
        dashboardViewModel.fetchTopAiring()
        onDispose { }
    }
    val trendingState by dashboardViewModel.topAiringState.collectAsState()
    val uiShiftState by searchViewModel.uiState.collectAsState()
    val searchState by searchViewModel.fetchSearch.collectAsState()
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.video_load))

    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, top = 50.dp)
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {

            searchViewModel.fetchAnime(query = it)
            searchViewModel.shiftUI()
        }

        when (uiShiftState) {
            SearchViewModel.UIShift.SearchQuery -> {
                when (searchState) {
                    is SearchViewModel.SearchUIEvent.Failure -> {}
                    SearchViewModel.SearchUIEvent.Idle -> {}
                    SearchViewModel.SearchUIEvent.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
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

                    is SearchViewModel.SearchUIEvent.Success -> {
                        val data by rememberSaveable {
                            mutableStateOf((searchState as SearchViewModel.SearchUIEvent.Success).data.results)
                        }
                        LazyColumn(content = {
                            items(data) { anime ->
                                SearchCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 15.dp, vertical = 10.dp),
                                    imageURL = anime.image,
                                    title = anime.title,
                                    onCardClicked = {
                                        navController.navigate(
                                            Screen.DetailScreen.passAnimeId(
                                                anime.id,
                                                anime.title
                                            )
                                        )
                                    }
                                )
                            }
                        })
                    }
                }
            }


            SearchViewModel.UIShift.TopAiring -> {
                when (trendingState) {
                    is DashboardViewModel.AiringUIEvent.Failure -> {}
                    DashboardViewModel.AiringUIEvent.Loading -> {}
                    is DashboardViewModel.AiringUIEvent.Success -> {
                        val data by rememberSaveable {
                            mutableStateOf((trendingState as DashboardViewModel.AiringUIEvent.Success).data.results.shuffled())
                        }
                        Text(
                            text = "Top Searches",
                            color = Color.White,
                            modifier = Modifier.padding(10.dp),
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Normal,
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        LazyColumn(content = {
                            items(data) { anime ->
                                SearchCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 15.dp, vertical = 10.dp),
                                    imageURL = anime.image,
                                    title = anime.title,
                                    onCardClicked = {
                                        navController.navigate(
                                            Screen.DetailScreen.passAnimeId(
                                                anime.id,
                                                anime.title
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

    }

}