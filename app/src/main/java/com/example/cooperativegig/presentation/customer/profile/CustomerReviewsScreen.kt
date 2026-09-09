package com.example.cooperativegig.presentation.customer.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class MyReviewItem(
    val id: String,
    val workerName: String,
    val serviceName: String,
    val rating: Int,
    val reviewText: String,
    val date: String
)

val sampleMyReviews = listOf(
    MyReviewItem("rev1", "Ramesh Kumar", "Plumbing Service", 5, "Fixed the leaking pipe under kitchen sink very fast. Excellent cooperative worker!", "Yesterday"),
    MyReviewItem("rev2", "Suresh Verma", "Electrical Repair", 5, "Installed new switchboard cleanly. Fair pricing.", "Last week")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerReviewsScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Feedback & Reviews", fontWeight = FontWeight.Bold) },
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
            items(sampleMyReviews) { rev ->
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(rev.workerName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(rev.date, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                        }
                        Text(rev.serviceName, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)

                        Spacer(modifier = Modifier.height(6.dp))

                        Row {
                            (1..rev.rating).forEach { _ ->
                                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.size(16.dp))
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(rev.reviewText, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}