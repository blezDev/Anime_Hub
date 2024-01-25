package com.blez.anime_player_compose.feature_detail_info.domain.use_cases

import com.blez.anime_player_compose.common.util.InvalidAPIException
import com.blez.anime_player_compose.common.util.ResultState
import com.blez.anime_player_compose.feature_detail_info.domain.model.AnimeInfoModel
import com.blez.anime_player_compose.feature_detail_info.domain.repository.InfoRepository
import kotlin.jvm.Throws

class InfoDetails(private val repository: InfoRepository) {
    suspend operator fun invoke(animeId: String): ResultState<AnimeInfoModel> {
        @Throws(InvalidAPIException::class)
        if (animeId.isNotEmpty()) {
            return repository.getAnimeInfo(animeId)
        } else {
            throw InvalidAPIException("Recent Release page number is invalid")
        }

    }
}