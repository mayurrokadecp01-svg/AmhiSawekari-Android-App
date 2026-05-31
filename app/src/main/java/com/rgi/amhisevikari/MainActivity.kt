package com.rgi.amhisevikari

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.rgi.amhisevikari.presentation.navigation.AmhiSevekariNavGraph
import com.rgi.amhisevikari.ui.theme.AmhiSevikariTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Edge-to-edge only on API 29+ (Android Q) which properly supports
        // transparent status/navigation bars without visual glitches
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            enableEdgeToEdge()
        }
        setContent {
            AmhiSevikariTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    AmhiSevekariNavGraph(navController = navController)
                }
            }
        }
    }
}