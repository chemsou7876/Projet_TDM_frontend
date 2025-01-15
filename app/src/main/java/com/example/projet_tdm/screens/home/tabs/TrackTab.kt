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
import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.ui.draw.blur
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.app.NotificationCompat
import com.example.projet_tdm.models.PannierSingleton
import kotlinx.coroutines.delay
import com.example.projet_tdm.R
import com.example.projet_tdm.services.NotificationService
import com.example.projet_tdm.services.RetrofitClient
import com.example.projet_tdm.services.ReviewRequest
import com.example.projet_tdm.services.ReviewResponse
import com.example.projet_tdm.services.UserSession
import com.example.projet_tdm.services.createNotificationChannel
import com.example.projet_tdm.services.sendNotification
import com.example.projet_tdm.ui.theme.Sen
import kotlinx.coroutines.launch
import retrofit2.Call


val notificationChannelId = "order_status_channel"
val notificationChannelName = "Order Status Updates"
val notificationChannelDescription = "Notifications for order status updates"



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TrackTab(
    startTrackingTime: Boolean,
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

    val driverNumber = "0782683513"
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    val userId = sharedPreferences.getString("user_id", "null")
    var showRatingDialog by remember { mutableStateOf(false) }
    var isOrderDelivered by remember { mutableStateOf(false) }

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

    LaunchedEffect(currentStatuses) {
        // Check if the last status (delivery) becomes true
        if (currentStatuses.last().second && !isOrderDelivered) {
            isOrderDelivered = true
            showRatingDialog = true
        }
    }
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

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxSize()
                .blur(if (showRatingDialog) 4.dp else 0.dp)
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
            androidx.compose.material.Divider(
                color = Color(0xFFE0E0E0),
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (showRatingDialog) {
            RatingDialog(
                showDialog = true,
                onDismiss = { showRatingDialog = false },
                onSubmit = { rating, feedback ->
                    // Handle the rating submission here
                    // You can add API call or database operation
                    println("Rating: $rating, Feedback: $feedback")
                    showRatingDialog = false
                }
            )
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
        Icon(
            imageVector = Icons.Outlined.Phone,
            contentDescription = "Phone",
            tint = Color(0xFFFB6D3A),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Driver's Number:",
            fontSize = 14.sp,
            color = Color.Gray,
            fontFamily = Sen,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = driverNumber,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Sen,
            color = Color(0xFFA0A5BA),
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$driverNumber"))
                context.startActivity(intent)
            }
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onSubmit: (rating: Int, feedback: String) -> Unit
) {
    var rating by remember { mutableStateOf(0) }
    var feedback by remember { mutableStateOf("") }

    if (showDialog) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.8f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Rate your Experience !",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W800,
                    fontFamily = Sen,
                    color = Color(0xFFFF7622)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Rating Stars
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    repeat(5) { index ->
                        Icon(
                            imageVector = if (index < rating) {
                                Icons.Filled.Star  // Filled star for selected
                            } else {
                                Icons.Outlined.StarOutline  // Outlined star for unselected
                            },
                            contentDescription = "Star ${index + 1}",
                            tint = Color(0xFFFF7622),
                            modifier = Modifier
                                .size(40.dp)
                                .clickable(interactionSource = MutableInteractionSource(), indication = null) { rating = index + 1 }
                        )
                        if (index < 4) Spacer(modifier = Modifier.width(8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Feedback TextField
                OutlinedTextField(
                    value = feedback,
                    onValueChange = { feedback = it },
                    placeholder = {
                        Text(
                            "Feel free to share your feedback",
                            color = Color.Gray,
                            fontFamily = Sen
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .background(Color(0xFFF0F5FA), RoundedCornerShape(16.dp)),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Submit Button
                Button(
                    onClick = {
                        val reviewRequest = ReviewRequest(
                            userId = UserSession.userId, // Remplacez par l'ID de l'utilisateur actuel
                            restaurantId = "67748c891540f24d3cf3b757", // Remplacez par l'ID du restaurant
                            rating = rating, // Note donnée par l'utilisateur
                            comment = feedback // Commentaire de l'utilisateur
                        )
                        RetrofitClient.instance.submitReview(reviewRequest).enqueue(
                            object : retrofit2.Callback<ReviewResponse> {
                                override fun onResponse(
                                    call: Call<ReviewResponse>,
                                    response: retrofit2.Response<ReviewResponse>
                                ) {
                                    if (response.isSuccessful) {
                                        // Afficher un message de succès
                                        //  Toast.makeText(context, "Review submitted successfully!", Toast.LENGTH_SHORT).show()
                                        // Fermer le dialogue après la soumission
                                        onDismiss()
                                    } else {
                                        // Afficher un message d'erreur
                                        //   Toast.makeText(context, "Failed to submit review: ${response.message()}", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                                    // Afficher un message d'erreur en cas d'échec de la requête
                                    //   Toast.makeText(context, "Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                                }
                            })
                              },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(62.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF7622)
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        "Submit",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Sen,
                        color = Color.White
                    )
                }
            }
        }
    }
}