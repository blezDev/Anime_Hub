package com.blez.anime_player_compose.common.util

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home_screen")

    data object LoginScreen : Screen("login_screen")
    data object DetailScreen : Screen("detail_screen/{animeId}&&{otherName}") {
        fun passAnimeId(animeId: String,otherName : String): String {
            return "detail_screen/${animeId}&&${otherName}"
        }

    }
    data object VideoScreen : Screen("video_screen/{episode_id}&&{title}&&{episodeNumber}"){
        fun passInfo(episodeId : String, title : String,episodeNumber : String ): String {
            return "video_screen/${episodeId}&&${title}&&${episodeNumber}"
        }
    }
}