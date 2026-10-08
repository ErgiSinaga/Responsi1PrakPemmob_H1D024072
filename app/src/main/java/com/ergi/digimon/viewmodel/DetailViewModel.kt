package com.ergi.digimon.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ergi.digimon.data.model.Digimon
import com.ergi.digimon.data.repository.DigimonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val repository = DigimonRepository()
    private val digimonId: Int = checkNotNull(savedStateHandle["id"])

    private val _uiState = MutableStateFlow<UiState<Digimon>>(UiState.Loading)
    val uiState: StateFlow<UiState<Digimon>> = _uiState.asStateFlow()

    init {
        loadDetail()
    }

    fun loadDetail() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getDigimonDetail(digimonId)
                .onSuccess { _uiState.value = UiState.Success(it) }
                .onFailure { _uiState.value = UiState.Error(it.message ?: "Gagal memuat detail") }
        }
    }
}
