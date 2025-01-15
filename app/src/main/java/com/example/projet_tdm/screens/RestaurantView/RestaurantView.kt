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
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projet_tdm.R
import com.example.projet_tdm.models.Restaurant
import com.example.projet_tdm.screens.search.MenuBoxx
import com.example.projet_tdm.services.RetrofitClient
import com.example.projet_tdm.services.Review
import com.example.projet_tdm.services.ReviewsResponse
import com.example.projet_tdm.ui.theme.Sen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RestaurantDetailsScreen(navController: NavController, restaurant: Restaurant) {
    var selectedCategory by remember { mutableStateOf("Burger") }
    var reviews by remember { mutableStateOf<List<Review>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    @Composable
    fun NoReviewsPlaceholder() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icon
            Icon(
                imageVector = Icons.Outlined.Comment, // Use a star outline icon
                contentDescription = "No Reviews",
                modifier = Modifier.size(64.dp),
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            // Message
            Text(
                text = "No reviews available.",
                fontSize = 18.sp,
                color = Color.Gray,
                fontFamily = Sen,
                textAlign = TextAlign.Center
            )
        }
    }
    // Fetch reviews when the composable is first launched
    LaunchedEffect(restaurant.restaurant_id) {
        RetrofitClient.instance.getReviewsForRestaurant(restaurant.restaurant_id).enqueue(
            object : Callback<ReviewsResponse> {
                override fun onResponse(
                    call: Call<ReviewsResponse>,
                    response: Response<ReviewsResponse>
                ) {
                    if (response.isSuccessful) {
                        val reviewsResponse = response.body()
                        if (reviewsResponse != null) {
                            println("Reviews fetched: ${reviewsResponse.reviews}") // Debug log
                            reviews = reviewsResponse.reviews
                        } else {
                            error = "No reviews found."
                            println("No reviews found in response.") // Debug log
                            reviews = emptyList() // Set reviews to an empty list
                        }
                    } else {
                        error = "Failed to fetch reviews: ${response.message()}"
                        println("Error fetching reviews: ${response.message()}") // Debug log
                        println("Error body: ${response.errorBody()?.string()}") // Debug log
                    }
                    isLoading = false
                }

                override fun onFailure(call: Call<ReviewsResponse>, t: Throwable) {
                    error = "Error: ${t.localizedMessage}"
                    println("Exception fetching reviews: ${t.localizedMessage}") // Debug log
                    t.printStackTrace() // Print full stack trace
                    isLoading = false
                }
            }
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Existing restaurant details UI
            Box(modifier = Modifier.padding(top = 16.dp)) {
                Image(
                    painter = painterResource(id = restaurant.imgUrl),
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

            Text(
                text = restaurant.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF303030),
                fontFamily = Sen,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${restaurant.typeCuisine} - ${restaurant.localisation}",
                fontSize = 16.sp,
                color = Color.Gray,
                fontFamily = Sen,
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                            .border(1.dp, if (isSelected) Color(0x00FFA500) else Color(0xFFEDEDED), RoundedCornerShape(50))
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

        if (isLoading) {
            item {
                CircularProgressIndicator()
            }
        } else if (error != null) {

            item {
                NoReviewsPlaceholder() // Show the placeholder when there are no reviews
            }}
        else {
            items(reviews) { review ->
                ReviewCard(
                    name = review.userName,
                    rating = review.rating,
                    review = review.comment
                )
            }
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
                    Text(text = name, style = MaterialTheme.typography.titleMedium, fontFamily = Sen)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Row {
                    repeat(5) { index ->
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = if (index < rating) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = null,
                            tint = if (index < rating) Color(0xFFFFA500) else Color.Gray
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

