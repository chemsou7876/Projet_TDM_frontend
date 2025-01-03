package com.example.projet_tdm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.projet_tdm.ui.theme.Tracking.MyTracking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Appel de MyTracking avec le paramètre pour démarrer le suivi du temps
            MyTracking(
                startTrackingTime = true, // Démarre le suivi du temps dès le début
                onBackClick = { /* Handle back click */ },
                driverNumber = "1234567890",
                restaurantName = "Uttora Coffee House",
                orderDetails = "Ordered At 06 Sept, 10:00pm",
                deliveryTime = "30 min",
                statuses = listOf(
                    "Your order has been received" to false,  // Step 1: Not started
                    "The restaurant is preparing your food" to false,  // Step 2: Not started
                    "Your order has been picked up for delivery" to false,  // Step 3: Not started
                    "Your Order is here!" to false  // Step 4: Not started
                )
            )
        }
    }
}
