package com.example.projet_tdm
// app/src/main/java/com/yourpackage/MainActivity.kt


import Profile
import android.os.Bundle
import android.provider.ContactsContract.Profile
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.projet_tdm.models.Notification
import com.example.projet_tdm.navigation.AppNavigation
import com.example.projet_tdm.screens.auth.SignUpScreen
import com.example.projet_tdm.screens.home.tabs.NotificationsTab
import com.example.projet_tdm.screens.profile.Adresses
import com.example.projet_tdm.screens.profile.Edit_adresses
import com.example.projet_tdm.screens.profile.ProfilePage
import com.example.projet_tdm.screens.settings.Setting

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            //AppNavigation()


           NotificationsTab()
        }
    }
}