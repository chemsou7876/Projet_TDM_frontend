package com.example.projet_tdm.ui.theme.Tracking

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyTracking(
    startTrackingTime: Boolean, // Nouveau paramètre pour démarrer le suivi du temps
    onBackClick: () -> Unit,
    driverNumber: String,
    restaurantName: String = "Uttora Coffee House",
    orderDetails: String = "Ordered At 06 Sept, 10:00pm",
    deliveryTime: String = "20 min",
    statuses: List<Pair<String, Boolean>> = listOf(
        "Your order has been received" to false,  // Step 1: Not started
        "The restaurant is preparing your food" to false,  // Step 2: Not started
        "Your order has been picked up for delivery" to false,  // Step 3: Not started
        "Your Order is here!" to false  // Step 4: Not started
    )
) {
    var currentStatuses by remember { mutableStateOf(statuses) }
    var timerRunning by remember { mutableStateOf(startTrackingTime) }

    // Durée des étapes (en secondes)
    val stepDurations = listOf(5, 5, 5, 5) // Délai de 5 secondes pour chaque étape, tu peux modifier

    // Si le suivi commence, on démarre le suivi des étapes
    LaunchedEffect(timerRunning) {
        if (timerRunning) {
            for (i in currentStatuses.indices) {
                delay(stepDurations[i] * 1000L) // Attendre la durée de l'étape actuelle

                // Mise à jour de l'état des étapes
                currentStatuses = currentStatuses.mapIndexed { index, pair ->
                    when {
                        index == i -> pair.copy(second = true) // Étape en cours
                        index == i - 1 -> pair.copy(second = true) // Étape précédente terminée
                        else -> pair // Ne change pas les autres étapes
                    }
                }

                // Après chaque étape, on marque la suivante comme "en cours"
            }

            // Une fois toutes les étapes terminées, remettre l'état initial
            currentStatuses = currentStatuses.map { it.copy(second = false) }
        }
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
                Text(
                    text = "Order Tracking",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        },
        modifier = Modifier.fillMaxSize().padding(16.dp) // Retirer contentPadding
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp) // Ajouter de l'espace entre la flèche et les informations sur la commande
        ) {
            // Affichage des informations du restaurant
            RestaurantInfo(restaurantName, orderDetails)

            Spacer(modifier = Modifier.height(24.dp)) // Espace entre les infos du resto et les étapes

            // Affichage des étapes avec leur état
            currentStatuses.forEachIndexed { index, (status, isActive) ->
                StatusItem(status, isActive)
                Spacer(modifier = Modifier.height(16.dp)) // Espace entre chaque étape
            }

            Spacer(modifier = Modifier.height(32.dp)) // Espace entre les étapes et le temps de livraison

            // Estimation du temps de livraison
            EstimatedDeliveryTime(deliveryTime)

            Spacer(modifier = Modifier.height(32.dp)) // Espace entre le temps de livraison et les infos du conducteur

            // Informations sur le conducteur
            DriverInfo(driverNumber)
        }
    }
}

@Composable
fun RestaurantInfo(restaurantName: String, orderDetails: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = restaurantName,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = orderDetails,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun StatusItem(text: String, isActive: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = if (isActive) Color(0xFFFFA500) else Color.Gray,
                    shape = CircleShape
                )
        ) {
            if (isActive) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Completed",
                    modifier = Modifier.align(Alignment.Center),
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = if (isActive) Color(0xFFFFA500) else Color.Gray,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun EstimatedDeliveryTime(deliveryTime: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = deliveryTime,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "ESTIMATED DELIVERY TIME",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun DriverInfo(driverNumber: String) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Driver's Number:",
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = driverNumber,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFB6D3A), // Couleur cliquable
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$driverNumber"))
                context.startActivity(intent)
            }
        )
    }
}