package com.blez.anime_player_compose.feature_dashboard.domain.use_cases

import com.blez.anime_player_compose.common.util.InvalidAPIException
import com.blez.anime_player_compose.core.entries.ListEntity
import com.blez.anime_player_compose.feature_dashboard.domain.repository.DashboardRepository
import kotlin.jvm.Throws

class InsertAnimeUseCase(val repository: DashboardRepository) {

    suspend operator fun invoke(data: ListEntity) {
        @Throws(InvalidAPIException::class)
        if (data.animeId.isNotEmpty()) {
            repository.insertAnimeList(data)
        }
        else {
            throw InvalidAPIException("Recent Release page number is invalid")
        }
    }
}