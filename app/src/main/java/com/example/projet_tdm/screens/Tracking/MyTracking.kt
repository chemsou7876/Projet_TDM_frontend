package com.example.projet_tdm.screens.Tracking

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyTracking(
    startTrackingTime: Boolean,
    onBackClick: () -> Unit,
    driverNumber: String,
    restaurantName: String,
    restaurantImage: Int,
    orderDate: String,
    orderTime: String,
    orderItems: List<Pair<String, Int>>,
    deliveryTime: String,
    statuses: List<Pair<String, Boolean>> = listOf(
        "Your order has been received" to false,
        "The restaurant is preparing your food" to false,
        "Your order has been picked up for delivery" to false,
        "Your Order is here!" to false
    )
) {
    var currentStatuses by remember { mutableStateOf(statuses) }
    var timerRunning by remember { mutableStateOf(startTrackingTime) }

    // Durée des étapes (en secondes)
    val stepDurations = listOf(5, 5, 5, 5)

    // Gestion du suivi des étapes
    LaunchedEffect(timerRunning) {
        if (timerRunning) {
            for (i in currentStatuses.indices) {
                delay(stepDurations[i] * 1000L)

                currentStatuses = currentStatuses.mapIndexed { index, pair ->
                    if (index <= i) pair.copy(second = true) else pair
                }
            }
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
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp)
        ) {
            RestaurantInfo(restaurantName, restaurantImage, orderDate, orderTime, orderItems)
            EstimatedDeliveryTime(deliveryTime)
            Spacer(modifier = Modifier.height(24.dp))
            currentStatuses.forEachIndexed { index, (status, isActive) ->
                StatusItem(status, isActive)
                Spacer(modifier = Modifier.height(16.dp))
            }
            Spacer(modifier = Modifier.height(10.dp))

            DriverInfo(driverNumber)
        }
    }
}

@Composable
fun RestaurantInfo(
    restaurantName: String,
    restaurantImage: Int,
    orderDate: String,
    orderTime: String,
    orderItems: List<Pair<String, Int>>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = restaurantImage),
                contentDescription = "Restaurant Image",
                modifier = Modifier
                    .size(60.dp)
                    .background(Color.Gray, shape = CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = restaurantName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Ordered on $orderDate at $orderTime",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(8.dp))
                orderItems.forEach { (item, quantity) ->
                    Text(
                        text = "$item x $quantity",
                        fontSize = 14.sp,
                        color = Color.Black

                    )
                }

            }
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
            color = Color(0xFFFB6D3A),
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$driverNumber"))
                context.startActivity(intent)
            }
        )
    }
}
