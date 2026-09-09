package com.example.cooperativegig.presentation.customer.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ServicesScreen(
    viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory())
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Cooperative Gig Platform",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp, top = 32.dp)
        )

        when (val state = uiState) {
            is HomeUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(top = 32.dp))
            }
            is HomeUiState.Success -> {
                if (state.services.isEmpty()) {
                    Text("No services available.", modifier = Modifier.padding(top = 32.dp))
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(state.services) { service ->
                            Text(
                                text = "• ${service.name}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
            is HomeUiState.Error -> {
                Text(
                    text = "Error: ${state.message}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 32.dp)
                )
                Button(
                    onClick = { viewModel.fetchServices() },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Retry")
                }
            }
        }
    }
}