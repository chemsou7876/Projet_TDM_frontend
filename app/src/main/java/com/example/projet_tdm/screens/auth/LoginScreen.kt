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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projet_tdm.R
import com.example.projet_tdm.components.CustomTextField
import com.example.projet_tdm.components.PasswordField
import com.example.projet_tdm.ui.theme.Sen

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }

    // Error states
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var showError by remember { mutableStateOf(false) }

    val orangebg = Color(0xFFFF7622)
    val orangeColor = Color(0x86FF7622)
    val errorColor = Color(0xFFB00020)
    val borderColor = Color(0xFFE3EBF2)
    // Validation functions
    fun validateEmail(): Boolean {
        return when {
            email.isEmpty() -> {
                emailError = "Email is required"
                false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                emailError = "Invalid email format"
                false
            }
            else -> {
                emailError = null
                true
            }
        }
    }

    fun validatePassword(): Boolean {
        return when {
            password.isEmpty() -> {
                passwordError = "Password is required"
                false
            }
            password.length < 6 -> {
                passwordError = "Password must be at least 6 characters"
                false
            }
            else -> {
                passwordError = null
                true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(orangebg, orangebg)
                )
            )
            .paint(
                painterResource(id = R.drawable.auth_bg),
                contentScale = ContentScale.Crop
            ),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header section remains the same
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

        // White Container
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
            CustomTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = null
                },
                label = "EMAIL",
                placeholder = "Please enter your Email",
                isEmail = true,
                error = emailError
            )

            // Password Field
            PasswordField(
                password = password,
                label = "PASSWORD",
                onPasswordChange = {
                    password = it
                    passwordError = null
                    showError = false
                },
                passwordError = passwordError,
                passwordVisible = passwordVisible,
                onPasswordVisibilityChange = { passwordVisible = it }
            )

            // Remember me and Forgot Password row (remains the same)
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
                            checkedColor = Color(0xFFFFA500),
                            uncheckedColor = Color(0xFFE3EBF2)
                        )
                    )
                    Text("Remember me",
                        fontSize = 14.sp,
                        color = Color(0xFF7E8A97),
                        fontWeight = FontWeight.W400,
                        fontFamily = Sen)
                }

                TextButton(onClick = { navController.navigate("forgot_password") }) {
                    Text(
                        "Forgot Password",
                        color = orangebg,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        fontFamily = Sen,
                    )
                }
            }

            // General error message (shows when form submission fails)


            // Login Button with validation
            Button(
                onClick = {
                    val isEmailValid = validateEmail()
                    val isPasswordValid = validatePassword()

                    if (isEmailValid && isPasswordValid) {
                        navController.navigate("home")
                    } else {
                        showError = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = orangebg),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("LOG IN",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Sen)
            }

            // Rest of the UI remains the same...
            // Sign up text and social login buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Don't have an account? ",
                    fontSize = 16.sp,
                    color = Color(0xFF646982),
                    fontWeight = FontWeight.W400,
                    fontFamily = Sen)
                TextButton(onClick = { navController.navigate("signup") }) {
                    Text(
                        "SIGN UP",
                        color = orangebg,
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
                SocialLoginButton(
                    icon = R.drawable.google_logo,
                    onClick = { /* Google login */ }
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





