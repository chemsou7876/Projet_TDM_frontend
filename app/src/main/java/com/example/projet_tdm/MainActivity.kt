package com.example.projet_tdm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.projet_tdm.ui.theme.Review.MyReview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Appel de MyReview avec le paramètre pour démarrer le suivi du temps
            // Par exemple, afficher le pop-up quand on appuie sur un bouton
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        MyReview(this@MainActivity).show()  // Appeler MyReview pour afficher le pop-up
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Show Feedback Popup")
                }
            }
        }
    }
}
