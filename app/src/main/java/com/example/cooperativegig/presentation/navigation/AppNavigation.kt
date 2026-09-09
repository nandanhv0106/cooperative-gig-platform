package com.example.cooperativegig.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cooperativegig.presentation.admin.AdminDashboardScreen
import com.example.cooperativegig.presentation.auth.AuthViewModel
import com.example.cooperativegig.presentation.auth.AuthViewModelFactory
import com.example.cooperativegig.presentation.auth.LoginScreen
import com.example.cooperativegig.presentation.auth.RegisterScreen
import com.example.cooperativegig.presentation.auth.SplashScreen
import com.example.cooperativegig.presentation.customer.CustomerMainScreen
import com.example.cooperativegig.presentation.customer.booking.BookingTrackingScreen
import com.example.cooperativegig.presentation.customer.booking.CreateBookingScreen
import com.example.cooperativegig.presentation.customer.booking.EmergencyServiceScreen
import com.example.cooperativegig.presentation.customer.notification.NotificationCenterScreen
import com.example.cooperativegig.presentation.customer.payment.PaymentScreen
import com.example.cooperativegig.presentation.customer.profile.CustomerReviewsScreen
import com.example.cooperativegig.presentation.customer.profile.EditProfileScreen
import com.example.cooperativegig.presentation.customer.profile.SavedAddressesScreen
import com.example.cooperativegig.presentation.customer.profile.SavedWorkersScreen
import com.example.cooperativegig.presentation.customer.profile.SettingsPreferencesScreen
import com.example.cooperativegig.presentation.customer.rating.RatingScreen
import com.example.cooperativegig.presentation.customer.service.ServiceDetailScreen
import com.example.cooperativegig.presentation.customer.worker.WorkerDetailScreen
import com.example.cooperativegig.presentation.worker.WorkerMainScreen
import com.example.cooperativegig.presentation.worker.verification.WorkerVerificationScreen

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
            CustomerMainScreen(
                authViewModel = authViewModel,
                onNavigateToServiceDetail = { serviceId ->
                    navController.navigate("service_detail/$serviceId")
                },
                onNavigateToWorkerDetail = { workerId ->
                    navController.navigate("worker_detail/$workerId")
                },
                onNavigateToEmergencyBooking = {
                    navController.navigate("emergency_booking")
                },
                onNavigateToNotifications = {
                    navController.navigate("notifications")
                },
                onNavigateToEditProfile = {
                    navController.navigate("edit_profile")
                },
                onNavigateToSavedAddresses = {
                    navController.navigate("saved_addresses")
                },
                onNavigateToSavedWorkers = {
                    navController.navigate("saved_workers")
                },
                onNavigateToCustomerReviews = {
                    navController.navigate("customer_reviews")
                },
                onNavigateToSettingsPreferences = {
                    navController.navigate("settings_preferences")
                },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("customer_home") { inclusive = true }
                    }
                }
            )
        }

        composable("edit_profile") {
            EditProfileScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("saved_addresses") {
            SavedAddressesScreen(
                onSelectOnMapClick = {
                    // Location/map selector
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("saved_workers") {
            SavedWorkersScreen(
                onWorkerClick = { workerId ->
                    navController.navigate("worker_detail/$workerId")
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("customer_reviews") {
            CustomerReviewsScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("settings_preferences") {
            SettingsPreferencesScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "service_detail/{serviceId}",
            arguments = listOf(navArgument("serviceId") { type = NavType.LongType })
        ) { backStackEntry ->
            val serviceId = backStackEntry.arguments?.getLong("serviceId") ?: 0L
            ServiceDetailScreen(
                serviceId = serviceId,
                onBookNow = { id ->
                    navController.navigate("create_booking/$id")
                },
                onWorkerClick = { workerId ->
                    navController.navigate("worker_detail/$workerId")
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "worker_detail/{workerId}",
            arguments = listOf(navArgument("workerId") { type = NavType.StringType })
        ) { backStackEntry ->
            val workerId = backStackEntry.arguments?.getString("workerId") ?: "w1"
            WorkerDetailScreen(
                workerId = workerId,
                onBookWorker = {
                    navController.navigate("create_booking/1")
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "create_booking/{serviceId}",
            arguments = listOf(navArgument("serviceId") { type = NavType.LongType })
        ) { backStackEntry ->
            val serviceId = backStackEntry.arguments?.getLong("serviceId") ?: 0L
            CreateBookingScreen(
                serviceId = serviceId,
                isEmergency = false,
                onBookingCreated = { bookingId ->
                    navController.navigate("booking_tracking/$bookingId") {
                        popUpTo("customer_home") { inclusive = false }
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("emergency_booking") {
            EmergencyServiceScreen(
                onDispatchRequested = { bookingId ->
                    navController.navigate("booking_tracking/$bookingId") {
                        popUpTo("customer_home") { inclusive = false }
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "booking_tracking/{bookingId}",
            arguments = listOf(navArgument("bookingId") { type = NavType.LongType })
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getLong("bookingId") ?: 0L
            BookingTrackingScreen(
                bookingId = bookingId,
                onPaymentClick = {
                    navController.navigate("payment/$bookingId")
                },
                onBackHome = {
                    navController.navigate("customer_home") {
                        popUpTo("customer_home") { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = "payment/{bookingId}",
            arguments = listOf(navArgument("bookingId") { type = NavType.LongType })
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getLong("bookingId") ?: 0L
            PaymentScreen(
                bookingId = bookingId,
                onPaymentSuccess = {
                    navController.navigate("rating/$bookingId") {
                        popUpTo("booking_tracking/$bookingId") { inclusive = true }
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "rating/{bookingId}",
            arguments = listOf(navArgument("bookingId") { type = NavType.LongType })
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getLong("bookingId") ?: 0L
            RatingScreen(
                bookingId = bookingId,
                onSubmitted = {
                    navController.navigate("customer_home") {
                        popUpTo("customer_home") { inclusive = true }
                    }
                }
            )
        }

        composable("notifications") {
            NotificationCenterScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("worker_home") {
            WorkerMainScreen(
                authViewModel = authViewModel,
                onNavigateToVerification = {
                    navController.navigate("worker_verification")
                },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("worker_home") { inclusive = true }
                    }
                }
            )
        }

        composable("worker_verification") {
            WorkerVerificationScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("admin_dashboard") {
            AdminDashboardScreen(
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("admin_dashboard") { inclusive = true }
                    }
                }
            )
        }
    }
}