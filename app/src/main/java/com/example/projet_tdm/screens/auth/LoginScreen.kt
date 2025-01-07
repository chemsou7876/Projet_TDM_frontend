package com.example.projet_tdm.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
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
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }

    val orangebg = Color(0xFFFF7622)
    val orangeColor = Color(0x86FF7622)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(orangebg),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Container with transparent background
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Log In",
                fontSize = 30.sp,
                fontFamily = Sen,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(top = 100.dp)
            )

            Text(
                text = "Please sign in to your existing account",
                fontSize = 14.sp,
                fontFamily = Sen,
                fontWeight = FontWeight.W400,
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp)
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
            // Email Field
            Text(
                text = "EMAIL",
                fontFamily = Sen,
                fontWeight = FontWeight.W400,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                color = Color(0xFF32343E),
                fontSize = 12.sp
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = {
                    Text("Please enter your Email", color = Color(0xFFA0A5BA),fontWeight = FontWeight.W400, fontFamily = Sen,
                ) },
                modifier = Modifier
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
                fontWeight = FontWeight.W400,
                fontFamily = Sen,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                color = Color(0xFF32343E),
                fontSize = 12.sp
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = {
                    Text("* * * * * * * * * ", color = Color(0xFFA0A5BA),fontWeight = FontWeight.W400, fontFamily = Sen,
                    ) },                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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
                    .background(Color(0xFFF0F5FA)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = orangeColor,
                    unfocusedBorderColor = Color(0x00F0F5FA),

                    )
            )

            // Remember me and Forgot Password row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = rememberMe,
                        onCheckedChange = { rememberMe = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFFFFA500), // Orange color
                            uncheckedColor = Color(0xFFE3EBF2)
                        )
                    )
                    Text("Remember me", fontSize = 14.sp, color = Color(0xFF7E8A97), fontWeight = FontWeight.W400,fontFamily = Sen)
                }

                TextButton(onClick = { navController.navigate("forgot_password") }) {
                    Text(
                        "Forgot Password",
                        color = orangebg,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily =  Sen,
                    )
                }
            }

            // Login Button
            Button(
                onClick = { navController.navigate("home") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = orangebg),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("LOG IN", color = Color.White, fontWeight = FontWeight.Bold, fontFamily = Sen)
            }

            // Sign up text and link
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Don't have an account? ",fontSize = 16.sp, color = Color(0xFF646982), fontWeight = FontWeight.W400,fontFamily = Sen)
                TextButton(onClick = { navController.navigate("signup") }) {
                    Text(
                        "SIGN UP",
                        color = orangeColor,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Sen,
                        fontSize = 16.sp
                    )
                }
            }

            Text(
                "Or",
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 16.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                fontFamily = Sen
            )

            // Social login buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SocialLoginButton(
                    icon = R.drawable.ic_facebook,
                    onClick = { /* Facebook login */ }
                )
                SocialLoginButton(
                    icon = R.drawable.ic_twitter,
                    onClick = { /* Twitter login */ }
                )

            }
        }
    }
}

@Composable
fun SocialLoginButton(
    icon: Int,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF5F5F5))
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "Social login",
            modifier = Modifier.size(24.dp)
        )
    }
}