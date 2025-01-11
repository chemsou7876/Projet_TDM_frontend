package com.example.projet_tdm.screens.auth

import android.content.Context
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projet_tdm.R
import com.example.projet_tdm.components.CustomTextField
import com.example.projet_tdm.components.PasswordField
import com.example.projet_tdm.services.ApiClient
import com.example.projet_tdm.services.ApiNewClient
import com.example.projet_tdm.services.LoginRequest
import com.example.projet_tdm.services.LoginResponse
import com.example.projet_tdm.services.SignupRequest
import com.example.projet_tdm.services.SignupResponse
import com.example.projet_tdm.services.UserSession
import com.example.projet_tdm.ui.theme.Sen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SignUpScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var loginMessage by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }


    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current


    val orangeColor = Color(0xFFFF7622)

    // Validation functions
    fun validateName(): Boolean {
        return when {
            name.isEmpty() -> {
                nameError = "Name is required"
                false
            }
            name.length < 2 -> {
                nameError = "Name must be at least 2 characters"
                false
            }
            else -> {
                nameError = null
                true
            }
        }
    }
    fun validateEmail(): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return when {
            email.isEmpty() -> {
                emailError = "Email is required"
                false
            }
            !email.matches(emailPattern.toRegex()) -> {
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
            password.length < 8 -> {
                passwordError = "Password must be at least 8 characters"
                false
            }
            !password.any { it.isDigit() } -> {
                passwordError = "Password must contain at least one number"
                false
            }
            !password.any { it.isUpperCase() } -> {
                passwordError = "Password must contain at least one uppercase letter"
                false
            }
            !password.any { it.isLowerCase() } -> {
                passwordError = "Password must contain at least one lowercase letter"
                false
            }
            else -> {
                passwordError = null
                true
            }
        }
    }
    fun validateConfirmPassword(): Boolean {
        return when {
            confirmPassword.isEmpty() -> {
                confirmPasswordError = "Please confirm your password"
                false
            }
            confirmPassword != password -> {
                confirmPasswordError = "Passwords do not match"
                false
            }
            else -> {
                confirmPasswordError = null
                true
            }
        }
    }
    fun validateForm(): Boolean {
//        val isNameValid = validateName()
//        val isEmailValid = validateEmail()
//        val isPasswordValid = validatePassword()
//        val isConfirmPasswordValid = validateConfirmPassword()
//
//        return isNameValid && isEmailValid && isPasswordValid && isConfirmPasswordValid
        return true
    }
   fun signup(context: Context) {
        val signupService = ApiNewClient.authService
        val request = SignupRequest(email = email, name = name ,password = password ,confirmPassword= confirmPassword )
        signupService.signup(request).enqueue((object : Callback<SignupResponse> {
            override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()

                    if (result?.message == "User created successfully") {
                        UserSession.userId = result.id
                        UserSession.isLoggedIn = true
                        loginMessage = "Login Successful!"

                        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()

                        editor.putBoolean("is_logged_in", true)
                        editor.putString("user_id", result.id)
                        editor.apply()

                        navController.navigate("upload_profile") {
                            popUpTo("signup") { inclusive = true }
                        }
                    } else {
                        loginMessage = "Signup Failed: ${result?.message}"
                    }
                } else {
                    val errorJson = response.errorBody()?.string() // Parse error body as string
                    try {
                        val gson = com.google.gson.Gson()
                        val errorResponse = gson.fromJson(errorJson, LoginResponse::class.java)
                        loginMessage = "Error: ${errorResponse.message}"
                    } catch (e: Exception) {
                        loginMessage = "Sign Up Failed: ${response.message()}"
                    }
                }
            }
            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                loginMessage = "Network error: ${t.message}"
            }

        }))
    }


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
                .fillMaxHeight(0.15f)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFFF7622), Color(0xFFFF7622))
                    )
                )
                .paint(
                    painterResource(id = R.drawable.auth_bg),
                    contentScale = ContentScale.Crop
                ),

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
            CustomTextField(
                value = name,
                onValueChange = { name = it },
                label = "NAME",
                placeholder = "Please enter your name",
                error = nameError,
                modifier = Modifier.fillMaxWidth()
            )

            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = "EMAIL",
                placeholder = "Please enter your Email",
                isEmail = true,
                error = emailError,
                modifier = Modifier.fillMaxWidth()
            )

            PasswordField(
                password = password,
                label = "PASSWORD",
                onPasswordChange = { password = it },
                passwordError = passwordError,
                passwordVisible = passwordVisible,
                onPasswordVisibilityChange = { passwordVisible = it },
                modifier = Modifier.fillMaxWidth()
            )

            PasswordField(
                password = confirmPassword,
                label = "Re-Type PASSWORD",
                onPasswordChange = { confirmPassword = it },
                passwordError = confirmPasswordError,
                passwordVisible = confirmPasswordVisible,
                onPasswordVisibilityChange = { confirmPasswordVisible = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )
            Spacer(modifier = Modifier.weight(1f))

            // Sign Up Button
            Button(
                onClick = {  if (validateForm()) {
                    signup(context)

                } },
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
