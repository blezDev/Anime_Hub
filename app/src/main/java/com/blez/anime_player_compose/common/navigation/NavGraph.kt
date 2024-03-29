package com.blez.anime_player_compose.common.navigation

import android.app.ActionBar
import android.view.Window
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.blez.anime_player_compose.common.util.Screen
import com.blez.anime_player_compose.feature_dashboard.presentation.HomeScreen
import com.blez.anime_player_compose.feature_detail_info.presentation.DetailScreen
import com.blez.anime_player_compose.feature_login.presentation.LoginScreen
import com.blez.anime_player_compose.feature_search.presentation.SearchScreen
import com.blez.anime_player_compose.feature_video.presentation.VideoScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String,
    window: Window,
    drawerState: DrawerState,
    actionBar: ActionBar?
) {
    NavHost(navController = navController, startDestination = startDestination) {

        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController)
        }

        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController, window = window, drawerState = drawerState)
        }

        composable(route = Screen.SearchScreen.route){
            SearchScreen(navController = navController)
        }


        composable(route = Screen.VideoScreen.route, arguments = listOf(navArgument("episode_id") {
            type = NavType.StringType
            defaultValue = "megumi-no-daigo-kyuukoku-no-orange"
        }, navArgument("title") {
            type = NavType.StringType
            defaultValue = "Undead Unluck"
        }, navArgument("episodeNumber"){
            type = NavType.IntType
            defaultValue = 1
        }, navArgument("animeId"){
            type = NavType.StringType
            defaultValue = "yubisaki-to-renren"
        })) {
            VideoScreen(
                navController = navController,
                window = window,
                actionBar = actionBar,
                episodeId = it.arguments?.getString("episode_id").toString(),
                title = it.arguments?.getString("title").toString(),
                epiNumber = it.arguments?.getInt("episodeNumber") ?: 1,
                animeId = it.arguments?.getString("animeId").toString()

            )
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