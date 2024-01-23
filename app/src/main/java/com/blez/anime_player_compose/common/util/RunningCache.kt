package com.blez.anime_player_compose.common.util

import com.blez.anime_player_compose.feature_dashboard.domain.model.Recent_Release_Model
import com.blez.anime_player_compose.feature_dashboard.domain.model.Top_Airing
import com.blez.anime_player_compose.feature_detail_info.domain.model.AnimeInfoModel

object RunningCache {
    val recentReleaseCache = mutableMapOf<Int, Recent_Release_Model>()
    val topAiringCache = mutableMapOf<Int, Top_Airing>()
    val animeDetails = mutableMapOf<String, AnimeInfoModel>()
}