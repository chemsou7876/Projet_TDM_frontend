package com.example.projet_tdm.screens.home.tabs

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projet_tdm.R
import com.example.projet_tdm.services.NotificationService
import com.example.projet_tdm.services.UserSession
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@Composable
fun NotificationsTab() {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    val userId= sharedPreferences.getString("user_id", null)  // Return null if not found

    val currentUserId = userId
    var notifications by remember { mutableStateOf<List<com.example.projet_tdm.models.Notification>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    val listState = rememberLazyListState()


    suspend fun fetchNotifications(userId: String) = try {
        val fetchedNotifications = NotificationService.fetchNotifications(userId)
        notifications = fetchedNotifications
        println("Fetched notifications for user $userId: $notifications")
    } catch (e: Exception) {
        println("Error fetching notifications: ${e.message}")
    } finally {
        isLoading = false
    }
    LaunchedEffect(Unit) {
        if (currentUserId != null) {
            fetchNotifications(currentUserId)
        }
    }

    LaunchedEffect(listState.firstVisibleItemIndex) {
        if (listState.firstVisibleItemIndex == 0 && !isLoading) {
            println("Scrolled to the top, refreshing notifications...")
            isLoading = true  // Set loading state to true

            if (currentUserId != null) {
                fetchNotifications(currentUserId)
            }

        }
    }


    val groupedNotifications = notifications.groupBy { notification ->
        try {
            // Parse notification date using "yyyy-MM-dd" format
            val parsedDate = LocalDate.parse(notification.date)

            val todayLocalDate = LocalDate.now()
            val yesterdayLocalDate = todayLocalDate.minusDays(1)

            // Handle "Today" and "Yesterday"
            when {
                parsedDate.isEqual(todayLocalDate) -> "Today"
                parsedDate.isEqual(yesterdayLocalDate) -> "Yesterday"
                else -> parsedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) // Custom format (yyyy-MM-dd)
            }
        } catch (e: Exception) {
            // In case of an error, return the original date string
            notification.date
        }
    }

    Column(modifier = Modifier.padding((8.dp)).fillMaxSize()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.back_icon),
                contentDescription = "Default Profile Image",
                modifier = Modifier.size(55.dp).clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                   // navController.navigate("settings") // Navigate to the HomeScreen when clicked
                },
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text ="Notifications",
                fontSize = 22.sp)

        }
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterHorizontally)
                    , color = Color(0xFFFF7622)
            )
        } else {
            if(notifications.isEmpty())
            {
                Column (modifier = Modifier.fillMaxSize() , verticalArrangement = Arrangement.Center , horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("You have no notifications !! " , color = Color(0xFFFF7622) , fontWeight = FontWeight.Bold , fontSize = 15.sp)
                }
            }
            else {
                LazyColumn(
                    state = listState, // Pass the scroll state
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    groupedNotifications.forEach { (displayDate, notificationsOnSameDay) ->
                        item {
                            Text(
                                text = displayDate,
                                fontSize = 14.sp,
                                color = Color.Gray,  // Customize the style here
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                        notificationsOnSameDay.forEach { notification ->
                            item {
                                NotificationCard(
                                    notification.text,
                                    pic = notification.pic
                                )
                                Spacer(modifier = Modifier.height(15.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun NotificationCard(text: String, pic: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(12.dp))
            .background(color = Color(0xFFFFF0EA))
            .padding(vertical = 15.dp, horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = pic),
            contentDescription = "Default Notification Image",
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text, fontSize = 16.sp)
    }
}
