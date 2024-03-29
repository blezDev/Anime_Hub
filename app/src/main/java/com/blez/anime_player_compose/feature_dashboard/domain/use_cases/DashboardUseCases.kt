package com.blez.anime_player_compose.feature_dashboard.domain.use_cases

import com.blez.anime_player_compose.feature_detail_info.domain.use_cases.InfoDetails
import com.blez.anime_player_compose.feature_video.domain.use_cases.VideoLinksUseCases

data class DashboardUseCases(
    val recentReleaseUseCase: RecentReleaseUseCase,
    val topAiringUseCase: TopAiringUseCase,
    val infoDetails: InfoDetails,
    val moviesAddedUseCase: MoviesAddedUseCase,
    val popularAnimeUseCase: PopularAnimeUseCase,
    val videoLinksUseCases: VideoLinksUseCases,
    val getAnimeListUseCases : GetAnimeListUseCase,
    val insertAnimeUseCase: InsertAnimeUseCase
)
