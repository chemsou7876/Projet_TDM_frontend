package com.example.projet_tdm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import com.example.projet_tdm.screens.Tracking.MyTracking

class MainActivity : ComponentActivity() {

    // Constantes simulant les données reçues du backend
    private val driverNumber = "1234567890"
    private val restaurantName = "Uttora Coffee House"
    private val orderDetails = "Ordered at 06 Sept, 10:00pm"
    private val estimatedDeliveryTime = "30 min"

    // Statuts simulés du backend
    private val statusesFromBackend = listOf(
        "Your order has been received" to false,
        "The restaurant is preparing your food" to false,
        "Your order has been picked up for delivery" to false,
        "Your Order is here!" to false
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Définition de l'interface utilisateur
        setContent {
            // Appel de l'écran de suivi
            MyTracking(
                startTrackingTime = true,  // Démarre le suivi automatiquement
                onBackClick = { handleBackClick() },  // Gestion du bouton retour
                driverNumber = driverNumber,
                restaurantName = restaurantName,
                orderDetails = orderDetails,
                deliveryTime = estimatedDeliveryTime,
                statuses = statusesFromBackend
            )
        }
    }

    // Fonction simulant le retour à l'écran précédent
    private fun handleBackClick() {
        finish()  // Ferme l'activité actuelle
    }
}
