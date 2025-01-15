package com.example.projet_tdm.navigation



import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projet_tdm.models.getData
import com.example.projet_tdm.screens.Cart.CartScreen
import com.example.projet_tdm.screens.MenuView.MenuView
import com.example.projet_tdm.screens.RestaurantView.RestaurantDetailsScreen
import com.example.projet_tdm.screens.RestaurantView.RestaurantViewInfos
import com.example.projet_tdm.screens.splash.SplashScreen
import com.example.projet_tdm.screens.onboarding.OnboardingScreen
import com.example.projet_tdm.screens.auth.LoginScreen
import com.example.projet_tdm.screens.auth.SignUpScreen
import com.example.projet_tdm.screens.auth.ForgotPasswordScreen
import com.example.projet_tdm.screens.auth.OtpScreen
import com.example.projet_tdm.screens.categories.CategoryScreen
import com.example.projet_tdm.screens.home.HomeScreen
import com.example.projet_tdm.screens.home.tabs.ProfilTab
import com.example.projet_tdm.screens.home.tabs.TrackTab
import com.example.projet_tdm.screens.profile.UploadProfileScreen
import com.example.projet_tdm.screens.location.LocationScreen
import com.example.projet_tdm.screens.profile.Adresses
import com.example.projet_tdm.screens.profile.Edit_adresses
import com.example.projet_tdm.screens.profile.ProfilePage
import com.example.projet_tdm.screens.search.SearchCategorieView
import com.example.projet_tdm.screens.search.SearchView
import com.example.projet_tdm.screens.settings.Setting

@Composable
fun AppNavigation(deepLinkUri: Uri? = null) {
    val navController = rememberNavController()


    // Handle deep link
    LaunchedEffect(deepLinkUri) {
        deepLinkUri?.let { uri ->
            if (uri.host == "auth" && uri.path == "/google/callback") {
                navController.navigate("home/0") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
    }

    // Your existing NavHost with all routes remains exactly the same
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("onboarding") { OnboardingScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("forgot_password") { ForgotPasswordScreen(navController) }
        composable("otp") { OtpScreen(navController) }
        composable("signup") { SignUpScreen(navController) }
        composable("upload_profile") { UploadProfileScreen(navController) }
        composable("location") { LocationScreen(navController) }
        composable("cart") { CartScreen(navController) }
        composable("edit_profile") { ProfilePage(navController) }
        composable("profile") { ProfilTab(navController) }
        composable("settings") { Setting(navController) }
        composable("adresses") { Adresses(navController) }
        composable("edit_adresses") { Edit_adresses(navController) }
        composable("categorie") { SearchCategorieView(navController) }
        composable("tracking_screen") { TrackTab(
            startTrackingTime = true,
        ) }
        composable("home/{tabIndex}") { backStackEntry ->
            val tabIndex = backStackEntry.arguments?.getString("tabIndex")?.toIntOrNull() ?: 0
            HomeScreen(navController, selectTab = tabIndex)

        }

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
        composable("searchView"){ SearchView() }
        composable(
            route = "category/{categoryName}",
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            CategoryScreen(navController = navController, category = categoryName)
        }
    }
}