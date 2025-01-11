package com.example.projet_tdm.screens.auth

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projet_tdm.R
import com.example.projet_tdm.components.CustomTextField
import com.example.projet_tdm.components.PasswordField
import com.example.projet_tdm.services.ApiClient
import com.example.projet_tdm.services.ApiInfoClient
import com.example.projet_tdm.services.InfoRequest
import com.example.projet_tdm.services.InfoResponse
import com.example.projet_tdm.services.LoginRequest
import com.example.projet_tdm.services.LoginResponse
import com.example.projet_tdm.services.UserSession
import com.example.projet_tdm.ui.theme.Sen
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private fun <T> Call<T>.enqueue(callback: Callback<T>, function: () -> Unit) {

}

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    var loginMessage by remember { mutableStateOf("") }
    var infoMessage by remember { mutableStateOf("") }
    var isLoginInProgress by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var showError by remember { mutableStateOf(false) }
    val orangebg = Color(0xFFFF7622)

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
    fun getInfo(context : Context , id:String?){
        val infoService = ApiInfoClient.authService
        val request = InfoRequest(id = id)
        println("request")
        println(request)
        infoService.getInfo(request).enqueue(object: Callback<InfoResponse>{
            override fun onResponse(call: Call<InfoResponse>, response: Response<InfoResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    println(result)
                    if (result?.user != null) {
                        val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()

                        editor.putBoolean("is_logged_in", true)
                        editor.putString("user_id", id)
                        editor.putString("user_name", result.user.name)
                        editor.putString("user_email", result.user.email)
                        editor.putString("user_phoneNumber", result.user.phoneNumber)
                        editor.putString("user_profilePicture", result.user.profilePicture)
                        editor.putString("user_bio", result.user.bio)
                        val gson = Gson()
                        val addressesJson = gson.toJson(result.user.addresses) // user.addresses is a List<Address>
                        editor.putString("user_addresses", addressesJson)
                        editor.apply()

                    } else {
                        infoMessage = "Login Failed: ${result?.message}"
                    }
                } else {
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

        println(infoMessage)
    }
    fun login(context: Context) {
        val authService = ApiClient.authService
        val request = LoginRequest(email = email, password = password)

        emailError = null
        passwordError = null
        showError = false

        authService.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()

                    if (result?.user != null) {
                        UserSession.userId = result.user.id
                        UserSession.isLoggedIn = true
                        loginMessage = "Login Successful!"

                        getInfo(context ,result.user.id )
                        println("it worked ")
                        println(UserSession.userId)
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        loginMessage = "Login Failed: ${result?.message}"
                    }
                } else {
                    val errorJson = response.errorBody()?.string() // Parse error body as string
                    try {
                        val gson = com.google.gson.Gson()
                        val errorResponse = gson.fromJson(errorJson, LoginResponse::class.java)
                        loginMessage = "Error: ${errorResponse.message}"
                    } catch (e: Exception) {
                        loginMessage = "Login Failed: ${response.message()}"
                    } finally {
                        if (loginMessage == "Error: Invalid password") {
                            passwordError = loginMessage
                        }
                        if (loginMessage == "Error: Email not found") {
                            emailError = loginMessage
                        }
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                println("Network error: ${t.message}")
                loginMessage = "Network error: ${t.message}"
            }
        })
        isLoginInProgress = false
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
                    println("herreeeeee")
                    val isEmailValid = validateEmail()
                    val isPasswordValid = validatePassword()

                    if (isEmailValid && isPasswordValid) {
                        isLoginInProgress = true
                        login(context)

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
                Text(
                    text = if (isLoginInProgress) "Logging In..." else "LOG IN",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Sen
                )
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





