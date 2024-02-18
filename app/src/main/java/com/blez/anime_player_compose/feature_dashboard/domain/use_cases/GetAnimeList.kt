package com.blez.anime_player_compose.feature_dashboard.domain.use_cases

import com.blez.anime_player_compose.core.entries.ListEntity
import com.blez.anime_player_compose.feature_dashboard.domain.repository.DashboardRepository
import kotlinx.coroutines.flow.Flow

class GetAnimeListUseCase(val repository: DashboardRepository) {
     operator fun invoke(): Flow<List<ListEntity>> {
        return repository.getAnimeList()
    }
}