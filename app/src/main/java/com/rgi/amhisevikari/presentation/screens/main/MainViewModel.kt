package com.rgi.amhisevikari.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rgi.amhisevikari.domain.usecase.CategoryWithBhajans
import com.rgi.amhisevikari.domain.usecase.GetCategoriesWithBhajansUseCase
import com.rgi.amhisevikari.domain.usecase.SearchDevataAndBhajansUseCase
import com.rgi.amhisevikari.domain.usecase.SyncDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCategoriesWithBhajans: GetCategoriesWithBhajansUseCase,
    private val searchDevataAndBhajans: SearchDevataAndBhajansUseCase,
    private val syncData: SyncDataUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val allCategories = getCategoriesWithBhajans()

    val uiState: StateFlow<MainUiState> = combine(
        allCategories,
        _searchQuery
    ) { categories, query ->
        if (categories.isEmpty()) {
            MainUiState.Empty
        } else {
            val filtered = searchDevataAndBhajans(query, categories)
            if (filtered.isEmpty() && query.isNotBlank()) {
                MainUiState.NoResults
            } else {
                MainUiState.Success(filtered)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MainUiState.Loading
    )

    init {
        viewModelScope.launch {
            syncData()
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }
}

sealed class MainUiState {
    object Loading : MainUiState()
    object Empty : MainUiState()
    object NoResults : MainUiState()
    data class Success(val categories: List<CategoryWithBhajans>) : MainUiState()
    data class Error(val message: String) : MainUiState()
}
