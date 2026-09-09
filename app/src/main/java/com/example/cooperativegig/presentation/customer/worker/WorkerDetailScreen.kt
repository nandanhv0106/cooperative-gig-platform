package com.example.cooperativegig.presentation.customer.worker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class WorkerReview(
    val id: String,
    val customerName: String,
    val rating: Int,
    val comment: String,
    val date: String
)

val sampleWorkerReviews = listOf(
    WorkerReview("r1", "Ananya Sharma", 5, "Ramesh fixed the leaking pipe under our kitchen sink very quickly. Punctual and polite!", "Yesterday"),
    WorkerReview("r2", "Rajesh Gupta", 5, "Very professional cooperative worker. Fair pricing and cleaned up after finishing.", "3 days ago"),
    WorkerReview("r3", "Priya Nair", 4, "Great plumbing job. Knew exactly what parts were needed.", "1 week ago")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkerDetailScreen(
    workerId: String,
    workerName: String = "Ramesh Kumar",
    primarySkill: String = "Master Plumber",
    rating: Double = 4.9,
    jobsCompleted: Int = 142,
    experienceYears: Int = 8,
    cooperativeName: String = "South Delhi Labor Cooperative Federation #12",
    onBookWorker: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Worker Profile", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
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
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Phone, contentDescription = "Contact")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Contact")
                    }

                    Button(
                        onClick = onBookWorker,
                        modifier = Modifier
                            .weight(2f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Book Worker Now", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
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
            // Profile Card Header
            item {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = workerName,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(48.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(text = workerName, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text(text = primarySkill, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)

                        Spacer(modifier = Modifier.height(8.dp))

                        // Verified Member Badge
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Verified, contentDescription = "Verified", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Verified Cooperative Member",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Key Stats Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            StatColumn(title = "Rating", value = "⭐ $rating")
                            StatColumn(title = "Gigs Done", value = "$jobsCompleted+")
                            StatColumn(title = "Experience", value = "$experienceYears Yrs")
                        }
                    }
                }
            }

            // Cooperative Affiliation Card
            item {
                OutlinedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Cooperative Affiliation", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = cooperativeName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                        Text(text = "Identity, trade certification and background verification completed by cooperative administrators.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }

            // Certified Skills
            item {
                Text(text = "Skills & Certifications", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SuggestionChip(onClick = { }, label = { Text("ITI Certified") })
                    SuggestionChip(onClick = { }, label = { Text("Pipe Fitting") })
                    SuggestionChip(onClick = { }, label = { Text("Drainage Repair") })
                }
            }

            // Customer Reviews
            item {
                Text(text = "Customer Reviews ($rating ⭐)", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }

            items(sampleWorkerReviews) { review ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = review.customerName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleSmall)
                            Text(text = review.date, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(text = "⭐ ".repeat(review.rating), style = MaterialTheme.typography.labelSmall)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = review.comment, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}

@Composable
private fun StatColumn(
    title: String,
    value: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text(text = title, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}