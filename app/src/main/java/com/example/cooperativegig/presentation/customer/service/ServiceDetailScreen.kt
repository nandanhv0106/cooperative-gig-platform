package com.example.cooperativegig.presentation.customer.service

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cooperativegig.presentation.components.DisplayWorker
import com.example.cooperativegig.presentation.components.WorkerCard
import com.example.cooperativegig.presentation.components.getIconForCategory

val sampleTasksForServices = mapOf(
    "Plumbing" to listOf("Tap Repair & Replacement", "Pipe Leakage Fix", "Sink & Drain Unclogging", "Bathroom & Toilet Plumbing", "Overhead Tank Inspection"),
    "Electrical" to listOf("Short Circuit Diagnostic", "Switchboard & Wiring Repair", "Fan & Light Fitting", "MCB & Fuse Replacement", "Appliance Power Check"),
    "Carpentry" to listOf("Door Lock & Hinge Repair", "Furniture Assembly", "Cabinet & Drawer Repair", "Wood Polishing & Touchup", "Custom Wood Fitting"),
    "Cleaning" to listOf("Full House Deep Cleaning", "Kitchen & Bathroom Sanitization", "Sofa & Carpet Cleaning", "Water Tank Cleaning", "Post-Paint Cleanup")
)

val sampleAvailableWorkers = listOf(
    DisplayWorker("w1", "Ramesh Kumar", "Certified Plumber", 4.9, 8, 0.8),
    DisplayWorker("w2", "Suresh Verma", "Licensed Electrician", 4.8, 6, 1.2),
    DisplayWorker("w3", "Anita Devi", "Cleaning Specialist", 4.9, 5, 1.5),
    DisplayWorker("w4", "Vikram Singh", "Master Carpenter", 4.7, 10, 2.1)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceDetailScreen(
    serviceId: Long,
    serviceName: String = "Plumbing Services",
    basePrice: Double = 299.0,
    onBookNow: (Long) -> Unit,
    onWorkerClick: (String) -> Unit,
    onBack: () -> Unit
) {
    val tasks = sampleTasksForServices[serviceName] ?: sampleTasksForServices["Plumbing"]!!

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(serviceName, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Starting From", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(text = "₹${basePrice.toInt()}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    }

                    Button(
                        onClick = { onBookNow(serviceId) },
                        modifier = Modifier
                            .height(48.dp)
                            .widthIn(min = 180.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Book Service", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // Service Header Banner Card
            item {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = getIconForCategory(serviceName),
                                contentDescription = serviceName,
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(36.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(text = serviceName, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, contentDescription = "Rating", tint = Color(0xFFD97706), modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "4.9 (120+ Cooperative Gigs)", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                            }
                            Text(text = "⏱ Estimated Arrival: 30-45 Mins", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }

            // Trust & Pricing Guarantee Card
            item {
                OutlinedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Verified, contentDescription = "Cooperative Guarantee", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = "Strata Cooperative Pricing Guarantee", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text(text = "Fixed base inspection rate. Standard rates set directly by the Labour Federation with zero hidden commissions.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }

            // Common Tasks Included Checklist
            item {
                Text(text = "Common Services Covered", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        tasks.forEach { task ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(text = task, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }
            }

            // Verified Nearby Specialists
            item {
                Text(text = "Available Verified Specialists", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(sampleAvailableWorkers) { worker ->
                        WorkerCard(
                            worker = worker,
                            onBookClick = { onWorkerClick(worker.id) }
                        )
                    }
                }
            }
        }
    }
}