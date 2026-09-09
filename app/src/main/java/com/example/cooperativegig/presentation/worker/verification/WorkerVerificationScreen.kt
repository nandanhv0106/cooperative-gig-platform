package com.example.cooperativegig.presentation.worker.verification

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerVerificationScreen(
    onBack: () -> Unit
) {
    var idDocUploaded by remember { mutableStateOf(false) }
    var skillCertUploaded by remember { mutableStateOf(false) }
    var expDocUploaded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Worker Verification") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = "Cooperative Document Submission",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Upload required verification documents for cooperative review.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            DocumentUploadCard(
                title = "Government Identity Document",
                subtitle = "Aadhaar / Voter ID / Driving License",
                isUploaded = idDocUploaded,
                onUpload = { idDocUploaded = true }
            )

            Spacer(modifier = Modifier.height(12.dp))

            DocumentUploadCard(
                title = "Skill / Trade Certificate",
                subtitle = "ITI / Vocational / Cooperative Certificate",
                isUploaded = skillCertUploaded,
                onUpload = { skillCertUploaded = true }
            )

            Spacer(modifier = Modifier.height(12.dp))

            DocumentUploadCard(
                title = "Experience / Reference Letter",
                subtitle = "Letter from previous cooperative / employer",
                isUploaded = expDocUploaded,
                onUpload = { expDocUploaded = true }
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth(),
                enabled = idDocUploaded && skillCertUploaded
            ) {
                Text(if (idDocUploaded && skillCertUploaded) "Submit for Verification" else "Upload Required Docs")
            }
        }
    }
}

@Composable
fun DocumentUploadCard(
    title: String,
    subtitle: String,
    isUploaded: Boolean,
    onUpload: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
            }
            if (isUploaded) {
                FilterChip(
                    selected = true,
                    onClick = { },
                    label = { Text("Uploaded") }
                )
            } else {
                IconButton(onClick = onUpload) {
                    Icon(Icons.Default.UploadFile, contentDescription = "Upload")
                }
            }
        }
    }
}