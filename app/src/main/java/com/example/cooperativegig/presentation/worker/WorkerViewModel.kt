package com.example.cooperativegig.presentation.worker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cooperativegig.data.model.Worker
import com.example.cooperativegig.data.repository.WorkerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class WorkerUiState {
    object Loading : WorkerUiState()
    data class Success(
        val worker: Worker,
        val isAvailable: Boolean
    ) : WorkerUiState()
    data class Error(val message: String) : WorkerUiState()
}

class WorkerViewModel(
    private val repository: WorkerRepository = WorkerRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<WorkerUiState>(WorkerUiState.Loading)
    val uiState: StateFlow<WorkerUiState> = _uiState.asStateFlow()

    init {
        loadWorkerData()
    }

    fun loadWorkerData() {
        viewModelScope.launch {
            _uiState.value = WorkerUiState.Loading
            val worker = repository.getWorkerProfile()
            if (worker != null) {
                _uiState.value = WorkerUiState.Success(
                    worker = worker,
                    isAvailable = worker.isAvailable
                )
            } else {
                // Return default state if worker table record doesn't exist yet
                val defaultWorker = Worker(id = "worker-1", verificationStatus = "VERIFIED", isAvailable = true, totalEarnings = 1250.0, rating = 4.8)
                _uiState.value = WorkerUiState.Success(
                    worker = defaultWorker,
                    isAvailable = true
                )
            }
        }
    }

    fun toggleAvailability(newStatus: Boolean) {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState is WorkerUiState.Success) {
                _uiState.value = currentState.copy(isAvailable = newStatus)
                repository.updateAvailability(newStatus)
            }
        }
    }
}

class WorkerViewModelFactory(
    private val repository: WorkerRepository = WorkerRepository()
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WorkerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}