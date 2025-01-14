package com.example.projet_tdm.screens.profile

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.projet_tdm.R
import com.example.projet_tdm.components.CustomTextField
import com.example.projet_tdm.components.OrangeButton
import com.example.projet_tdm.services.ApiInfoClient
import com.example.projet_tdm.services.InfoResponse
import com.example.projet_tdm.services.LoginResponse
import com.example.projet_tdm.services.UpdateRequest
import com.example.projet_tdm.ui.theme.Sen
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback

@Composable
fun ProfilePage(navController: NavController){
    var full_name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }
    var profileImage by remember { mutableStateOf<Uri?>(null) } // Store the profile image
    var showDialog by remember { mutableStateOf(false) } // Optional dialog state for confirming image change

    // Default image in case no image is selected
    val defaultProfileImage = painterResource(id = R.drawable.profile_pic)

    val onProfileImageClick: () -> Unit = {
        showDialog = true  // Just to represent an action
    }
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    val userId= sharedPreferences.getString("user_id", "null")
    val userName = sharedPreferences.getString("user_name", "No Name")
    val userEmail = sharedPreferences.getString("user_email", "No Email")
    val userPhone = sharedPreferences.getString("user_phoneNumber", "No phone number")
    val userBio = sharedPreferences.getString("user_bio", "Write your bio")
    val userProfile = sharedPreferences.getString("user_profilePicture", "profile pic")

    var infoMessage by remember { mutableStateOf("") }


    fun updateUserInfo(context : Context ,navController: NavController, updatedInfo: UpdateRequest){
        val infoService = ApiInfoClient.authService
        val request = UpdateRequest(updatedInfo.id , updatedInfo.name , updatedInfo.email , updatedInfo.profilePicture ,
            updatedInfo.addresses , updatedInfo.phoneNumber , updatedInfo.bio)
       infoService.updateInfo(request).enqueue(object: Callback<InfoResponse>{
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
                       navController.navigate("profile"){
                           popUpTo("edit_profile"){ inclusive = true }
                       }

                   } else {
                       infoMessage = "Login Failed: ${result?.message}"
                   }
               }
               else {
                   println("Response code: ${response.code()}")
                   println("Error body: ${response.errorBody()?.string()}")

                   val errorJson = response.errorBody()?.string() // Parse error body as string
                   try {
                       val gson = com.google.gson.Gson()
                       val errorResponse = gson.fromJson(errorJson, LoginResponse::class.java)
                       infoMessage = "Error: ${errorResponse.message}"
                   } catch (e: Exception) {
                       infoMessage = "fetching info Failed: ${response.message()}"
                   }
               }
           }
           override fun onFailure(call: Call<InfoResponse>, t: Throwable) {
               infoMessage = "Network error: ${t.message}"
           }
       })
    }




    Column(modifier = Modifier.padding((8.dp)).fillMaxSize(),

         verticalArrangement = Arrangement.SpaceBetween
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp)
                    .background(Color.White, CircleShape)
                    .clickable(interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }, indication = null) { navController.navigateUp() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(text ="Edit profile",
                fontSize = 20.sp, fontFamily = Sen, )

        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            // verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Profile Image
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clickable(onClick = onProfileImageClick) // Clickable area to edit image
            ) {
                if (profileImage != null) {
                    // Show the selected profile image if available
                    Image(
                        painter = rememberImagePainter(profileImage),
                        contentDescription = "Profile Image",

                        modifier = Modifier.fillMaxSize().clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Default image if none selected
                    Image(
                        painter = defaultProfileImage,
                        contentDescription = "Default Profile Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "Default Profile Image",
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .size(40.dp)
                        .clickable { showDialog = true  },
                    contentScale = ContentScale.Crop
                )


            }

            Column {
                Column (modifier = Modifier.padding(vertical = 8.dp)) {
                    if (userName != null) {
                        CustomTextField(
                            value = full_name,
                            onValueChange = {
                                full_name = it
                                // emailError = null
                            },
                            label = "FULL NAME",
                            placeholder = userName,
                            //  isEmail = true,
                            //error = emailError
                        )
                    }
                }
                Column (modifier = Modifier.padding(vertical = 8.dp)) {

                    if (userEmail != null) {
                        CustomTextField(
                            value = email,
                            onValueChange = {
                              //  email = it
                                // emailError = null
                            },
                            label = "EMAIL",
                            placeholder = userEmail,
                            //  isEmail = true,
                            //error = emailError
                        )
                    }

                }
                Column (modifier = Modifier.padding(vertical = 8.dp)) {


                    if (userPhone != null) {
                        CustomTextField(
                            value = phone,
                            onValueChange = {
                                phone = it
                                // emailError = null
                            },
                            label = "Phone Number",

                            placeholder =userPhone,
                            //  isEmail = true,
                            //error = emailError
                        )
                    }
                }
                Column (modifier = Modifier.padding(vertical = 8.dp)) {


                    if (userBio != null) {
                        CustomTextField(
                            value = bio,
                            onValueChange = {
                                bio = it
                                // emailError = null
                            },
                            label = "BIO",
                            placeholder = userBio,
                            //  isEmail = true,
                            //error = emailError
                        )
                    }
                }

            }
           OrangeButton("SAVE", onClick = {
               if (userId != null) {

                   val updatedInfo = UpdateRequest(
                       id = userId,
                       name = full_name,
                       email = null,
                       profilePicture = null,
                       addresses = null,
                       phoneNumber = phone,
                       bio = bio
                   )
                   updateUserInfo(context, navController,updatedInfo)
               }
           })

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent(),
                onResult = { uri: Uri? ->
                    profileImage = uri // Store selected image URI
                }
            )
            // Dialog to simulate the image selection
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Pick an image") },
                    text = { Text("You can choose an image from the gallery or camera") },
                    confirmButton = {
                        Button(onClick = {
                            launcher.launch("image/*") // Open image picker for any image type
                            showDialog = false
                        },
                            colors = ButtonDefaults.textButtonColors(Color(0xFFFF7622))) {
                            Text("Select Image" , color = Color.White)
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDialog = false },
                            colors = ButtonDefaults.textButtonColors(Color(0xFFFF7622))) {
                            Text("Cancel", color = Color.White)
                        }
                    }
                )
            }
        }


    }
    }

