package com.example.cooperativegig.presentation.customer.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPreferencesScreen(
    onBack: () -> Unit
) {
    var pushNotificationsEnabled by remember { mutableStateOf(true) }
    var smsAlertsEnabled by remember { mutableStateOf(true) }
    var selectedLanguage by remember { mutableStateOf("English (IN)") }
    var selectedTheme by remember { mutableStateOf("System Default") }

    val languages = listOf("English (IN)", "Hindi (हिंदी)", "Kannada (ಕನ್ನಡ)")
    val themes = listOf("System Default", "Light Theme", "Dark Theme")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("App Preferences & Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Notification Preferences
            Text("Notification Settings", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Push Notifications", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text("Receive booking status updates & worker dispatch alerts", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Switch(checked = pushNotificationsEnabled, onCheckedChange = { pushNotificationsEnabled = it })
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("SMS & WhatsApp Alerts", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text("Important payment receipts & arrival notifications", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Switch(checked = smsAlertsEnabled, onCheckedChange = { smsAlertsEnabled = it })
                    }
                }
            }

            // Language Selection
            Text("App Language / भाषा / ಭಾಷೆ", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    languages.forEach { lang ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedLanguage = lang }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = selectedLanguage == lang, onClick = { selectedLanguage = lang })
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(lang, style = MaterialTheme.typography.bodyMedium, fontWeight = if (selectedLanguage == lang) FontWeight.Bold else FontWeight.Normal)
                        }
                    }
                }
            }

            // Appearance Mode
            Text("Appearance & Theme", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    themes.forEach { theme ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedTheme = theme }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = selectedTheme == theme, onClick = { selectedTheme = theme })
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(theme, style = MaterialTheme.typography.bodyMedium, fontWeight = if (selectedTheme == theme) FontWeight.Bold else FontWeight.Normal)
                        }
                    }
                }
            }
        }
    }
}