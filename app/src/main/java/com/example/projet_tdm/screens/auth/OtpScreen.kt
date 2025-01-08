package com.example.projet_tdm.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projet_tdm.R
import com.example.projet_tdm.ui.theme.Sen
import kotlinx.coroutines.delay

@Composable
fun OtpScreen(navController: NavController) {
    var otpValues by remember { mutableStateOf(List(4) { "" }) }
    var timeLeft by remember { mutableStateOf(50) }
    var isResendEnabled by remember { mutableStateOf(false) }
    val orangeColor = Color(0xFFFF7622)

    // Timer effect
    LaunchedEffect(key1 = Unit) {
        while (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
        isResendEnabled = true
    }

    // Focus requesters for OTP fields
    val focusRequesters = remember {
        List(4) { FocusRequester() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(orangeColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back button and header sections remain the same...
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp)
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15f)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFFF7622), Color(0xFFFF7622))
                    )
                )
                .paint(
                    painterResource(id = R.drawable.auth_bg),
                    contentScale = ContentScale.Crop
                ),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Verification",
                fontSize = 30.sp,
                fontWeight = FontWeight.W800,
                color = Color.White,
                fontFamily = Sen,
            )

            Text(
                text = "We have sent a code to your email",
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp),
                fontWeight = FontWeight.W400,
                fontFamily = Sen
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "CODE",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = Sen,
                    color = Color(0xFF32343E)
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = {
                            if (isResendEnabled) {
                                timeLeft = 50
                                isResendEnabled = false
                                // Add your resend logic here
                            }
                        },
                        enabled = isResendEnabled
                    ) {
                        Text(
                            "Resend",
                            color = if (isResendEnabled) Color(0xFF32343E) else Color.Gray,
                            fontWeight = if (isResendEnabled) FontWeight.ExtraBold else FontWeight.Normal,
                            style = TextStyle(
                                textDecoration = if (isResendEnabled) TextDecoration.Underline else TextDecoration.None
                            )
                        )
                    }
                    Text(
                        "in ${timeLeft}s",
                        color = Color(0xFF32343E),
                        fontWeight = FontWeight.W400
                    )
                }
            }


            // OTP Input Fields
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                otpValues.forEachIndexed { index, value ->
                    OutlinedTextField(
                        value = value,
                        onValueChange = { newValue ->
                            if (newValue.length <= 1) {
                                val newList = otpValues.toMutableList()
                                newList[index] = newValue
                                otpValues = newList

                                // Auto-focus next field
                                if (newValue.isNotEmpty() && index < 3) {
                                    focusRequesters[index + 1].requestFocus()
                                }
                            }
                        },
                        modifier = Modifier
                            .width(64.dp)
                            .height(64.dp)
                            .focusRequester(focusRequesters[index])
                            .clip(shape = RoundedCornerShape(10.dp))
                            .background(Color(0xFFF0F5FA)),
                        textStyle = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Sen
                        ),
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = orangeColor,
                            unfocusedBorderColor = Color(0x00F0F5FA),
                            backgroundColor = Color(0xFFF0F5FA)
                        ),
                        shape = CircleShape
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { /* Verify OTP Logic */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = orangeColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "VERIFY",
                    color = Color.White,
                    fontWeight = FontWeight.W800,
                    fontFamily = Sen
                )
            }
        }
    }
}