package com.rgi.amhisevikari.presentation.screens.sublist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rgi.amhisevikari.domain.model.Bhajan
import com.rgi.amhisevikari.domain.repository.BhajanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SubListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: BhajanRepository
) : ViewModel() {

    private val categoryId: String = checkNotNull(savedStateHandle["categoryId"])

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val bhajansFlow = repository.getBhajansByCategory(categoryId)

    val uiState: StateFlow<SubListUiState> = combine(
        bhajansFlow,
        _searchQuery
    ) { bhajans, query ->
        val filtered = if (query.isBlank()) {
            bhajans
        } else {
            bhajans.filter { 
                it.title.contains(query, ignoreCase = true) ||
                it.searchToken.contains(query, ignoreCase = true)
            }
        }
        SubListUiState.Success(filtered)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SubListUiState.Loading
    )

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
}

sealed class SubListUiState {
    object Loading : SubListUiState()
    data class Success(val bhajans: List<Bhajan>) : SubListUiState()
}
