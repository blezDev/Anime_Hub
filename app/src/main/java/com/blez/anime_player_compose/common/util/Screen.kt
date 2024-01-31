package com.blez.anime_player_compose.common.util

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home_screen")

    data object LoginScreen : Screen("login_screen")
    data object DetailScreen : Screen("detail_screen/{animeId}&&{otherName}") {
        fun passAnimeId(animeId: String,otherName : String): String {
            return "detail_screen/${animeId}&&${otherName}"
        }

    }
    data object VideoScreen : Screen("video_screen"){
        fun passInfo(): String {
            return "video_screen"
        }
    }
}