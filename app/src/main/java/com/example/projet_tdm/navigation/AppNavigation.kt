package com.example.projet_tdm.navigation



import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projet_tdm.models.getData
import com.example.projet_tdm.screens.MenuView.MenuView
import com.example.projet_tdm.screens.RestaurantView.RestaurantDetailsScreen
import com.example.projet_tdm.screens.RestaurantView.RestaurantViewInfos
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
import com.example.projet_tdm.screens.profile.Edit_adresses
import com.example.projet_tdm.screens.profile.ProfilePage
import com.example.projet_tdm.screens.settings.Setting

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
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
        composable("adresses") { Adresses(navController) }
        composable("edit_adresses") { Edit_adresses(navController) }
        composable("restaurantDetails/{restaurantId}") { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getString("restaurantId")?.toIntOrNull()
            val restaurant = getData().find { it.id == restaurantId }
            restaurant?.let { RestaurantDetailsScreen(navController,it) }
        }
        composable("restaurantViewInfos/{restaurantId}") { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getString("restaurantId")?.toIntOrNull()
            val restaurant = getData().find { it.id == restaurantId }
            restaurant?.let { RestaurantViewInfos(navController,it) }
        }
        composable("menuView/{menuId}/{restaurantId}") { backStackEntry ->
    val menuId = backStackEntry.arguments?.getString("menuId")?.toIntOrNull()
    val restaurantId = backStackEntry.arguments?.getString("restaurantId")?.toIntOrNull()
    val restaurant = getData().find { it.id == restaurantId }
    val menu = restaurant?.menus?.find { it.id == menuId }
    if (menu != null && restaurant != null) {
        MenuView(navController, menu, restaurant)
    }
}

    }
}