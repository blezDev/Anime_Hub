package com.blez.anime_player_compose.feature_dashboard.domain.use_cases

import com.blez.anime_player_compose.feature_detail_info.domain.use_cases.InfoDetails

data class DashboardUseCases(val recentReleaseUseCase : RecentReleaseUseCase,val topAiringUseCase: TopAiringUseCase,val infoDetails: InfoDetails)
