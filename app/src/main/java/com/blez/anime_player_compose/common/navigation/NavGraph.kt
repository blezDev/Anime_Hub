package com.blez.anime_player_compose.common.navigation

import android.view.Window
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.blez.anime_player_compose.common.util.Screen
import com.blez.anime_player_compose.feature_dashboard.presentation.HomeScreen
import com.blez.anime_player_compose.feature_detail_info.presentation.DetailScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String,
    window: Window
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController, window = window)
        }
        composable(route = Screen.DetailScreen.route, arguments = listOf(navArgument("animeId") {
            type = NavType.StringType
        }, navArgument("otherName") {
            type = NavType.StringType
            defaultValue = ""
        })) {
            DetailScreen(
                animeId = it.arguments?.getString("animeId").toString(),
                otherName = it.arguments?.getString("otherName").toString(),
                navController = navController,
                window = window
            )
        }
    }
}