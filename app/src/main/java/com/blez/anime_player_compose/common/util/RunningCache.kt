package com.blez.anime_player_compose.common.util

import com.blez.anime_player_compose.feature_dashboard.domain.model.Recent_Release_Model
import com.blez.anime_player_compose.feature_dashboard.domain.model.Top_Airing
import com.blez.anime_player_compose.feature_dashboard.domain.model.ZoroModel
import com.blez.anime_player_compose.feature_detail_info.domain.model.AnimeInfoModel

object RunningCache {
    val recentReleaseCache = mutableMapOf<Int, ZoroModel>()
    val recentAddedCache = mutableMapOf<Int,ZoroModel>()
    val completedAnimeCache = mutableMapOf<Int,ZoroModel>()
    val topAiringCache = mutableMapOf<Int, Top_Airing>()
    val animeDetails = mutableMapOf<String, AnimeInfoModel>()
}