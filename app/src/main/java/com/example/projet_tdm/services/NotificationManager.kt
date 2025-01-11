package com.example.projet_tdm.services
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.projet_tdm.R

// Notification Channel (required for Android Oreo and above)
val notificationChannelId = "order_status_channel"
val notificationChannelName = "Order Status Updates"
val notificationChannelDescription = "Notifications for order status updates"

fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            notificationChannelId,
            notificationChannelName,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = notificationChannelDescription
        }
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun sendNotification(context: Context, message: String, id: Int) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val builder = NotificationCompat.Builder(context, notificationChannelId)
        .setSmallIcon(R.drawable.img) // Your notification icon
        .setContentTitle("Order Status")
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)
        .setTicker(message)

    notificationManager.notify(id, builder.build())
}
