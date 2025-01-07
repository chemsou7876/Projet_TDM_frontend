package com.example.projet_tdm.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projet_tdm.R
import com.example.projet_tdm.models.Restaurant
import com.example.projet_tdm.models.getData
import com.example.projet_tdm.screens.home.tabs.SearchBar
import com.example.projet_tdm.ui.theme.Sen

@Composable
fun SearchView() {
    var searchText by remember { mutableStateOf(TextFieldValue()) }
    val restaurants = getData()

    LazyColumn(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        // Header
        item {
            HeaderSearch()
        }

        // Search Bar
        item {
            SearchBar(searchText) { newValue -> searchText = newValue }
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
                    Text(text = "See All")
                }
            }
        }

        // Suggested Restaurants Items
        items(restaurants.take(4)) { restaurant ->
            SuggestedRestaurantItem(restaurant = restaurant)
            Divider(
                color = Color.LightGray,
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
                    Text(text = "See All")
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
                    PopularFastFoodItem(restaurant = restaurant)
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
fun HeaderSearch(){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 45.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
        ) {
            Image(
                painter = painterResource(R.drawable.back_icon),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Search",
                modifier = Modifier
                    .padding(top = 8.dp),
                color = Color(0xFF303030),
                fontSize = 18.sp,
                fontFamily = Sen
            )
        }
        Image(
            painter = painterResource(R.drawable.cart2_icon),
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            //contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun SuggestedRestaurantItem(restaurant: Restaurant){
    Row(
        modifier = Modifier
            .fillMaxWidth()
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
fun SuggestedRestaurants() {
    val restaurants = getData()


    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Suggested Restaurants",
                fontSize = 18.sp,
                //fontFamily = SenFont,
                fontFamily = Sen,
                //fontWeight = FontWeight.Bold
            )
            TextButton(onClick = { /* See all action */ }) {
                Text(text = "See All")
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
            //.padding(vertical = 8.dp)
        ) {
            items(restaurants.take(4)) { restaurant ->
                SuggestedRestaurantItem(restaurant = restaurant)
                Divider(color = Color.LightGray, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 1.dp))
            }
        }
    }
}


@Composable
fun PopularFastFoodItem(restaurant: Restaurant) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(5.dp)
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

@Composable
fun PopularFastFoodGrid() {
    val restaurants = getData()

    // Extraire les éléments de l'indice 4 à 9 (non inclus)
    val selectedRestaurants = if (restaurants.size > 9) {
        restaurants.subList(4, 10) // Sous-liste des indices 4 à 8
    } else {
        restaurants.subList(4, restaurants.size) // Si la liste est plus petite
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Popular Fast Food",
                fontSize = 18.sp,
                fontFamily = Sen
            )
            TextButton(onClick = { /* See all action */ }) {
                Text(text = "See All")
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // 2 items par ligne
            modifier = Modifier.padding(3.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(selectedRestaurants) { restaurant ->
                PopularFastFoodItem(restaurant = restaurant)
            }
        }
    }
}