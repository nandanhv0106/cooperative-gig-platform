package com.example.cooperativegig.presentation.customer.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cooperativegig.data.model.Service
import com.example.cooperativegig.data.repository.ServiceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val services: List<Service>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

class HomeViewModel(
    private val repository: ServiceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        fetchServices()
    }

    fun fetchServices() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val services = repository.getServices()
                _uiState.value = HomeUiState.Success(services)
            } catch (e: Exception) {
                _uiState.value = HomeUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}

class HomeViewModelFactory(
    private val repository: ServiceRepository = ServiceRepository()
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}