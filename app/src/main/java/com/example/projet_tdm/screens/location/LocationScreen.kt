package com.example.projet_tdm.screens.location

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projet_tdm.R

@Composable
fun LocationScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
//        Image(
//            painter = painterResource(id = R.drawable.location_illustration),
//            contentDescription = "Location",
//            modifier = Modifier
//                .size(200.dp)
//                .padding(bottom = 32.dp)
//        )
        Text("nrmkm hna 'image")
        Text(
            text = "Enable Location",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "We need your location to show you nearby restaurants and deliver your food",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = {
                // Request location permission
                navController.navigate("home") {
                    popUpTo("splash") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Access Location")
        }
    }
}