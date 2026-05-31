package com.rgi.amhisevikari.presentation.screens.lyrics

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rgi.amhisevikari.domain.model.Bhajan
import com.rgi.amhisevikari.domain.repository.BhajanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LyricsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: BhajanRepository
) : ViewModel() {

    private val bhajanId: String = checkNotNull(savedStateHandle["bhajanId"])

    val uiState: StateFlow<LyricsUiState> = repository.getAllBhajans().map { bhajans ->
        val bhajan = bhajans.find { it.id == bhajanId }
        if (bhajan != null) {
            LyricsUiState.Success(bhajan)
        } else {
            LyricsUiState.Error("Bhajan not found")
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = LyricsUiState.Loading
    )
    
    // Font settings will be backed by DataStore
}

sealed class LyricsUiState {
    object Loading : LyricsUiState()
    data class Success(val bhajan: Bhajan) : LyricsUiState()
    data class Error(val message: String) : LyricsUiState()
}
