package com.example.cooperativegig.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cooperativegig.data.model.Profile
import com.example.cooperativegig.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Authenticated(val profile: Profile) : AuthUiState()
    object Unauthenticated : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun checkSession() {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            val profile = repository.checkSession()
            if (profile != null) {
                _uiState.value = AuthUiState.Authenticated(profile)
            } else {
                _uiState.value = AuthUiState.Unauthenticated
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                val profile = repository.signIn(email, password)
                if (profile != null) {
                    _uiState.value = AuthUiState.Authenticated(profile)
                } else {
                    _uiState.value = AuthUiState.Error("Failed to fetch profile. User might not exist.")
                }
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Login failed")
            }
        }
    }

    fun register(email: String, password: String, role: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                val success = repository.signUp(email, password, role)
                if (success) {
                    // Automatically try to fetch session or login after register
                    // if auto-confirm is not required.
                    val profile = repository.checkSession()
                    if (profile != null) {
                        _uiState.value = AuthUiState.Authenticated(profile)
                    } else {
                        _uiState.value = AuthUiState.Unauthenticated
                    }
                } else {
                    _uiState.value = AuthUiState.Error("Registration failed")
                }
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Registration failed")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            repository.signOut()
            _uiState.value = AuthUiState.Unauthenticated
        }
    }
    
    fun resetState() {
        _uiState.value = AuthUiState.Idle
    }
}

class AuthViewModelFactory(
    private val repository: AuthRepository = AuthRepository()
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}