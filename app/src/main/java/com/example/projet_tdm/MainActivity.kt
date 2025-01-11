package com.example.projet_tdm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.projet_tdm.screens.Cart.MyCart

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Affichage de l'interface "MyCart" avec Jetpack Compose
        setContent {
            // Appel de l'écran de panier MyCart
            MyCart()
        }
    }
}
