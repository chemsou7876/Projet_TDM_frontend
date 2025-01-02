package com.example.projet_tdm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.projet_tdm.ui.theme.Projet_TDMTheme
import com.example.projet_tdm.ui.theme.Cart.MyCart


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Projet_TDMTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyCartScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MyCartScreen(modifier: Modifier = Modifier) {
    MyCart()
}
