package com.ergi.digimon.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ergi.digimon.data.model.DigimonSummary
import com.ergi.digimon.data.repository.DigimonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = DigimonRepository()

    private val _uiState = MutableStateFlow<UiState<List<DigimonSummary>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<DigimonSummary>>> = _uiState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    init {
        loadDigimon()
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun loadDigimon() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getDigimonList()
                .onSuccess { _uiState.value = UiState.Success(it) }
                .onFailure { _uiState.value = UiState.Error(it.message ?: "Gagal memuat data") }
        }
    }
}
