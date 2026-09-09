package com.example.cooperativegig.presentation.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    onLogout: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Strata Federation Admin", fontWeight = FontWeight.Bold) },
                actions = {
                    TextButton(onClick = onLogout) {
                        Text("Logout", color = MaterialTheme.colorScheme.error)
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
            item {
                Text(
                    text = "Cooperative Federation Overview",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Realtime operational analytics & AI workforce forecasting",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // AI Demand Forecast Card
            item {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                            contentDescription = "AI Forecast",
                            tint = MaterialTheme.colorScheme.onTertiaryContainer,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = "AI Demand Forecast Notice",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "High Plumbing demand (+35%) predicted in Sector 4 tomorrow between 6 PM–9 PM. Allocate 3 additional verified plumbers.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.9f)
                            )
                        }
                    }
                }
            }

            // Financial & Operational Metric Cards
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ElevatedCard(modifier = Modifier.weight(1f), shape = RoundedCornerShape(16.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Active Members", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("142 Workers", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        }
                    }
                    ElevatedCard(modifier = Modifier.weight(1f), shape = RoundedCornerShape(16.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Completed Gigs", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("1,280 Gigs", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ElevatedCard(modifier = Modifier.weight(1f), shape = RoundedCornerShape(16.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Worker Earnings", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("₹3,84,000", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color(0xFF059669))
                        }
                    }
                    ElevatedCard(modifier = Modifier.weight(1f), shape = RoundedCornerShape(16.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Coop Admin Fee (10%)", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("₹38,400", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }

            // Worker Verification Queue
            item {
                Text("Pending Worker Verification Queue (2)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }

            item {
                Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Ramesh Kumar — Master Plumber", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                            Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = RoundedCornerShape(8.dp)) {
                                Text("ITI Certified", style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                            }
                        }
                        Text("Affiliation: Labor Society North #12", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = { }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(12.dp)) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Approve")
                            }
                            OutlinedButton(onClick = { }, modifier = Modifier.weight(1f), shape = RoundedCornerShape(12.dp)) {
                                Text("Reject")
                            }
                        }
                    }
                }
            }
        }
    }
}