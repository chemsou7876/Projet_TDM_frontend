package com.example.projet_tdm.screens.home.tabs

import android.annotation.SuppressLint
import android.app.Notification
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

    // Fetch notifications when the composable is first created
    LaunchedEffect(Unit) {
        try {
            notifications = NotificationService.fetchNotifications()
            print(notifications)
        } catch (e: Exception) {
           print("a prblm in notifications ! ")
        } finally {
            isLoading = false
        }
    }

    val groupedNotifications = notifications.groupBy { it.date }
    val sortedGroupedNotifications = groupedNotifications.toSortedMap(
        compareByDescending { date ->
            try {
                // Parse ISO 8601 date format
                OffsetDateTime.parse(date).toLocalDate()
            } catch (e: Exception) {
                // Fallback for dd-MM-yyyy format
                try {
                    LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                } catch (e: Exception) {
                    // If both parsing attempts fail, use epoch date as fallback
                    LocalDate.EPOCH
                }
            }
        }
    )

    Column(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                sortedGroupedNotifications.forEach { (date, notificationList) ->
                    item {
                        val displayDate = try {
                            // Parse ISO 8601 date
                            val parsedDate = OffsetDateTime.parse(date).toLocalDate()
                            val today = LocalDate.now()
                            when {
                                parsedDate == today -> "Today"
                                parsedDate == today.minusDays(1) -> "Yesterday"
                                else -> parsedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                            }
                        } catch (e: Exception) {
                            // If parsing fails, just display the original date string
                            date
                        }

                        Text(
                            text = displayDate,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }

                    items(notificationList) { notification ->
                        Column(modifier = Modifier.padding(vertical = 5.dp)) {
                            NotificationCard(notification.text, pic = notification.pic)
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun NotificationCard(text:String , pic: Int){
   Row (modifier = Modifier.fillMaxWidth()
       .clip(shape = RoundedCornerShape(12.dp))
       .background(color = Color(0xFFFFF0EA))
       .padding(vertical = 15.dp , horizontal = 6.dp)
       , verticalAlignment = Alignment.CenterVertically) {
       Image(
           painter = painterResource(id = pic),
           contentDescription = "Default Notification Image",
           modifier = Modifier.size(40.dp)
       )
       Spacer(modifier = Modifier.width(10.dp))
       Text(text, fontSize = 16.sp)
   }
}