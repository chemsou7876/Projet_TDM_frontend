package com.example.projet_tdm.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Colors
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.projet_tdm.R

@Composable
fun ProfilePage(){
    var full_name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var bio by remember { mutableStateOf("") }

    var profileImage by remember { mutableStateOf<Uri?>(null) } // Store the profile image
    var showDialog by remember { mutableStateOf(false) } // Optional dialog state for confirming image change

    // Default image in case no image is selected
    val defaultProfileImage = painterResource(id = R.drawable.profile_pic)

    val onProfileImageClick: () -> Unit = {
        // Logic to open the gallery or camera to select a new image
        showDialog = true  // Just to represent an action
    }
    //val scrollState = rememberScrollState()

    Column(modifier = Modifier.padding((8.dp)).fillMaxSize()
     //   .verticalScroll(scrollState) ,
        , verticalArrangement = Arrangement.SpaceBetween
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = { println("clicked !!") }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back_icon),
                    contentDescription = "Back icon"
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(text ="Edit profile",
                fontSize = 20.sp)

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

                IconButton(
                    onClick = onProfileImageClick,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit) ,
                        contentDescription = "Edit Profile"
                    )
                }

            }

            Column {
                Column (modifier = Modifier.padding(vertical = 8.dp)) {
                    Text("FULL NAME",
                        fontSize = 18.sp ,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(bottom = 5.dp))
                    OutlinedTextField(
                        value = full_name,
                        onValueChange = {full_name = it},
                        modifier = Modifier.fillMaxWidth()
                            .background(Color(0xFFF0F5FA)),

                        placeholder = { Text("Zaidi yasmine", color = Color(0xFF6B6E82)) },


                    )
                }
                Column (modifier = Modifier.padding(vertical = 8.dp)) {
                    Text("EMAIL",
                        fontSize = 18.sp ,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(bottom = 5.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = {email = it},
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("ly_zaidi@esi.dz") }
                    )
                }
                Column (modifier = Modifier.padding(vertical = 8.dp)) {
                    Text("Phone Number",
                        fontSize = 18.sp ,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(bottom = 5.dp))
                    OutlinedTextField(
                        value = phone,
                        onValueChange = {phone = it},
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("078 68 35 13") }
                    )
                }
                Column (modifier = Modifier.padding(vertical = 8.dp)) {
                    Text("BIO",
                        fontSize = 18.sp ,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(bottom = 5.dp))
                    OutlinedTextField(
                        value = bio,
                        onValueChange = {bio = it},
                        modifier = Modifier.fillMaxWidth().height(100.dp),
                        placeholder = { Text("HI EVERYONE ! ") }
                    )
                }

            }
            TextButton(onClick = {
            },
                modifier = Modifier.fillMaxWidth(),
                colors =  ButtonDefaults.textButtonColors(Color(0xFFFF7622))) {
                Text("Save", fontSize = 18.sp
                    , color = Color.White)
            }

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

