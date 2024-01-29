package com.blez.anime_player_compose.feature_dashboard.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blez.anime_player_compose.common.util.ResultState
import com.blez.anime_player_compose.feature_dashboard.domain.model.Recent_Release_Model
import com.blez.anime_player_compose.feature_dashboard.domain.model.Result
import com.blez.anime_player_compose.feature_dashboard.domain.model.Top_Airing
import com.blez.anime_player_compose.feature_dashboard.domain.model.ZoroModel
import com.blez.anime_player_compose.feature_dashboard.domain.use_cases.DashboardUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor(val dashboardUseCases: DashboardUseCases) :
    ViewModel() {

    sealed class UIEvent {
        data object Loading : UIEvent()
        data class Success(val data: ZoroModel) : UIEvent()
        data class Failure(val message: String) : UIEvent()

    }

    sealed class AiringUIEvent {
        data object Loading : AiringUIEvent()
        data class Success(val data: Top_Airing) : AiringUIEvent()
        data class Failure(val message: String) : AiringUIEvent()
    }

    sealed class RecentAddedUIEvent {
        data object Loading : RecentAddedUIEvent()
        data class Success(val data: ZoroModel) : RecentAddedUIEvent()
        data class Failure(val message: String) : RecentAddedUIEvent()
    }

    sealed class CompletedAnimeUIEvent {
        data object Loading : CompletedAnimeUIEvent()
        data class Success(val data: ZoroModel) : CompletedAnimeUIEvent()
        data class Failure(val message: String) : CompletedAnimeUIEvent()

    }

    private val _fetchState = MutableStateFlow<UIEvent>(UIEvent.Loading)
    val fetchState: StateFlow<UIEvent>
        get() = _fetchState

    private val _topAiringState = MutableStateFlow<AiringUIEvent>(AiringUIEvent.Loading)
    val topAiringState: StateFlow<AiringUIEvent>
        get() = _topAiringState


    private val _fetchRecentAdded = MutableStateFlow<RecentAddedUIEvent>(RecentAddedUIEvent.Loading)
    val fetchRecentAdded: StateFlow<RecentAddedUIEvent>
        get() = _fetchRecentAdded

    private val _fetchCompletedAnime =
        MutableStateFlow<CompletedAnimeUIEvent>(CompletedAnimeUIEvent.Loading)
    val fetchCompletedAnime: StateFlow<CompletedAnimeUIEvent>
        get() = _fetchCompletedAnime

    fun fetchTopAiring(page: Int = 1) {
        viewModelScope.launch {
            when (val result = dashboardUseCases.topAiringUseCase(page)) {
                is ResultState.Error -> {
                    _topAiringState.emit(AiringUIEvent.Failure(result.message.toString()))
                }

                is ResultState.Loading -> {
                    _topAiringState.emit(AiringUIEvent.Loading)
                }

                is ResultState.Success -> {
                    if (result.data != null)
                        _topAiringState.emit(AiringUIEvent.Success(result.data))
                    else
                        _topAiringState.emit(AiringUIEvent.Failure(result.message.toString()))
                }
            }
        }
    }


    fun fetchRecentRelease(page: Int = 1) {
        viewModelScope.launch {
            when (val result = dashboardUseCases.recentReleaseUseCase(page)) {
                is ResultState.Error -> {
                    _fetchState.emit(UIEvent.Failure(result.message.toString()))
                }

                is ResultState.Loading -> {
                    _fetchState.emit(UIEvent.Loading)
                }

                is ResultState.Success -> {
                    if (result.data != null) {
                        _fetchState.emit(UIEvent.Success(result.data))

                    } else
                        _fetchState.emit(UIEvent.Failure(result.message.toString()))
                }
            }
        }
    }


    fun fetchRecentAdded(page: Int = 1) {
        viewModelScope.launch {
            when (val result = dashboardUseCases.recentAddedUseCase(page)) {
                is ResultState.Error -> {
                    _fetchRecentAdded.emit(RecentAddedUIEvent.Failure(result.message.toString()))
                }

                is ResultState.Loading -> {
                    _fetchRecentAdded.emit(RecentAddedUIEvent.Loading)
                }

                is ResultState.Success -> {

                    if (result.data != null)
                        _fetchRecentAdded.emit(RecentAddedUIEvent.Success(result.data))
                    else
                        _fetchRecentAdded.emit(RecentAddedUIEvent.Failure(result.message.toString()))
                }
            }
        }
    }


    fun fetchCompletedAnime(page: Int = 1) {
        viewModelScope.launch {
            when (val result = dashboardUseCases.completedAnimeUseCase(page)) {
                is ResultState.Error -> {
                    _fetchCompletedAnime.emit(CompletedAnimeUIEvent.Failure(result.message.toString()))
                }

                is ResultState.Loading -> {
                    _fetchCompletedAnime.emit(CompletedAnimeUIEvent.Loading)
                }

                is ResultState.Success -> {
                    if (result.data != null)
                        _fetchCompletedAnime.emit(CompletedAnimeUIEvent.Success(result.data))
                    else
                        _fetchCompletedAnime.emit(CompletedAnimeUIEvent.Failure(result.message.toString()))
                }
            }
        }
    }
}