package com.example.cooperativegig.presentation.worker.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cooperativegig.presentation.auth.AuthViewModel
import com.example.cooperativegig.presentation.worker.WorkerUiState
import com.example.cooperativegig.presentation.worker.WorkerViewModel

@Composable
fun WorkerProfileScreen(
    authViewModel: AuthViewModel,
    workerViewModel: WorkerViewModel,
    onLogout: () -> Unit
) {
    val uiState by workerViewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("Worker Profile & Welfare", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(24.dp))

        when (val state = uiState) {
            is WorkerUiState.Success -> {
                // Verification Badge Card
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (state.worker.verificationStatus == "VERIFIED") Icons.Default.CheckCircle else Icons.Default.Info,
                            contentDescription = "Status",
                            tint = if (state.worker.verificationStatus == "VERIFIED") Color(0xFF2E7D32) else Color(0xFFED6C02)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = "Cooperative Verification", style = MaterialTheme.typography.labelMedium)
                            Text(
                                text = state.worker.verificationStatus,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (state.worker.verificationStatus == "VERIFIED") Color(0xFF2E7D32) else Color(0xFFED6C02)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Welfare & Insurance Summary Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Cooperative Welfare & Insurance", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Active Policy: Gig Worker Health & Accident Scheme", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Coverage: ₹2,00,000 Medical Insurance", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSecondaryContainer)
                    }
                }
            }
            else -> {}
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                authViewModel.logout()
                onLogout()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Logout")
        }
    }
}