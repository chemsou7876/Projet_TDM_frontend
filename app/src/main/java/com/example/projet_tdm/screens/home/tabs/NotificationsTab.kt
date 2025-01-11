package com.example.projet_tdm.screens.home.tabs

import android.annotation.SuppressLint
import android.app.Notification
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


@SuppressLint("NewApi")
@Composable
fun NotificationsTab() {
    val notifications = listOf(
        com.example.projet_tdm.models.Notification(
            2,
            "Offer: 50% Discount!",
            "01-01-2025",
            R.drawable.ic_edit
        ),
        com.example.projet_tdm.models.Notification(
            3,
            "Reminder: Buy Groceries",
            "02-01-2025",
            R.drawable.profile_pic
        ),
        com.example.projet_tdm.models.Notification(
            6,
            "Reminder: Buy Groceries , Reminder: Buy Groceries , Reminder: Buy Groceries Reminder: Buy Groceries Reminder: Buy Groceries",
            "02-01-2025",
            R.drawable.profile_pic
        ),
        com.example.projet_tdm.models.Notification(
            4,
            "New Message Received",
            "05-01-2025",
            R.drawable.ic_pen
        ),
        com.example.projet_tdm.models.Notification(
            5,
            "Event: Conference Tomorrow",
            "06-01-2025",
            R.drawable.ic_pen
        ),

        com.example.projet_tdm.models.Notification(
            1,
            "Meeting at 5 PM",
            "06-01-2025",
            R.drawable.profile_pic 
        ),
    )
    val groupedNotifications = notifications.groupBy { it.date }
    val sortedGroupedNotifications = groupedNotifications.toSortedMap(
        compareByDescending { SimpleDateFormat("dd-MM-yyyy").parse(it) }
    )


    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            sortedGroupedNotifications.forEach { (date, notificationList) ->
                item {
                    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                    val parsedDate = LocalDate.parse(date, formatter)
                    val today = LocalDate.now()
                    val textToDisplay = when {
                        parsedDate == today -> "Today"
                        parsedDate == today.minus(1, ChronoUnit.DAYS) -> "Yesterday"
                        else -> date // You can format this if needed
                    }
                    //   Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text =textToDisplay,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 12.dp )
                    )
                }

                items(notificationList) { notification ->
                   Column (modifier = Modifier.padding(vertical = 5.dp)){
                       NotificationCard(notification.text , pic = notification.pic)
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