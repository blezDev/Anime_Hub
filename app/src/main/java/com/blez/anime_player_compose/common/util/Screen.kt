package com.blez.anime_player_compose.common.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.DownloadForOffline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.blez.anime_player_compose.feature_detail_info.domain.model.Gogoanime_Episode

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home_screen")

    data object LoginScreen : Screen("login_screen")
    data object DetailScreen : Screen("detail_screen/{animeId}&&{otherName}") {
        fun passAnimeId(animeId: String,otherName : String): String {
            return "detail_screen/${animeId}&&${otherName}"
        }

    }
    data object VideoScreen : Screen("video_screen/{episode_id}&&{title}&&{episodeNumber}&&{animeId}"){
        fun passInfo(
            episodeId: String,
            title: String,
            episodeNumber: Int,
            animeId: String
        ): String {
            return "video_screen/${episodeId}&&${title}&&${episodeNumber}&&${animeId}"
        }
    }


    data object SearchScreen : Screen("search_screen")
}


sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String,var active : Boolean) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home",true)
    object List : BottomNavItem("list", Icons.Default.CollectionsBookmark, "List",false)
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile",false)
}