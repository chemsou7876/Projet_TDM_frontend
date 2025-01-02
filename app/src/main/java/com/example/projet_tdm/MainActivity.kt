package com.example.projet_tdm
// app/src/main/java/com/yourpackage/MainActivity.kt


import Profile
import android.os.Bundle
import android.provider.ContactsContract.Profile
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.projet_tdm.navigation.AppNavigation
import com.example.projet_tdm.screens.auth.SignUpScreen
import com.example.projet_tdm.screens.profile.ProfilePage
import com.example.projet_tdm.screens.settings.Setting

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

               //AppNavigation()
            val navController = rememberNavController()
             Setting(navController)




        }
    }
}