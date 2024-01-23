package com.blez.anime_player_compose.feature_dashboard.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blez.anime_player_compose.common.util.ResultState
import com.blez.anime_player_compose.feature_dashboard.domain.model.Recent_Release_Model
import com.blez.anime_player_compose.feature_dashboard.domain.model.Result
import com.blez.anime_player_compose.feature_dashboard.domain.model.Top_Airing
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
        data class Success(val data: Recent_Release_Model) : UIEvent()
        data class Failure(val message: String) : UIEvent()

    }

    sealed class AiringUIEvent {
        data object Loading : AiringUIEvent()
        data class Success(val data: Top_Airing) : AiringUIEvent()
        data class Failure(val message: String) : AiringUIEvent()
    }


    private val _fetchState = MutableStateFlow<UIEvent>(UIEvent.Loading)
    val fetchState: StateFlow<UIEvent>
        get() = _fetchState

    private val _topAiringState = MutableStateFlow<AiringUIEvent>(AiringUIEvent.Loading)
    val topAiringState: StateFlow<AiringUIEvent>
        get() = _topAiringState

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

}