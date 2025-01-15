package com.example.projet_tdm.screens.splash



import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projet_tdm.R
import com.example.projet_tdm.services.UserSession
import kotlinx.coroutines.delay
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log
import androidx.compose.ui.graphics.Color

@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)

    LaunchedEffect(key1 = true) {
        delay(2000L)
        withContext(Dispatchers.Main) {
            try {
                if (isLoggedIn) {
                    navController.navigate("home/0") {
                        popUpTo("splash") { inclusive = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                } else {
                    navController.navigate("login") {
                        popUpTo("splash") { inclusive = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            } catch (e: Exception) {
                // Handle any navigation errors
                Log.e("SplashScreen", "Navigation error: ${e.message}")
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),  // Add a background color
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_bg),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(200.dp)
                .animateContentSize()  // Add animation to the logo
        )
    }
}