package com.blez.anime_player_compose.common.util

import com.blez.anime_player_compose.feature_dashboard.domain.model.MovieModel
import com.blez.anime_player_compose.feature_dashboard.domain.model.PopularAnimeModel
import com.blez.anime_player_compose.feature_dashboard.domain.model.Recent_Release_Model
import com.blez.anime_player_compose.feature_dashboard.domain.model.Top_Airing
import com.blez.anime_player_compose.feature_dashboard.domain.model.ZoroModel
import com.blez.anime_player_compose.feature_detail_info.domain.model.AnimeInfoModel
import com.blez.anime_player_compose.feature_search.domain.model.SearchDetail
import com.blez.anime_player_compose.feature_video.domain.model.VideoModel

object RunningCache {
    val recentReleaseCache = mutableMapOf<Int, ZoroModel>()
    val popularCache = mutableMapOf<Int, PopularAnimeModel>()
    val moviesAnimeCache = mutableMapOf<Int, MovieModel>()
    val topAiringCache = mutableMapOf<Int, Top_Airing>()
    val animeDetails = mutableMapOf<String, AnimeInfoModel>()
    val videoData = mutableMapOf<String,VideoModel>()
    val searchData = mutableMapOf<String,SearchDetail>()
}