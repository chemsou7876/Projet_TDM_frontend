package com.example.projet_tdm.screens.home.tabs

import android.annotation.SuppressLint
import android.app.Notification
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projet_tdm.R
import com.example.projet_tdm.services.NotificationService
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
@SuppressLint("NewApi")
@Composable
fun NotificationsTab() {
    var notifications by remember { mutableStateOf<List<com.example.projet_tdm.models.Notification>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // State for LazyColumn to manage scroll position
    val listState = rememberLazyListState()

    // Fetch notifications when the composable is first created

    // Function to fetch notifications
    suspend fun fetchNotifications() {
        try {
            // Simulate fetching new notifications (Replace this with real API call)
            notifications = NotificationService.fetchNotifications()
            println("Fetched notifications: $notifications")
        } catch (e: Exception) {
            println("Error fetching notifications: ${e.message}")
        } finally {
            isLoading = false
        }
    }
    LaunchedEffect(Unit) {
        fetchNotifications()
    }

    LaunchedEffect(listState.firstVisibleItemIndex) {
        if (listState.firstVisibleItemIndex == 0 && !isLoading) {
            println("Scrolled to the top, refreshing notifications...")
            isLoading = true  // Set loading state to true
            fetchNotifications()  // Fetch the new notifications
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

    Column(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
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
