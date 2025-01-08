package com.example.projet_tdm.screens.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.projet_tdm.R
import com.example.projet_tdm.ui.theme.Sen

@Composable
fun UploadProfileScreen(navController: NavController) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    val orangeColor = Color(0xFFFF7622)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(orangeColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Back button and header
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

        // Header text
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
                text = "Sign Up",
                fontSize = 30.sp,
                fontWeight = FontWeight.W800,
                color = Color.White,
                fontFamily = Sen,
            )

            Text(
                text = "Please sign up to get started",
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp),
                fontFamily = Sen,
                fontWeight = FontWeight.W400
            )
        }

        // White Container for the rest of the content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Let's put a face to your name!",
                fontSize = 18.sp,
                color = Color(0xFF363946),
                modifier = Modifier.padding(top = 8.dp , bottom = 50.dp),
                fontWeight = FontWeight.Bold,
                fontFamily = Sen,
            )
            // Profile Picture Upload Box
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(Color(0x23FF7622))
                    .clickable { launcher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = "Profile Picture",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Upload Picture",
                            modifier = Modifier.size(64.dp),
                            tint = Color(0xFFFF7622)
                        )
                        Text(
                            text = "Tap to upload",
                            color = Color(0xFFFF7622),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            Text(
                    text = "Upload your photo or skip this step by clicking 'Sign Up'",
            fontSize = 18.sp,
            color = Color(0xFF646982),
                textAlign = TextAlign.Center, // Ensure each line of text is center-aligned

            modifier = Modifier.padding(top = 25.dp ),
                fontWeight = FontWeight.W400,
                fontFamily = Sen,
            )
            Spacer(modifier = Modifier.weight(1f))

            // Next Button
            Button(
                onClick = { navController.navigate("location") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = orangeColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "SIGN UP",
                    color = Color.White,
                    fontWeight = FontWeight.W800,
                    fontFamily = Sen
                )
            }
        }
    }
}