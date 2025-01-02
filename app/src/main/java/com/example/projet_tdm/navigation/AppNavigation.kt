package com.example.projet_tdm.navigation



import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projet_tdm.screens.splash.SplashScreen
import com.example.projet_tdm.screens.onboarding.OnboardingScreen
import com.example.projet_tdm.screens.auth.LoginScreen
import com.example.projet_tdm.screens.auth.SignUpScreen
import com.example.projet_tdm.screens.auth.ForgotPasswordScreen
import com.example.projet_tdm.screens.auth.OtpScreen
import com.example.projet_tdm.screens.home.HomeScreen
import com.example.projet_tdm.screens.profile.UploadProfileScreen
import com.example.projet_tdm.screens.location.LocationScreen
import com.example.projet_tdm.screens.profile.Adresses
import com.example.projet_tdm.screens.profile.ProfilePage
import com.example.projet_tdm.screens.settings.Setting

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("onboarding") { OnboardingScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("forgot_password") { ForgotPasswordScreen(navController) }
        composable("otp") { OtpScreen(navController) }
        composable("signup") { SignUpScreen(navController) }
        composable("upload_profile") { UploadProfileScreen(navController) }
        composable("location") { LocationScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("edit_profile") { ProfilePage(navController) }
        composable("settings") { Setting(navController) }
        composable("adresses") { Adresses() }

    }
}