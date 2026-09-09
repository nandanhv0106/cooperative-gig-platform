package com.example.cooperativegig.presentation.customer.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingTrackingScreen(
    bookingId: Long,
    serviceName: String = "Plumbing Service",
    onPaymentClick: () -> Unit,
    onBackHome: () -> Unit
) {
    var currentStatus by remember { mutableStateOf("SEARCHING") }

    // Simulated status progress for SIH demonstration
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
                title = { Text("Booking #$bookingId", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackHome) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Home")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // Status Header Card
            item {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = if (currentStatus == "COMPLETED") MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "CURRENT STATUS",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = getStatusTitle(currentStatus),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = getStatusSubtitle(currentStatus),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Assigned Worker Card (when assigned)
            if (currentStatus != "SEARCHING") {
                item {
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Ramesh Kumar",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("Ramesh Kumar", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(Icons.Default.Verified, contentDescription = "Verified", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                                }
                                Text("Verified Master Plumber", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.size(14.dp))
                                    Text(" 4.9 (140+ Gigs)", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                                }
                            }

                            IconButton(onClick = { }) {
                                Icon(Icons.Default.Phone, contentDescription = "Call", tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }

            // Timeline Stepper Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Live Service Timeline", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)

                        StatusStepItem(
                            stepName = "Cooperative Worker Discovery & Pair",
                            isDone = currentStatus != "SEARCHING",
                            isActive = currentStatus == "SEARCHING"
                        )
                        StatusStepItem(
                            stepName = "Worker Assigned & Request Accepted",
                            isDone = currentStatus in listOf("ON_THE_WAY", "STARTED", "COMPLETED"),
                            isActive = currentStatus == "ASSIGNED"
                        )
                        StatusStepItem(
                            stepName = "Worker On The Way (Live Dispatch)",
                            isDone = currentStatus in listOf("STARTED", "COMPLETED"),
                            isActive = currentStatus == "ON_THE_WAY"
                        )
                        StatusStepItem(
                            stepName = "Service In Progress at Location",
                            isDone = currentStatus == "COMPLETED",
                            isActive = currentStatus == "STARTED"
                        )
                        StatusStepItem(
                            stepName = "Service Completed & Verification",
                            isDone = currentStatus == "COMPLETED",
                            isActive = false
                        )
                    }
                }
            }

            // Action Button when COMPLETED
            if (currentStatus == "COMPLETED") {
                item {
                    Button(
                        onClick = onPaymentClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Payment, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Proceed to Razorpay Sandbox Payment", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun StatusStepItem(
    stepName: String,
    isDone: Boolean,
    isActive: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isDone) {
            Icon(Icons.Default.CheckCircle, contentDescription = "Done", tint = Color(0xFF059669), modifier = Modifier.size(24.dp))
        } else if (isActive) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
        } else {
            Icon(Icons.Default.HourglassTop, contentDescription = "Pending", tint = MaterialTheme.colorScheme.outline, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = stepName,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isActive || isDone) FontWeight.Bold else FontWeight.Normal,
            color = if (isDone) Color(0xFF059669) else if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
        )
    }
}

private fun getStatusTitle(status: String): String {
    return when (status) {
        "SEARCHING" -> "Searching Nearby Workers"
        "ASSIGNED" -> "Worker Assigned"
        "ON_THE_WAY" -> "Worker On The Way"
        "STARTED" -> "Service In Progress"
        "COMPLETED" -> "Service Completed!"
        else -> status
    }
}

private fun getStatusSubtitle(status: String): String {
    return when (status) {
        "SEARCHING" -> "Matching with verified cooperative specialists..."
        "ASSIGNED" -> "Ramesh Kumar accepted your request."
        "ON_THE_WAY" -> "ETA ~ 15 Mins to your location."
        "STARTED" -> "Worker is currently performing the trade task."
        "COMPLETED" -> "Job completed successfully. Please settle payment."
        else -> ""
    }
}