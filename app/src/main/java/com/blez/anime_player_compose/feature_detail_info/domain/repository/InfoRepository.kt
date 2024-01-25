package com.blez.anime_player_compose.feature_detail_info.domain.repository

import com.blez.anime_player_compose.common.util.ResultState
import com.blez.anime_player_compose.feature_detail_info.domain.model.AnimeInfoModel

interface InfoRepository {

    suspend fun getAnimeInfo(animeId: String): ResultState<AnimeInfoModel>
}