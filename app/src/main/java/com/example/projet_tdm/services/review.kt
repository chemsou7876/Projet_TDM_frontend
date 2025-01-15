package com.example.projet_tdm.services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class ReviewRequest(
    val userId: String?,
    val restaurantId: String,
    val rating: Int,
    val comment: String
)

data class ReviewResponse(
    val message: String,
    val review: Review,
    val restaurant: RestaurantRating
)


data class ReviewsResponse(
    val message: String,
    val reviews: List<Review>
)

data class Review(
    val rating: Int,
    val comment: String,
    val user: String, // User ID
    val userName: String // User name
)

data class RestaurantRating(
    val averageRating: Double,
    val totalReviews: Int
)

interface ReviewService {
    @POST("/api/reviews/")
    fun submitReview(@Body reviewRequest: ReviewRequest): Call<ReviewResponse>

    @GET("/api/reviews/{restaurantId}/")
    fun getReviewsForRestaurant(@Path("restaurantId") restaurantId: String): Call<ReviewsResponse>
}



object RetrofitClient {
    private const val BASE_URL = "https://deliveryfood-backend-yyxy.onrender.com" // Replace with your server URL

    val instance: ReviewService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ReviewService::class.java)
    }
}