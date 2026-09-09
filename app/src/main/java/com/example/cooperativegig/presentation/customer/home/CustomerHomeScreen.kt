package com.example.cooperativegig.presentation.customer.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cooperativegig.data.model.Service
import com.example.cooperativegig.presentation.components.*

data class ServiceCategoryItem(
    val name: String,
    val icon: ImageVector
)

val defaultCategories = listOf(
    ServiceCategoryItem("Plumbing", Icons.Default.WaterDrop),
    ServiceCategoryItem("Electrical", Icons.Default.Bolt),
    ServiceCategoryItem("Carpentry", Icons.Default.Build),
    ServiceCategoryItem("Cleaning", Icons.Default.CleaningServices),
    ServiceCategoryItem("Technician", Icons.Default.Handyman),
    ServiceCategoryItem("Painting", Icons.Default.FormatPaint),
    ServiceCategoryItem("Gardening", Icons.Default.Yard),
    ServiceCategoryItem("Driver", Icons.Default.DirectionsCar),
    ServiceCategoryItem("Domestic Help", Icons.Default.Home),
    ServiceCategoryItem("Caregiver", Icons.Default.VolunteerActivism)
)

val sampleWorkers = listOf(
    DisplayWorker("w1", "Ramesh Kumar", "Plumber", 4.9, 8, 0.8),
    DisplayWorker("w2", "Suresh Verma", "Electrician", 4.8, 6, 1.2),
    DisplayWorker("w3", "Anita Devi", "Cleaning Specialist", 4.9, 5, 1.5),
    DisplayWorker("w4", "Vikram Singh", "Carpenter", 4.7, 10, 2.1)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerHomeScreen(
    viewModel: HomeViewModel = viewModel(factory = HomeViewModelFactory()),
    onServiceClick: (Long) -> Unit,
    onEmergencyClick: () -> Unit,
    onWorkerClick: (String) -> Unit = {},
    onNotificationClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Top Header Bar
        StrataHeader(
            userName = "Nandan",
            location = "Sector 4, Dwarka, New Delhi",
            onLocationClick = { },
            onNotificationClick = onNotificationClick,
            onProfileClick = onProfileClick
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Search Bar
        StrataSearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            placeholder = "Search plumbing, electrical, cleaning..."
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // 1. Emergency Service Callout
            item {
                EmergencyBanner(
                    onEmergencyClick = onEmergencyClick
                )
            }

            // 2. Service Categories Section
            item {
                SectionHeader(
                    title = "Service Categories",
                    subtitle = "Book skilled workers from local cooperatives",
                    actionLabel = "All Categories",
                    onActionClick = { }
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(defaultCategories) { cat ->
                        ServiceCategoryCard(
                            categoryName = cat.name,
                            icon = cat.icon,
                            onClick = {
                                // Match category to first relevant service
                            }
                        )
                    }
                }
            }

            // 3. Popular Services Section (DB-driven)
            item {
                SectionHeader(
                    title = "Popular Services",
                    subtitle = "Transparent pricing & verified trade experts",
                    actionLabel = null
                )
            }

            when (val state = uiState) {
                is HomeUiState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                is HomeUiState.Success -> {
                    val filteredServices = state.services.filter {
                        it.name.contains(searchQuery, ignoreCase = true) ||
                                (it.description?.contains(searchQuery, ignoreCase = true) == true)
                    }

                    if (filteredServices.isEmpty()) {
                        item {
                            EmptyState(
                                title = "No Services Found",
                                message = "No cooperative services match \"$searchQuery\"."
                            )
                        }
                    } else {
                        items(filteredServices) { service ->
                            PopularServiceCard(
                                service = service,
                                onClick = { onServiceClick(service.id) }
                            )
                        }
                    }
                }
                is HomeUiState.Error -> {
                    item {
                        ErrorState(
                            message = state.message,
                            onRetry = { viewModel.fetchServices() }
                        )
                    }
                }
            }

            // 4. Nearby Verified Workers Section
            item {
                SectionHeader(
                    title = "Nearby Verified Workers",
                    subtitle = "Experienced cooperative members near you",
                    actionLabel = "View All",
                    onActionClick = { }
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(sampleWorkers) { worker ->
                        WorkerCard(
                            worker = worker,
                            onBookClick = { onWorkerClick(worker.id) }
                        )
                    }
                }
            }

            // 5. Why Choose Cooperative Services Card
            item {
                WhyCooperativeCard()
            }
        }
    }
}