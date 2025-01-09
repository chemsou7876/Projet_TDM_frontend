package com.example.projet_tdm.screens.RestaurantView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Divider
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
import com.example.projet_tdm.R
import com.example.projet_tdm.models.Restaurant

@Composable
fun RestaurantViewInfos(navController: NavController, restaurant: Restaurant) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Box {
                // Image principale (keep your existing Image composable)
                Image(
                    painter = painterResource(id = restaurant.imgUrl),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(15.dp))
                )

                // Back arrow with circular background
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(40.dp)
                        .background(Color.White, CircleShape)
                        .clickable { /* Add your navigation logic here */ },
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

            Spacer(modifier = Modifier.height(20.dp))


            // Nom du restaurant
            Text(
                text = restaurant.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF303030)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .height(150.dp),

                contentAlignment = Alignment.Center // Centre le contenu à l'intérieur du Box
            ) {
                Image(
                    painter = painterResource(id = restaurant.mapImg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            //Stars Row
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier=Modifier.fillMaxWidth()
                    .padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        imageVector = Icons.Outlined.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFA500)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "${restaurant.noteMoy}",
                        fontSize = 17.sp,
                        color = Color.Black
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "29 avis",
                        fontSize = 17.sp,
                        color = Color.Gray
                    )
                }
            }
            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            )

            //Phone Row
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier=Modifier.fillMaxWidth()
                    .padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Outlined.Call,
                        contentDescription = null,
                        tint = Color(0xFFFFA500)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Phone",
                        fontSize = 17.sp,
                        color = Color.Black
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${restaurant.phone}",
                        fontSize = 17.sp,
                        color = Color.Gray
                    )
                }
            }
            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            )

            //Heures d'ouvertures Row
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier=Modifier.fillMaxWidth()
                    .padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        modifier = Modifier.size(17.dp),
                        painter = painterResource(R.drawable.clock_orange),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Heures d'ouverture",
                        fontSize = 17.sp,
                        color = Color.Black
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${restaurant.openHour} - ${restaurant.closeHour}",
                        fontSize = 17.sp,
                        color = Color.Gray
                    )
                }
            }
            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            )

            //Délai de livraison Row
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier=Modifier.fillMaxWidth()
                    .padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        modifier = Modifier.size(22.dp),
                        painter = painterResource(R.drawable.food_delivery),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Délai de livraison",
                        fontSize = 17.sp,
                        color = Color.Black
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${restaurant.deliverytime}",
                        fontSize = 17.sp,
                        color = Color.Gray
                    )
                }
            }
            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            )

            //Facebook Row
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier=Modifier.fillMaxWidth()
                    .padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(R.drawable.facebook),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Fecebook",
                        fontSize = 17.sp,
                        color = Color.Black
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${restaurant.facebook}",
                        fontSize = 17.sp,
                        color = Color.Gray
                    )
                }
            }
            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp)
            )

            //Instagram Row
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier=Modifier.fillMaxWidth()
                    .padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(R.drawable.instagram),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Instagram",
                        fontSize = 17.sp,
                        color = Color.Black
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${restaurant.instagram}",
                        fontSize = 17.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}