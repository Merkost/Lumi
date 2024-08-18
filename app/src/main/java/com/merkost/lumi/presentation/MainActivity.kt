package com.merkost.lumi.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.merkost.lumi.presentation.screens.navigation.MainNavigation
import com.merkost.lumi.ui.theme.LumiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LumiTheme {
                MainNavigation()
            }
        }
    }
}