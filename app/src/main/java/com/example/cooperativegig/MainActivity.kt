package com.example.cooperativegig

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.cooperativegig.presentation.navigation.AppNavigation
import com.example.cooperativegig.ui.theme.CooperativeGigPlatformTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CooperativeGigPlatformTheme {
                AppNavigation()
            }
        }
    }
}