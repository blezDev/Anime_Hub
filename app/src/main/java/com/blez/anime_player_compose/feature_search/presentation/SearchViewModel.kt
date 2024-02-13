package com.blez.anime_player_compose.feature_search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blez.anime_player_compose.common.util.ResultState
import com.blez.anime_player_compose.feature_search.domain.model.SearchDetail
import com.blez.anime_player_compose.feature_search.domain.use_cases.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(val searchUseCase: SearchUseCase) : ViewModel() {

    sealed class SearchUIEvent {
        data object Idle : SearchUIEvent()
        data object Loading : SearchUIEvent()
        data class Success(val data: SearchDetail) : SearchUIEvent()
        data class Failure(val message: String) : SearchUIEvent()
    }

    sealed class UIShift{
        data object TopAiring : UIShift()
        data object SearchQuery : UIShift()
    }
    private val _uiState = MutableStateFlow<UIShift>(UIShift.TopAiring)
    val uiState : StateFlow<UIShift>
        get() = _uiState

    fun shiftUI(){
        viewModelScope.launch {
            if (_uiState.value == UIShift.TopAiring){
                _uiState.emit(UIShift.SearchQuery)
            }
        }
    }



    private val _fetchSearch = MutableStateFlow<SearchUIEvent>(SearchUIEvent.Idle)
    val fetchSearch: StateFlow<SearchUIEvent>
        get() = _fetchSearch

    fun fetchAnime(query: String) {

        viewModelScope.launch {
            _fetchSearch.emit(SearchUIEvent.Idle)
            when (val result = searchUseCase(query)) {
                is ResultState.Error -> {
                    _fetchSearch.emit(SearchUIEvent.Failure(result.message.toString()))
                }

                is ResultState.Loading -> {
                    _fetchSearch.emit(SearchUIEvent.Loading)
                }

                is ResultState.Success -> {
                    if (result.data != null)
                        _fetchSearch.emit(SearchUIEvent.Success(data = result.data))
                    else
                        _fetchSearch.emit(SearchUIEvent.Failure(result.message.toString()))
                }
            }
        }
    }


}