package com.example.cooperativegig.presentation.customer.booking

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingTrackingScreen(
    bookingId: Long,
    onPaymentClick: () -> Unit,
    onBackHome: () -> Unit
) {
    var currentStatus by remember { mutableStateOf("SEARCHING") }

    // Simulated status progress for SIH demo
    LaunchedEffect(Unit) {
        delay(2000)
        currentStatus = "ASSIGNED"
        delay(2500)
        currentStatus = "ON_THE_WAY"
        delay(3000)
        currentStatus = "STARTED"
        delay(3000)
        currentStatus = "COMPLETED"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Booking #$bookingId") },
                navigationIcon = {
                    TextButton(onClick = onBackHome) {
                        Text("Home")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Realtime Booking Status",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = currentStatus,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Stepper timeline
            StatusStepItem(
                stepName = "Worker Discovery & Matching",
                isDone = currentStatus != "SEARCHING",
                isActive = currentStatus == "SEARCHING"
            )
            StatusStepItem(
                stepName = "Worker Assigned & Accepted",
                isDone = currentStatus in listOf("ON_THE_WAY", "STARTED", "COMPLETED"),
                isActive = currentStatus == "ASSIGNED"
            )
            StatusStepItem(
                stepName = "Worker On The Way",
                isDone = currentStatus in listOf("STARTED", "COMPLETED"),
                isActive = currentStatus == "ON_THE_WAY"
            )
            StatusStepItem(
                stepName = "Service In Progress",
                isDone = currentStatus == "COMPLETED",
                isActive = currentStatus == "STARTED"
            )
            StatusStepItem(
                stepName = "Service Completed",
                isDone = currentStatus == "COMPLETED",
                isActive = false
            )

            Spacer(modifier = Modifier.weight(1f))

            if (currentStatus == "COMPLETED") {
                Button(
                    onClick = onPaymentClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(Icons.Default.Payment, contentDescription = "Pay")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Proceed to Razorpay Test Payment")
                }
            }
        }
    }
}

@Composable
fun StatusStepItem(
    stepName: String,
    isDone: Boolean,
    isActive: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isDone) {
            Icon(Icons.Default.CheckCircle, contentDescription = "Done", tint = Color(0xFF2E7D32), modifier = Modifier.size(24.dp))
        } else if (isActive) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
        } else {
            Icon(Icons.Default.HourglassTop, contentDescription = "Pending", tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = stepName,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (isActive || isDone) FontWeight.Bold else FontWeight.Normal,
            color = if (isDone) Color(0xFF2E7D32) else if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
        )
    }
}