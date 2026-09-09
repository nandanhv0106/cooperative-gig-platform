package com.example.cooperativegig.presentation.customer.rating

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
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
fun RatingScreen(
    bookingId: Long,
    onSubmitted: () -> Unit
) {
    var rating by remember { mutableIntStateOf(5) }
    var reviewText by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rate Service Experience", fontWeight = FontWeight.Bold) }
            )
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                shadowElevation = 8.dp
            ) {
                Button(
                    onClick = { isSubmitting = true },
                    enabled = !isSubmitting,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (isSubmitting) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                    } else {
                        Text("Submit Review to Cooperative", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "How was your service experience?",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Your rating helps worker Ramesh Kumar and the labor cooperative maintain high trade quality.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Star Selector Bar
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                (1..5).forEach { starIndex ->
                    Icon(
                        imageVector = if (starIndex <= rating) Icons.Default.Star else Icons.Outlined.Star,
                        contentDescription = "Star $starIndex",
                        tint = if (starIndex <= rating) Color(0xFFD97706) else MaterialTheme.colorScheme.outline,
                        modifier = Modifier
                            .size(48.dp)
                            .clickable { rating = starIndex }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = when (rating) {
                    5 -> "Excellent! Exceeded expectations ⭐⭐⭐⭐⭐"
                    4 -> "Very Good ⭐⭐⭐⭐"
                    3 -> "Good ⭐⭐⭐"
                    2 -> "Fair ⭐⭐"
                    else -> "Needs Improvement ⭐"
                },
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = reviewText,
                onValueChange = { reviewText = it },
                label = { Text("Write a review (optional)") },
                placeholder = { Text("Share details about trade work quality, behavior, or punctuality...") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
                shape = RoundedCornerShape(12.dp)
            )
        }
    }

    LaunchedEffect(isSubmitting) {
        if (isSubmitting) {
            delay(1000)
            isSubmitting = false
            onSubmitted()
        }
    }
}