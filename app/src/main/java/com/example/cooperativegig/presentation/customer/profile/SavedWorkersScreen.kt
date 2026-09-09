package com.example.cooperativegig.presentation.customer.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cooperativegig.presentation.components.DisplayWorker
import com.example.cooperativegig.presentation.components.WorkerCard

val savedCooperativeWorkersList = listOf(
    DisplayWorker("w1", "Ramesh Kumar", "Master Plumber", 4.9, 8, 0.8),
    DisplayWorker("w2", "Suresh Verma", "Licensed Electrician", 4.8, 6, 1.2)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedWorkersScreen(
    onWorkerClick: (String) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Saved Cooperative Workers", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(savedCooperativeWorkersList) { worker ->
                WorkerCard(
                    worker = worker,
                    onBookClick = { onWorkerClick(worker.id) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}