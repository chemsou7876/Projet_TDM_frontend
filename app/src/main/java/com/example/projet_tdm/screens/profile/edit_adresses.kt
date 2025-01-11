package com.example.projet_tdm.screens.profile

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projet_tdm.R
import com.example.projet_tdm.components.CustomTextField
import com.example.projet_tdm.components.OrangeButton
import com.example.projet_tdm.services.Address
import com.example.projet_tdm.services.ApiInfoClient
import com.example.projet_tdm.services.InfoResponse
import com.example.projet_tdm.services.LoginResponse
import com.example.projet_tdm.services.UpdateRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback



@Composable
fun Edit_adresses(navController: NavController) {
    val context = LocalContext.current
    var address by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var postCode by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf(1) }

    // Get current list of addresses from SharedPreferences
    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    val addressesJson = sharedPreferences.getString("user_addresses", "[]") ?: "[]"
    val gson = Gson()
    val type = object : TypeToken<List<Address>>() {}.type
    var addressList by remember { mutableStateOf(gson.fromJson<List<Address>>(addressesJson, type)) }


    fun updateUserInfo(context : Context, navController: NavController, updatedInfo: UpdateRequest){

        val infoService = ApiInfoClient.authService
        val request = UpdateRequest(updatedInfo.id , updatedInfo.name , updatedInfo.email , updatedInfo.profilePicture ,
            updatedInfo.addresses , updatedInfo.phoneNumber , updatedInfo.bio)
        infoService.updateInfo(request).enqueue(object: Callback<InfoResponse> {
            override fun onResponse(call: Call<InfoResponse>, response: retrofit2.Response<InfoResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result?.user != null) {
                        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()


                        editor.putBoolean("is_logged_in", true)
                        editor.putString("user_id", updatedInfo.id)
                        editor.putString("user_name", result.user.name)
                        editor.putString("user_email", result.user.email)
                        editor.putString("user_phoneNumber", result.user.phoneNumber)
                        editor.putString("user_profilePicture", result.user.profilePicture)
                        editor.putString("user_bio", result.user.bio)
                        val gson = Gson()
                        val addressesJson = gson.toJson(result.user.addresses) // user.addresses is a List<Address>
                        editor.putString("user_addresses", addressesJson)
                        editor.apply()

                        navController.navigate("settings"){
                            popUpTo("edit_profile"){ inclusive = true }
                        }

                    }
                }
                else {
                    println("Response code: ${response.code()}")
                    println("Error body: ${response.errorBody()?.string()}")

                    val errorJson = response.errorBody()?.string() // Parse error body as string
                    try {
                        val gson = com.google.gson.Gson()
                        val errorResponse = gson.fromJson(errorJson, LoginResponse::class.java)

                    } catch (e: Exception) {

                    }
                }
            }
            override fun onFailure(call: Call<InfoResponse>, t: Throwable) {
            }
        })


    }
    // Function to save new address

    fun saveNewAddress() {
        val newAddress = Address(
            title = when (selectedOption) {
                1 -> "HOME"
                2 -> "WORK"
                else -> "OTHER"
            },
            street = street,
            city = address,
            postalCode = postCode
        )
        addressList = addressList + newAddress

        // Update SharedPreferences
        val updatedAddressesJson = gson.toJson(addressList)
        with(sharedPreferences.edit()) {
            putString("user_addresses", updatedAddressesJson)
            apply()
        }

        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("user_name", "No Name")
        val userEmail = sharedPreferences.getString("user_email", "No Email")
        val userPhone = sharedPreferences.getString("user_phoneNumber", "No phone number")
        val userBio = sharedPreferences.getString("user_bio", "Write your bio")
        val userId = sharedPreferences.getString("user_id", "Write your bio")

        // Call your updateUserInfo function here to update the backend
        val updatedRequest = UpdateRequest(
            id =userId,
            name = userName,
            email = userEmail,
            profilePicture = "",
            addresses = addressList,
            phoneNumber =userPhone,
            bio =userBio
        )

        // Send updated request to API
        updateUserInfo(context, navController, updatedRequest)

        // Navigate back after updating
        navController.navigate("settings") {
            popUpTo("edit_profile") { inclusive = true }
        }
    }
    Column(modifier = Modifier
        .padding(vertical = 15.dp, horizontal = 6.dp)
        .fillMaxSize()
    ) {
        // Back Row
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_back_blk),
                contentDescription = "Default Profile Image",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        navController.navigate("adresses")
                    },
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "My Address", fontSize = 20.sp)
        }

        // Form to input address
        Column(modifier = Modifier.fillMaxWidth().padding(10.dp), verticalArrangement = Arrangement.Top) {
            CustomTextField(
                value = address,
                onValueChange = { address = it },
                label = "CITY",
                placeholder = "Please enter the city",
            )
            Spacer(modifier = Modifier.height(20.dp))

            CustomTextField(
                value = street,
                onValueChange = { street = it },
                label = "STREET",
                placeholder = "Please enter the street",
            )

            Spacer(modifier = Modifier.height(20.dp))
            CustomTextField(
                value = postCode,
                onValueChange = { postCode = it },
                label = "POST CODE",
                placeholder = "Please enter the postal code",
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Label selection for the address
            Text("LABEL AS", fontSize = 18.sp, style = MaterialTheme.typography.h5, modifier = Modifier.padding(bottom = 5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (option in 1..3) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 5.dp)
                            .height(40.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                if (option == selectedOption) Color(0xFFF58D1D) else Color(0xFFF0F5FA)
                            )
                            .clickable {
                                selectedOption = option
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = when (option) {
                                2 -> "WORK"
                                1 -> "HOME"
                                else -> "OTHER"
                            },
                            color = if (option == selectedOption) Color.White else Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Save button
            OrangeButton("Save location", onClick = { saveNewAddress() })
        }
    }
}



