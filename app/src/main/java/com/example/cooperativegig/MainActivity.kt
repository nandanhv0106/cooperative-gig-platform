package com.example.cooperativegig

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.cooperativegig.ui.theme.CooperativeGigPlatformTheme
import io.github.jan.supabase.postgrest.from

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CooperativeGigPlatformTheme {
                ServicesScreen()
            }
        }
    }
}

@Composable
fun ServicesScreen() {

    var services by remember { mutableStateOf<List<String>>(emptyList()) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {

        try {
            val result = SupabaseClient.client
                .from("services")
                .select()

            services = result.decodeList<Service>().map { it.name }

        } catch (e: Exception) {
            error = e.message
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("Cooperative Gig Platform")

        if (error != null) {

            Text("Error: $error")

        } else if (services.isEmpty()) {

            Text("Loading services...")

        } else {

            services.forEach { service ->
                Text(service)
            }
        }
    }
}

@kotlinx.serialization.Serializable
data class Service(
    val id: Long,
    val name: String,
    val description: String? = null,
    @kotlinx.serialization.SerialName("base_price")
    val basePrice: Double = 0.0
)