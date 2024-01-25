package com.blez.anime_player_compose.common.util

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home_screen")
    data object DetailScreen : Screen("detail_screen/{animeId}") {
        fun passAnimeId(animeId: String): String {
            return "detail_screen/${animeId}"
        }

    }
}