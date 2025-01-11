package com.example.projet_tdm.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.PUT


data class InfoRequest(
    val id: String?,
)

data class UpdateRequest(
    val id: String?,
    val name: String?,
    val email: String?,
    val profilePicture: String?,
    val addresses: List<Address>?,
    val phoneNumber: String?,
    val bio: String?,
)

data class InfoResponse(
    val message: String?,
    val user: InfoUser?
)

data class Address(
    val title: String?,
    val street: String?,
    val city: String?,
    val postalCode: String?
)

data class InfoUser(
    val id: String?,
    val name: String?,
    val email: String?,
    val profilePicture: String?,
    val addresses: List<Address>?,
    val phoneNumber: String?,
    val bio: String?
)




interface InfoService {
    @POST("api/auth/userinfo")
    fun getInfo(@Body request: InfoRequest): Call<InfoResponse>
    @PUT("api/auth/updateUserInfo")
    fun updateInfo(@Body request: UpdateRequest):Call<InfoResponse>
}


object ApiInfoClient {
    private const val BASE_URL = "https://deliveryfood-backend-yyxy.onrender.com/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authService: InfoService by lazy {
        retrofit.create(InfoService::class.java)
    }
}




