package com.ergi.digimon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ergi.digimon.ui.navigation.AppNavigation
import com.ergi.digimon.ui.theme.DigimonTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DigimonTheme {
                AppNavigation()
            }
        }
    }
}