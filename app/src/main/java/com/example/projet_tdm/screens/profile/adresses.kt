package com.example.projet_tdm.screens.profile

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projet_tdm.R
import com.example.projet_tdm.components.Adresse_card
import com.example.projet_tdm.components.OrangeButton
import com.example.projet_tdm.services.Address
import com.example.projet_tdm.services.ApiInfoClient
import com.example.projet_tdm.services.InfoResponse
import com.example.projet_tdm.services.LoginResponse
import com.example.projet_tdm.services.UpdateRequest
import com.example.projet_tdm.services.UserSession
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback


@Composable
fun Adresses(navController: NavController){
    val context = LocalContext.current


    fun updateUserInfo(context : Context,  updatedInfo: UpdateRequest){

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

    fun getAddressesAsTriples(context: Context): List<Triple<String, String, Int>> {
        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val addressesJson = sharedPreferences.getString("user_addresses", null)

        if (addressesJson != null) {
            val gson = Gson()
            val type = object : TypeToken<List<Address>>() {}.type
            val addresses: List<Address> = gson.fromJson(addressesJson, type)

            return addresses.mapIndexed { index, address ->
                val title = address.title ?: "OTHER"
                val nb = when (title) {
                    "HOME" -> 1
                    "WORK" -> 2
                    else -> 3
                }
                val description = "${address.street}, ${address.city}, ${address.postalCode}"
                Triple(title, description, nb)
            }
        }
        return emptyList() // Default to an empty list if no addresses are found
    }


    var items by remember {
        mutableStateOf(getAddressesAsTriples(context))
    }
    //val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .padding(vertical = 15.dp , horizontal = 6.dp)
        .fillMaxSize()
        //   .verticalScroll(scrollState) ,
        , verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp)  // Use Column to hold everything inside it
        ) {
            // Top row with image and "My Addresses"
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.back_icon),
                    contentDescription = "Default Profile Image",
                    modifier = Modifier
                        .size(55.dp)
                        .clickable {
                            navController.navigate("settings") // navigate to the settings page
                        },
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text ="My Addresses", fontSize = 20.sp)
            }
            if(items.isEmpty()){
              Column (modifier = Modifier.fillMaxWidth().padding(30.dp) , horizontalAlignment = Alignment.CenterHorizontally){   Text("You Have no adresses yet ! ",
                  color = Color(0xFFFF7622) , fontWeight = FontWeight.W700 , fontSize = 15.sp) }
            }
            LazyColumn(
                modifier = Modifier.weight(1f)  // Use weight so LazyColumn fills remaining space in Column
            ) {

                items(items.size) { index -> // Loop through items
                    val item = items[index]
                    Adresse_card(
                        title = item.first,
                        desc = item.second,
                        type = item.third,
                        onEditClick = {
                            println("Edit clicked for ${item.first}")
                            navController.navigate("edit_adresses") // navigate to the edit addresses page
                        },
                        onDeleteClick ={
                                items = items.filterIndexed { i, _ -> i != index }
                                val updatedItems = items.filterIndexed { i, _ -> i != index }
                                val addressList: List<Address> = updatedItems.map { item ->
                                val addressParts = item.second.split(",")
                                    val street = addressParts.getOrNull(0)?.trim() ?: ""
                                val city = addressParts.getOrNull(1)?.trim() ?: ""
                                val postalCode = addressParts.getOrNull(2)?.trim() ?: ""

                                Address(
                                    street = street,
                                    city = city,
                                    postalCode = postalCode,
                                    title = if (item.first == "HOME") "Home" else if (item.first == "WORK") "Work" else "Other"
                                )
                            }

                              //Save the updated list of addresses in SharedPreferences
                                val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                val gson = Gson()
                                val updatedAddressesJson = gson.toJson(addressList)
                                editor.putString("user_addresses", updatedAddressesJson)
                                editor.apply()

                                // Prepare the updated InfoRequest with the new addresses list
                                val updatedInfo = UpdateRequest(
                                    id =  sharedPreferences.getString("user_id", "null"), // Replace with actual user ID
                                    name = sharedPreferences.getString("user_name", "No Name"),
                                    email = sharedPreferences.getString("user_email", "No Email"),
                                    profilePicture = sharedPreferences.getString("user_profilePicture", "profile pic"),
                                    addresses = addressList,  // Pass the updated addresses list
                                    phoneNumber = sharedPreferences.getString("user_phoneNumber", "No phone number"),
                                    bio = sharedPreferences.getString("user_bio", "Write your bio")
                                )
                                updateUserInfo(context, updatedInfo)
                        }

                    )
                }
            }

            // Add New Address Button
            OrangeButton(
                text = "Add new address",
                onClick = { navController.navigate("edit_adresses") }
            )
        }


    }

}


