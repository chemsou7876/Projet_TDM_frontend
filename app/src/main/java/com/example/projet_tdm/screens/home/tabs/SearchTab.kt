package com.example.projet_tdm.screens.home.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projet_tdm.R
import com.example.projet_tdm.models.PannierSingleton
import com.example.projet_tdm.models.Restaurant
import com.example.projet_tdm.models.getData
import com.example.projet_tdm.screens.home.tabs.SearchBar
import com.example.projet_tdm.ui.theme.Sen

@Composable
fun SearchTab(navController: NavController) {
    var searchText by remember { mutableStateOf(TextFieldValue()) }
    val restaurants = getData()

    LazyColumn(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        // Header
        item {
            HeaderSearch(navController)
        }

        // Search Bar
        item {
            SearchBarwithoutFilter(searchText = searchText, onValueChange = { newValue ->
                searchText = newValue

            },)
        }

        // Suggested Restaurants Section Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Suggested Restaurants",
                    fontSize = 18.sp,
                    fontFamily = Sen
                )
                TextButton(onClick = { /* See all action */ }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = "See All", fontFamily = Sen, color = Color(0xFFA0A5BA))
                        Spacer(modifier = Modifier.width(4.dp))
                        Image(
                            painter = painterResource(id = R.drawable.arrow_right_simple),
                            contentDescription = "Arrow Right Icon",
                            modifier = Modifier.size(7.dp)
                        )
                    }
                }
            }
        }

        // Suggested Restaurants Items
        items(restaurants.take(4)) { restaurant ->
            SuggestedRestaurantItem(restaurant = restaurant,navController = navController)
            Divider(
                color = Color(0xFFE3EBF2),
                thickness = 0.5.dp,
                modifier = Modifier.padding(vertical = 1.dp)
            )
        }

        // Popular Fast Food Section Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Popular Fast Food",
                    fontSize = 18.sp,
                    fontFamily = Sen
                )
                TextButton(onClick = { /* See all action */ }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = "See All", fontFamily = Sen, color = Color(0xFFA0A5BA))
                        Spacer(modifier = Modifier.width(4.dp))
                        Image(
                            painter = painterResource(id = R.drawable.arrow_right_simple),
                            contentDescription = "Arrow Right Icon",
                            modifier = Modifier.size(7.dp)
                        )
                    }
                }
            }
        }

        // Popular Fast Food Grid
        item {
            val selectedRestaurants = if (restaurants.size > 9) {
                restaurants.subList(4, 10)
            } else {
                restaurants.subList(4, restaurants.size)
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.height(((selectedRestaurants.size + 1) / 2 * 240).dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(selectedRestaurants) { restaurant ->
                    PopularFastFoodItem(restaurant = restaurant,navController=navController)
                }
            }
        }

        // Add some bottom padding
        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun HeaderSearch(navController: NavController){
    val context = LocalContext.current

    // Initialize PannierSingleton when the screen is composed
    LaunchedEffect(Unit) {
        PannierSingleton.initialize(context)
    }

    // Access the pannier
    val pannier = PannierSingleton.pannier
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
        ) {



            Text(
                text = "Search",
                modifier = Modifier
                    .padding(top = 8.dp),
                color = Color(0xFF303030),
                fontSize = 20.sp,
                fontFamily = Sen,
                fontWeight = FontWeight.Bold,
            )
        }
        Image(
            painter = painterResource(if (pannier.orders.size == 0) R.drawable.cart_off else R.drawable.cart_on),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clickable(indication = null,interactionSource = remember { MutableInteractionSource() },)
                { navController.navigate("cart") }
        )
    }
}

@Composable
fun SuggestedRestaurantItem(restaurant: Restaurant,navController: NavController){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("restaurantDetails/${restaurant.id}")
            }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = restaurant.imgUrl), // Replace with your image
            contentDescription = "Restaurant Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(16.dp)) // Ajouter un radius de 16dp
                .background(Color.LightGray) // Optionnel : couleur de fond
        )
        Column {
            Row(){
                Text(text = restaurant.name, fontSize = 16.sp,fontFamily = Sen, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_star), // Replace with your icon
                    contentDescription = "Star",
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = restaurant.noteMoy.toString(), fontSize = 12.sp , modifier = Modifier.padding(top = 2.dp))
                Text(text = "  • ${restaurant.deliverytime} - ${restaurant.deliveryprice}", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 2.dp))
            }

        }

    }
}



@Composable
fun PopularFastFoodItem(restaurant: Restaurant,navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(5.dp).clickable {
            navController.navigate("restaurantDetails/${restaurant.id}")
        }
    ) {
        Image(
            painter = painterResource(id = restaurant.imgUrl), // Replace with your image
            contentDescription = "Restaurant Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(170.dp) // Adjust size to your needs
                .clip(RoundedCornerShape(16.dp))
                .background(Color.LightGray)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = restaurant.name,
            fontSize = 14.sp,
            fontFamily = Sen
        )
        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_star), // Replace with your icon
                contentDescription = "Star",
                tint = Color(0xFFFFD700),
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))
            Text(text = restaurant.noteMoy.toString(), fontSize = 12.sp , modifier = Modifier.padding(top = 2.dp))
            Text(text = "  • ${restaurant.deliverytime} - ${restaurant.deliveryprice}", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 2.dp))
        }
    }
}

