package com.example.projet_tdm.screens.home.tabs

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.projet_tdm.models.PannierSingleton
import kotlinx.coroutines.delay
import com.example.projet_tdm.R
import com.example.projet_tdm.services.NotificationService
import com.example.projet_tdm.services.createNotificationChannel
import com.example.projet_tdm.services.sendNotification
import com.example.projet_tdm.ui.theme.Sen
import kotlinx.coroutines.launch


val notificationChannelId = "order_status_channel"
val notificationChannelName = "Order Status Updates"
val notificationChannelDescription = "Notifications for order status updates"



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TrackTab(
    startTrackingTime: Boolean,
    onBackClick: () -> Unit,
    driverNumber: String,
    restaurantName: String = "Uttora Coffee House",
    orderDetails: String = "Ordered At 15 jan, 16:00pm",
    deliveryTime: String = "20 min",
    statuses: List<Pair<String, Boolean>> = listOf(
        "Your order has been received" to false,
        "The restaurant is preparing your food" to false,
        "Your order has been picked up for delivery" to false,
        "Your Order is here!" to false
    )
) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    val userId = sharedPreferences.getString("user_id", "null")

    // Initialize PannierSingleton
    LaunchedEffect(Unit) {
        PannierSingleton.initialize(context)
    }

    // Create Notification Channel
    createNotificationChannel(context)

    var currentStatuses by remember { mutableStateOf(statuses) }
    var timerRunning by remember { mutableStateOf(startTrackingTime) }
    val scope = rememberCoroutineScope()
    val orderId = "67807e4d1a6a3db3b27c68b8"


    // Durée des étapes
    val stepDurations = listOf(5, 5, 5, 5) // durations in seconds

    // Gestion du suivi des étapes
    LaunchedEffect(timerRunning) {
        val notifiedStatuses = mutableSetOf<String>()
        if (timerRunning) {
            for (i in currentStatuses.indices) {
                delay(stepDurations[i] * 1000L)

                currentStatuses = currentStatuses.mapIndexed { index, pair ->
                    if (index <= i) {
                        val updatedStatus = pair.copy(second = true)
                        sendNotification(context, updatedStatus.first, index) // Send notification

                        scope.launch {
                            if (userId != null && !notifiedStatuses.contains(updatedStatus.first)) {
                                // Send notification only if it hasn't been sent for this status
                                try {
                                    NotificationService.sendStatusUpdateNotification(orderId, updatedStatus.first, userId)
                                    notifiedStatuses.add(updatedStatus.first) // Mark status as notified
                                } catch (e: Exception) {
                                    println("Error sending status update notification: ${e.localizedMessage}")
                                }
                            }
                        }

                        updatedStatus
                    } else pair
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxSize()
    ) {
        Text(
            text = "Order Tracking",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 10.dp),
            color = Color(0xFF303030),
            fontSize = 23.sp,
            fontFamily = Sen,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))
        EstimatedDeliveryTime(deliveryTime)
        Spacer(modifier = Modifier.height(24.dp))
        currentStatuses.forEachIndexed { index, (status, isActive) ->
            StatusItem(status, isActive)
            Spacer(modifier = Modifier.height(16.dp))
        }
        Spacer(modifier = Modifier.height(32.dp))
        Spacer(modifier = Modifier.height(32.dp))
        DriverInfo(driverNumber)
    }
}


@Composable
fun RestaurantInfo(restaurantName: String, orderDetails: String,image : Int) {
    Row {
        Image(
            painter = painterResource(id = image),
            contentDescription = "Restaurant Image",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = restaurantName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontFamily = Sen,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = orderDetails,
                fontSize = 14.sp,
                color = Color.Gray,
                fontFamily = Sen,
            )
        }
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
            fontWeight = FontWeight.Bold,
            fontFamily = Sen,
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
            fontWeight = FontWeight.Bold,
            fontFamily = Sen,
        )
        Text(
            text = "ESTIMATED DELIVERY TIME",
            fontSize = 14.sp,
            color = Color.Gray,
            fontFamily = Sen,
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
            color = Color.Gray,
            fontFamily = Sen,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = driverNumber,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Sen,
            color = Color(0xFFFB6D3A),
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$driverNumber"))
                context.startActivity(intent)
            }
        )
    }
}
