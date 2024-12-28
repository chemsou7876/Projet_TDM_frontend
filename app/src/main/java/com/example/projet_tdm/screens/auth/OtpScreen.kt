package com.example.projet_tdm.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController

@Composable
fun OtpScreen(navController: NavController) {
    var otpValues by remember { mutableStateOf(List(4) { "" }) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Enter OTP",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            otpValues.forEachIndexed { index, value ->
                OutlinedTextField(
                    value = value,
                    onValueChange = { newValue ->
                        if (newValue.length <= 1) {
                            val newList = otpValues.toMutableList()
                            newList[index] = newValue
                            otpValues = newList
                        }
                    },
                    modifier = Modifier.width(64.dp),
                    textStyle = TextStyle(textAlign = TextAlign.Center),
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }

        Button(
            onClick = { /* Verify OTP Logic */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text("Verify")
        }
    }
}