package com.example.projet_tdm.ui.theme.Tracking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyTracking(modifier: Modifier = Modifier, onBackClick: () -> Unit) {
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
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Restaurant Info
            RestaurantInfo()

            Spacer(modifier = Modifier.height(32.dp))

            // Order Status
            Column {
                StatusItem("Your order has been received", isActive = true)
                StatusItem("The restaurant is preparing your food", isActive = true)
                StatusItem("Your order has been picked up for delivery", isActive = false)
                StatusItem("Your Order is here!", isActive = false)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Estimated Delivery Time
            EstimatedDeliveryTime()

            Spacer(modifier = Modifier.height(32.dp))

            // Driver Info
            DriverInfo()
        }
    }
}

@Composable
fun RestaurantInfo() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Gray, shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text("Uttora Coffee House", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Ordered At 06 Sept, 10:00pm", color = Color.Gray, fontSize = 14.sp)
        }
    }
}

@Composable
fun StatusItem(text: String, isActive: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(
                    color = if (isActive) Color(0xFFFFA500) else Color.Gray,
                    shape = CircleShape
                )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = if (isActive) Color.Black else Color.Gray,
            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun EstimatedDeliveryTime() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "20 min",
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
fun DriverInfo() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "Driver's Number:",
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "0556 44 24 68",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
