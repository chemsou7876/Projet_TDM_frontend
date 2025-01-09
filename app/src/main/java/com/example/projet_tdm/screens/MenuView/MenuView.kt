package com.example.projet_tdm.screens.MenuView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projet_tdm.models.Menu
import com.example.projet_tdm.models.Restaurant

@Composable
fun MenuView(navController: NavController, menu: Menu, restaurant: Restaurant) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box {
            Image(
                painter = painterResource(id = menu.imageUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(15.dp))
            )

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .size(40.dp)
                    .background(Color.White, CircleShape)
                    .clickable { navController.navigateUp() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Text(
            text = menu.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF303030)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = Color(0xFFFFA500)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                //text = "${menu.rating}",
                text = "4.5",
                fontSize = 16.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.width(25.dp))

            Text(
                text = "${menu.preparationTime} mins",
                fontSize = 15.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.width(25.dp))

            Text(
                text = "${restaurant.deliveryprice}",
                fontSize = 15.sp,
                color = Color.Black
            )
        }

        Text(
            text = menu.description,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            color = Color(0xFF505050)
        )
    }
}