package com.example.projet_tdm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.projet_tdm.ui.theme.Projet_TDMTheme
import com.example.projet_tdm.ui.theme.Tracking.MyTracking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Projet_TDMTheme {
                MyTrackingScreen()
            }
        }
    }
}

@Composable
fun MyTrackingScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
        MyTracking(
            modifier = Modifier.padding(padding),
            onBackClick = { /* Logic for back navigation, e.g., finish() or navigation controller */ }
        )
    }
}
