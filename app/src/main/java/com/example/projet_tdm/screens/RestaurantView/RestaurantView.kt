package com.example.projet_tdm.screens.RestaurantView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.projet_tdm.screens.search.MenuBox
import com.example.projet_tdm.screens.search.MenuBoxx
import com.example.projet_tdm.ui.theme.Sen

@Composable
fun RestaurantDetailsScreen(navController: NavController, restaurant: Restaurant) {
    var selectedCategory by remember { mutableStateOf("Burger") }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Box (modifier = Modifier.padding(top = 16.dp)){

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
                        .clickable { navController.popBackStack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = Color.Black,
                        modifier = Modifier
                        .size(24.dp)
                        .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { navController.popBackStack() }
)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Outlined.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFA500)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "${restaurant.noteMoy}",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontFamily = Sen,
                )

                Spacer(modifier = Modifier.width(25.dp))

                Image(
                    modifier = Modifier.size(17.dp),
                    painter = painterResource(R.drawable.clock_orange),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = "${restaurant.deliverytime}",
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontFamily = Sen,
                )

                Spacer(modifier = Modifier.width(25.dp))

                Image(
                    modifier = Modifier.size(22.dp),
                    painter = painterResource(R.drawable.food_delivery),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = "${restaurant.deliveryprice}",
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontFamily = Sen,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Nom du restaurant
            Text(
                text = restaurant.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF303030),
                fontFamily = Sen,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Note moyenne et type de cuisine
            Text(
                text = "${restaurant.typeCuisine} - ${restaurant.localisation}",
                fontSize = 16.sp,
                color = Color.Gray,
                fontFamily = Sen,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Description
            Text(
                text = restaurant.description,
                fontSize = 16.sp,
                lineHeight = 22.sp,
                color = Color(0xFF505050),
                fontFamily = Sen,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Outlined.Call,
                    contentDescription = null,
                    tint = Color(0xFFFFA500)
                )
                Spacer(modifier = Modifier.width(25.dp))
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(R.drawable.facebook),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(25.dp))
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(R.drawable.instagram),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(25.dp))
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Outlined.Share,
                    contentDescription = null,
                    tint = Color(0xFFFFA500)
                )
                Spacer(modifier = Modifier.width(25.dp))
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            navController.navigate("restaurantViewInfos/${restaurant.id}")
                        },
                    imageVector = Icons.Outlined.Info,
                    contentDescription = null,
                    tint = Color(0xFFFFA500)
                )
            }
        }

        item {
            // Categories LazyRow
            Text(
                text = "Categories",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp),
                fontFamily = Sen,
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                val categories = restaurant.menus.map { it.category }.distinct()
                items(categories) { category ->
                    val isSelected = category == selectedCategory
                    Box(
                        modifier = Modifier
                            .clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { selectedCategory = category }
                            .clip(RoundedCornerShape(50))
                            .background(if (isSelected) Color(0xFFFFA500) else Color.Transparent)
                            .border(1.dp,if (isSelected) Color(0x00FFA500) else Color(0xFFEDEDED), RoundedCornerShape(50))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = category,
                            color = if (isSelected) Color.White else Color.DarkGray,
                            fontSize = 16.sp,
                            fontFamily = Sen,
                        )
                    }
                }
            }
        }

        item {
            // Popular Dishes LazyRow
            Text(
                text = selectedCategory,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp),
                fontFamily = Sen,
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                items(restaurant.menus.filter { it.category == selectedCategory }) { menu ->
                    MenuBoxx(menu, restaurant, navController)
                }
            }
        }

        item {
            // Reviews Section
            Text(
                text = "Reviews",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp),
                fontFamily = Sen,
            )
        }

        items(sampleReviews) { review ->
            ReviewCard(
                name = review.name,
                rating = review.rating,
                review = review.comment
            )
        }
    }
}

@Composable
fun ReviewCard(name: String, rating: Int, review: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .background(Color(0xFFFFF0EB))
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .padding(start = 18.dp, end = 18.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = name, style = MaterialTheme.typography.titleMedium,fontFamily = Sen)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Row {
                    // Display 5 stars total
                    repeat(5) { index ->
                        Icon(
                            modifier = Modifier.size(20.dp),
                            // Use filled star for ratings up to the current rating
                            imageVector = if (index < rating) {
                                Icons.Filled.Star
                            } else {
                                Icons.Outlined.Star // or Icons.Default.StarBorder
                            },
                            contentDescription = null,
                            tint = if (index < rating) {
                                Color(0xFFFFA500) // Orange for filled stars
                            } else {
                                Color.Gray // Gray for unfilled stars
                            }
                        )
                    }
                }
                Text(
                    text = review,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    fontFamily = Sen,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

data class Review(
    val name: String,
    val rating: Int,
    val comment: String
)

val sampleReviews = listOf(
    Review("Rania Sbr", 5, "Amazing food! The burgers are absolutely delicious. Will definitely come back!"),
    Review("John Doe", 4, "Great atmosphere and friendly staff. The pizza was fantastic."),
    Review("Sarah Smith", 3, "Decent food but the service was a bit slow."),
    Review("Mike Johnson", 5, "Best restaurant in town! Love their signature dishes."),
    Review("Emma Wilson", 4, "Good portion sizes and reasonable prices. Nice ambiance."),
    Review("Alex Brown", 5, "The chef's special was outstanding! Highly recommend."),
    Review("Lisa Chen", 3, "Food was okay but could use more seasoning."),
    Review("David Kim", 4, "Fresh ingredients and great presentation."),
    Review("Maria Garcia", 5, "Excellent service! The waiter was very attentive."),
    Review("Tom Anderson", 4, "Nice variety of options. The desserts are must-try!")
)
