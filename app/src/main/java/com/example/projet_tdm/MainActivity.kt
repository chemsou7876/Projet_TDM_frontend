package com.example.projet_tdm
// app/src/main/java/com/yourpackage/MainActivity.kt


import Profile
import android.os.Bundle
import android.provider.ContactsContract.Profile
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.material3.*
import com.example.projet_tdm.screens.Tracking.MyTracking

import androidx.navigation.compose.rememberNavController
import com.example.projet_tdm.models.Notification
import com.example.projet_tdm.navigation.AppNavigation
import com.example.projet_tdm.screens.auth.SignUpScreen
import com.example.projet_tdm.screens.home.tabs.NotificationsTab
import com.example.projet_tdm.screens.profile.Adresses
import com.example.projet_tdm.screens.profile.Edit_adresses
import com.example.projet_tdm.screens.profile.ProfilePage
import com.example.projet_tdm.screens.settings.Setting

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

        setContent {

            AppNavigation()


           
        }

    }
}
