package com.example.cooperativegig.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ServiceCategoryCard(
    categoryName: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier
            .width(96.dp)
            .height(112.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = categoryName,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = categoryName,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

fun getIconForCategory(categoryName: String): ImageVector {
    return when {
        categoryName.contains("Plumb", ignoreCase = true) -> Icons.Default.WaterDrop
        categoryName.contains("Electr", ignoreCase = true) -> Icons.Default.Bolt
        categoryName.contains("Carpen", ignoreCase = true) -> Icons.Default.Build
        categoryName.contains("Clean", ignoreCase = true) -> Icons.Default.CleaningServices
        categoryName.contains("Paint", ignoreCase = true) -> Icons.Default.FormatPaint
        categoryName.contains("Garden", ignoreCase = true) -> Icons.Default.Yard
        categoryName.contains("Drive", ignoreCase = true) -> Icons.Default.DirectionsCar
        categoryName.contains("Domestic", ignoreCase = true) || categoryName.contains("Help", ignoreCase = true) -> Icons.Default.Home
        categoryName.contains("Care", ignoreCase = true) -> Icons.Default.VolunteerActivism
        categoryName.contains("Tech", ignoreCase = true) || categoryName.contains("Repair", ignoreCase = true) -> Icons.Default.Handyman
        else -> Icons.Default.Construction
    }
}