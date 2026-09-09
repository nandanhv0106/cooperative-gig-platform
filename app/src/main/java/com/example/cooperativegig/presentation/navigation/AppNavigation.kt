package com.example.cooperativegig.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cooperativegig.presentation.auth.AuthViewModel
import com.example.cooperativegig.presentation.auth.AuthViewModelFactory
import com.example.cooperativegig.presentation.auth.LoginScreen
import com.example.cooperativegig.presentation.auth.RegisterScreen
import com.example.cooperativegig.presentation.auth.SplashScreen
import com.example.cooperativegig.presentation.customer.home.ServicesScreen
import com.example.cooperativegig.presentation.worker.home.WorkerHomeScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory())

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        composable("splash") {
            SplashScreen(
                viewModel = authViewModel,
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo("splash") { inclusive = true }
                    }
                },
                onNavigateToCustomerHome = {
                    navController.navigate("customer_home") {
                        popUpTo("splash") { inclusive = true }
                    }
                },
                onNavigateToWorkerHome = {
                    navController.navigate("worker_home") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("login") {
            LoginScreen(
                viewModel = authViewModel,
                onNavigateToRegister = {
                    navController.navigate("register")
                },
                onNavigateToCustomerHome = {
                    navController.navigate("customer_home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToWorkerHome = {
                    navController.navigate("worker_home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("register") {
            RegisterScreen(
                viewModel = authViewModel,
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onNavigateToCustomerHome = {
                    navController.navigate("customer_home") {
                        popUpTo("register") { inclusive = true }
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToWorkerHome = {
                    navController.navigate("worker_home") {
                        popUpTo("register") { inclusive = true }
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        composable("customer_home") {
            ServicesScreen()
        }

        composable("worker_home") {
            WorkerHomeScreen()
        }
    }
}