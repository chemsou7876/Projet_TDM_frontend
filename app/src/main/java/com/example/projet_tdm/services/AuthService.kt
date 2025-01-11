package com.example.projet_tdm.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call


// Define the request model
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val message: String?,
    val user: User?
)

data class User(
    val id: String?,
    val name: String?,
    val email: String?
)


interface AuthService {
    @POST("api/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}

object ApiClient {
    private const val BASE_URL = "https://deliveryfood-backend-yyxy.onrender.com/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }
}

