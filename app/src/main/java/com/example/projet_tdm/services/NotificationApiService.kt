package com.example.projet_tdm.services

import com.example.projet_tdm.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class NotificationResponse(
    val id: Int,
    val text: String,
    val date: String,
)

// Retrofit interface
interface NotificationApi {
    @GET("api/notifications/")
    suspend fun getNotifications(): List<NotificationResponse>
}

// Service class
object NotificationService {
    private const val BASE_URL = "https://deliveryfood-backend-yyxy.onrender.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val notificationApi = retrofit.create(NotificationApi::class.java)

    suspend fun fetchNotifications(): List<com.example.projet_tdm.models.Notification> {
        return withContext(Dispatchers.IO) {
            try {
                notificationApi.getNotifications().map { response ->
                    com.example.projet_tdm.models.Notification(
                        id = response.id,
                        text = response.text,
                        date = response.date,
                        pic = R.drawable.profile_pic
                    )
                }
            } catch (e: Exception) {
                // Handle error appropriately
                emptyList()
            }
        }
    }
}