package com.example.projet_tdm.services

import android.annotation.SuppressLint
import com.example.projet_tdm.R
import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

// Response data from backend API
data class NotificationResponse(
    @SerializedName("_id") val id: String, // Map MongoDB _id to id field
    val text: String,
    val date: String, // ISO 8601 format
)


// Retrofit interface
interface NotificationApi {
    @GET("api/notifications/")
    suspend fun getNotifications(): List<NotificationResponse>
}

// Service class for handling notifications fetching and mapping
object NotificationService {
    private const val BASE_URL = "https://deliveryfood-backend-yyxy.onrender.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val notificationApi = retrofit.create(NotificationApi::class.java)

    // Fetch notifications and map them to the app model
    @SuppressLint("NewApi")
    suspend fun fetchNotifications(): List<com.example.projet_tdm.models.Notification> {
        return withContext(Dispatchers.IO) {
            try {
                // Fetch notifications from the API
                val notificationsResponse = notificationApi.getNotifications()
                println("Notifications fetched successfully: $notificationsResponse")

                // Map backend notifications to app notifications
                notificationsResponse.map { response ->
                    // Parse the date string if needed (optional based on your usage)
                    val parsedDate = try {
                        OffsetDateTime.parse(response.date).toLocalDate()
                    } catch (e: Exception) {
                        // Fallback date if parsing fails
                        println("Error parsing date for notification ID ${response.id}: ${e.localizedMessage}")
                        null
                    }
                    com.example.projet_tdm.models.Notification(
                        id = response.id, // _id from MongoDB
                        text = response.text,
                        date = parsedDate?.toString() ?: response.date, // Use parsed date or fallback date string
                        pic = R.drawable.profile_pic
                    )
                }
            } catch (e: Exception) {
                // Catch any error during the fetch operation
                println("Error fetching notifications: ${e.localizedMessage}")
                e.printStackTrace()
                emptyList()
            }
        }
    }
}
