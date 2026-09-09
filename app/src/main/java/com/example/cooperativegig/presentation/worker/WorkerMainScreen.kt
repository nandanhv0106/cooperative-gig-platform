package com.example.cooperativegig.presentation.worker

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cooperativegig.presentation.auth.AuthViewModel
import com.example.cooperativegig.presentation.worker.home.WorkerDashboardScreen
import com.example.cooperativegig.presentation.worker.profile.WorkerProfileScreen

sealed class WorkerBottomNavItem(val route: String, val title: String, val icon: @Composable () -> Unit) {
    object Dashboard : WorkerBottomNavItem("work_dashboard", "Dashboard", { Icon(Icons.Default.Home, contentDescription = "Dashboard") })
    object Profile : WorkerBottomNavItem("work_profile", "Profile & Welfare", { Icon(Icons.Default.Person, contentDescription = "Profile") })
}

@Composable
fun WorkerMainScreen(
    authViewModel: AuthViewModel,
    onLogout: () -> Unit
) {
    val navController = rememberNavController()
    val workerViewModel: WorkerViewModel = viewModel(factory = WorkerViewModelFactory())
    val navItems = listOf(
        WorkerBottomNavItem.Dashboard,
        WorkerBottomNavItem.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                navItems.forEach { item ->
                    NavigationBarItem(
                        icon = item.icon,
                        label = { Text(item.title) },
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding: PaddingValues ->
        NavHost(
            navController = navController,
            startDestination = WorkerBottomNavItem.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(WorkerBottomNavItem.Dashboard.route) {
                WorkerDashboardScreen(
                    viewModel = workerViewModel
                )
            }
            composable(WorkerBottomNavItem.Profile.route) {
                WorkerProfileScreen(
                    authViewModel = authViewModel,
                    workerViewModel = workerViewModel,
                    onLogout = onLogout
                )
            }
        }
    }
}