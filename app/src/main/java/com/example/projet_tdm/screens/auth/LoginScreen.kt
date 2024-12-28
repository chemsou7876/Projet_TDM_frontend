package com.example.projet_tdm.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projet_tdm.R
import com.example.projet_tdm.components.SocialButton

@Composable
fun LoginScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        TextButton(
            onClick = { navController.navigate("forgot_password") }
        ) {
            Text("Forgot Password?")
        }

        Button(
            onClick = { /* Login Logic */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Login")
        }

        Row(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
//            SocialButton(icon = R.drawable.ic_google, onClick = { /* Google Login */ })
//            SocialButton(icon = R.drawable.ic_facebook, onClick = { /* Facebook Login */ })
//            SocialButton(icon = R.drawable.ic_twitter, onClick = { /* Twitter Login */ })
            Text("here social buttons")
        }

        TextButton(
            onClick = { navController.navigate("signup") }
        ) {
            Text("Don't have an account? Sign Up")
        }
    }
}
