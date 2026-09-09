package com.example.cooperativegig.presentation.customer.location

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cooperativegig.data.model.Worker
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

data class MapWorkerInfo(
    val worker: Worker,
    val name: String,
    val skill: String,
    val lat: Double,
    val lng: Double
)

val sampleMapWorkers = listOf(
    MapWorkerInfo(
        worker = Worker("w1", rating = 4.9, totalEarnings = 12000.0),
        name = "Ramesh Kumar",
        skill = "Master Plumber",
        lat = 28.5940,
        lng = 77.0480
    ),
    MapWorkerInfo(
        worker = Worker("w2", rating = 4.8, totalEarnings = 9500.0),
        name = "Suresh Verma",
        skill = "Licensed Electrician",
        lat = 28.5890,
        lng = 77.0420
    ),
    MapWorkerInfo(
        worker = Worker("w3", rating = 4.9, totalEarnings = 8200.0),
        name = "Anita Devi",
        skill = "Cleaning Specialist",
        lat = 28.5970,
        lng = 77.0510
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NearbyWorkersMapScreen(
    customerLat: Double = 28.5921,
    customerLng: Double = 77.0460,
    onWorkerSelect: (String) -> Unit,
    onBack: () -> Unit
) {
    val centerLatLng = remember { LatLng(customerLat, customerLng) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(centerLatLng, 14f)
    }

    var selectedWorker by remember { mutableStateOf<MapWorkerInfo?>(null) }
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nearby Verified Workers", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(zoomControlsEnabled = true)
            ) {
                // Customer Position Marker
                Marker(
                    state = MarkerState(position = centerLatLng),
                    title = "Your Service Location",
                    snippet = "Sector 4, Dwarka"
                )

                // Nearby Available Verified Workers Markers
                sampleMapWorkers.forEach { mapWorker ->
                    Marker(
                        state = MarkerState(position = LatLng(mapWorker.lat, mapWorker.lng)),
                        title = mapWorker.name,
                        snippet = mapWorker.skill,
                        onClick = {
                            selectedWorker = mapWorker
                            true
                        }
                    )
                }
            }
        }
    }

    // Bottom Sheet for selected worker
    if (selectedWorker != null) {
        ModalBottomSheet(
            onDismissRequest = { selectedWorker = null },
            sheetState = sheetState
        ) {
            val w = selectedWorker!!
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = w.name,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = w.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.CheckCircle, contentDescription = "Verified", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                        }
                        Text(text = w.skill, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFD97706), modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(text = "${w.worker.rating} (Verified Member)", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        val id = w.worker.id
                        selectedWorker = null
                        onWorkerSelect(id)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("View Profile & Book", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}