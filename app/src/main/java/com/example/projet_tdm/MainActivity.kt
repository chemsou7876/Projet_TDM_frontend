package com.example.projet_tdm
// app/src/main/java/com/yourpackage/MainActivity.kt


import Profile
import android.os.Bundle
import android.provider.ContactsContract.Profile
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.projet_tdm.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                AppNavigation()


        }
    }
}