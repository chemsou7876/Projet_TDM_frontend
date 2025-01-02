package com.example.projet_tdm.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projet_tdm.R
import com.example.projet_tdm.ui.theme.Sen

@Composable
fun SignUpScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val orangeColor = Color(0xFFFF7622)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(orangeColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back button and header container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp)
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }
        // Header Container with transparent background
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sign Up",
                fontSize = 30.sp,
                fontWeight = FontWeight.W800,
                color = Color.White,
                fontFamily = Sen,

            )

            Text(
                text = "Please sign up to get started",
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp),
                fontFamily = Sen,
                fontWeight = FontWeight.W400
            )
        }

        // White Container for the rest of the content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Name Field
            Text(
                text = "NAME",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                color = Color(0xFF32343E),
                fontSize = 12.sp,
                fontFamily = Sen,
                fontWeight = FontWeight.W400
            )
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = {
                    Text("Please enter your name", color = Color(0xFFA0A5BA),fontWeight = FontWeight.W400, fontFamily = Sen,
                    ) },                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(Color(0xFFF0F5FA)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = orangeColor,
                    unfocusedBorderColor = Color(0x00F0F5FA),
                )
            )


            // Email Field
            Text(
                text = "EMAIL",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                color = Color(0xFF32343E),
                fontSize = 12.sp,
                fontFamily = Sen,
                fontWeight = FontWeight.W400
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = {
                    Text("Please enter your Email", color = Color(0xFFA0A5BA),fontWeight = FontWeight.W400, fontFamily = Sen,
                    ) },                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(Color(0xFFF0F5FA)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = orangeColor,
                    unfocusedBorderColor = Color(0x00F0F5FA),
                )
            )


            // Password Field
            Text(
                text = "PASSWORD",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                color = Color.Gray,
                fontSize = 12.sp,
                fontFamily = Sen,
                fontWeight = FontWeight.W400
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = {
                    Text("* * * * * * * * * * * *", color = Color(0xFFA0A5BA),fontWeight = FontWeight.W400, fontFamily = Sen,
                    ) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = "Toggle password visibility",
                            tint = Color(0xFFA0A5BA) // Optional: Set icon color

                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(10.dp))
                    .padding(bottom = 24.dp)
                    .background(Color(0xFFF0F5FA)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = orangeColor,
                    unfocusedBorderColor = Color(0x00F0F5FA),

                    )
            )

            // Confirm Password Field
            Text(
                    text = "RE-TYPE PASSWORD",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                color = Color.Gray,
                fontSize = 12.sp,
                fontFamily = Sen,
                fontWeight = FontWeight.W400
            )
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = {
                    Text("* * * * * * * * * * * *", color = Color(0xFFA0A5BA),fontWeight = FontWeight.W400, fontFamily = Sen,
                    ) },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = "Toggle password visibility",
                            tint = Color(0xFFA0A5BA) // Optional: Set icon color
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(Color(0xFFF0F5FA)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = orangeColor,
                    unfocusedBorderColor = Color(0x00F0F5FA),

                    )
            )
            Spacer(modifier = Modifier.weight(1f))

            // Sign Up Button
            Button(
                onClick = { navController.navigate("upload_profile") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = orangeColor),
                shape = RoundedCornerShape(12.dp)            ) {
                Text("NEXT", color = Color.White, fontWeight = FontWeight.W800, fontFamily = Sen)
            }

            



        }
    }
}
