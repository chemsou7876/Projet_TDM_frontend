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
import coil.compose.rememberImagePainter
import com.example.projet_tdm.models.PannierSingleton
import com.example.projet_tdm.models.getData
import kotlinx.coroutines.delay

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

    // Initialize PannierSingleton when the screen is composed
    LaunchedEffect(Unit) {
        PannierSingleton.initialize(context)
    }

    // Access the pannier
    val pannier = PannierSingleton.pannier

    val restaurants = getData()
    val pannierRestaurants = restaurants.filter { restaurant ->
        pannier.orders.any { order ->
            restaurant.menus.contains(order.item)
        }}


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
           // RestaurantInfo(pannierRestaurants[0].name, orderDetails,pannierRestaurants[0].imgUrl)
            Spacer(modifier = Modifier.height(24.dp))
            currentStatuses.forEachIndexed { index, (status, isActive) ->
                StatusItem(status, isActive)
                Spacer(modifier = Modifier.height(16.dp))
            }
            Spacer(modifier = Modifier.height(32.dp))
            EstimatedDeliveryTime(deliveryTime)
            Spacer(modifier = Modifier.height(32.dp))
            DriverInfo(driverNumber)
        }
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
