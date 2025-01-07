package com.example.projet_tdm.screens.home.tabs

import com.example.projet_tdm.ui.theme.Sen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
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
import androidx.navigation.NavController
import com.example.projet_tdm.R
import com.example.projet_tdm.models.Restaurant
import com.example.projet_tdm.models.getData
import com.example.projet_tdm.screens.search.FilterDialog




@Composable
fun HomeTab(){
    var searchText by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
    ) {
        Row { HeaderHome() }
        Row { SearchBar(searchText) { newValue -> searchText = newValue } }
        Row { CategoriesSection() }
        //Row { PromotionBanner() }
        Row { OpenRestaurantsSection() }
    }
}

@Composable
fun HeaderHome() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 45.dp, start = 10.dp, end = 10.dp, bottom = 10.dp)
    ) {
        Text(
            text = "Order Your Favorite Meal !",
            modifier = Modifier
                .padding(top = 5.dp),
            color = Color(0xFF303030),
            fontSize = 23.sp,
            fontFamily = Sen,
            fontWeight = FontWeight.Bold
        )
        Image(
            painter = painterResource(R.drawable.cart2_icon),
            contentDescription = null,
            modifier = Modifier.size(40.dp),
            //contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun SearchBar(searchText: TextFieldValue, onValueChange: (TextFieldValue) -> Unit){
    var isFilterDialogVisible by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(25.dp))
            .padding(horizontal = 45.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.search),
            contentDescription = "Search Icon",
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        TextField(
            value = searchText,
            onValueChange = onValueChange,
            placeholder = { Text("Search restaurant..." ,fontFamily = Sen,) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFF8B06),
                unfocusedBorderColor = Color(0xFF303030),
                unfocusedContainerColor = Color(0xFFF5F5F5),
                focusedContainerColor = Color(0xFFF5F5F5)
            ),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
        Divider(
            color = Color.Gray,
            modifier = Modifier
                .height(24.dp)
                .width(1.dp)
        )
        IconButton(onClick = {isFilterDialogVisible = true}) {
            Icon(
                painter = painterResource(id = R.drawable.filter),
                contentDescription = "Filter Icon",
                tint = Color(0xFFFFA500),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(24.dp)
            )
        }
        // Affiche la pop-up si elle est visible
        if (isFilterDialogVisible) {
            FilterDialog(
                onDismiss = { isFilterDialogVisible = false },
                onFilterApply = {
                    // Logique pour appliquer les filtres
                    isFilterDialogVisible = false
                }
            )
        }
    }
}

@Composable
fun CategoriesSection() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "All Categories",
                fontSize = 18.sp,
                fontFamily = Sen,
                //fontWeight = FontWeight.Bold
            )
            TextButton (onClick = { /* See all action */ }) {
                Text(text = "See All" , fontFamily = Sen)

            }
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val categories = listOf("Burgers", "Pizzas" ,"Poulet","Tacos","Sandwichs","Asiatique","Traditional","Healthy","Pâtes","Indien","Syrienne","Poissons","Crêpes","Pancakes","Libanais","Dessert")
            val categoryImages = mapOf(
                "Burgers" to R.drawable.burger_plat,
                "Pizzas" to R.drawable.pizza_plat,
                "Poulet" to R.drawable.poulet_plat,
                "Tacos" to R.drawable.tacos_plate,
                "Sandwichs" to R.drawable.sandwichs_plat,
                "Asiatique" to R.drawable.sushi_plat,
                "Traditional" to R.drawable.traditional_plat,
                "Healthy" to R.drawable.healthy_plat,
                "Pâtes" to R.drawable.pates_plat,
                "Indien" to R.drawable.indian_plat,
                "Syrienne" to R.drawable.syrien_plat,
                "Poissons" to R.drawable.poisson_plat,
                "Crêpes" to R.drawable.crepes_plat,
                "Pancakes" to R.drawable.pancakes_plat,
                "Libanais" to R.drawable.libaneese_plat,
                "Dessert" to R.drawable.donut_plat
            )

            items(categories) { category ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Color.Transparent),
                        contentAlignment = Alignment.Center
                    ) {
                        val imageRes = categoryImages[category]
                        if (imageRes != null) {
                            Image(
                                painter = painterResource(id = imageRes),
                                contentDescription = "$category image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Text(text = category, fontSize = 14.sp)
                }
            }
        }
    }
}


@Composable
fun PromotionBanner() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            //.padding(horizontal = 15.dp)
            .background(Color(0xFFFFA500), shape = RoundedCornerShape(15.dp))
            .padding(25.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(2f)) {
                Text(
                    text = "Looking for great deals? \nCheck out our irresistible offers now!",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Button(
                    onClick = { /* Action du bouton */ },
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(150.dp)
                ) {
                    Text(text = "Order Now", color = Color.White)
                }
            }
            Image(
                painter = painterResource(id = R.drawable.burger),
                contentDescription = "Burger Image",
                modifier = Modifier
                    .size(150.dp)
                    .padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun RestaurantItem( restaurant: Restaurant) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(15.dp))
            .clickable {
              //  navController.navigate("restaurantDetails/${restaurant.id}")
            }
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(15.dp))
        //.padding(16.dp)
    ) {
        Column {
            // Afficher l'image du restaurant
            Image(
                painter = painterResource(id = restaurant.imgUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(10.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp)
            ) {
                // Afficher le nom du restaurant
                Text(
                    text = restaurant.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Sen,
                    color = Color(0xFF303030)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = restaurant.noteMoy.toString(), // Conversion de Double en String
                        fontSize = 15.sp
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Image(
                        modifier = Modifier
                            .size(15.dp),
                        painter = painterResource(R.drawable.star),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }

            }

            Spacer(modifier = Modifier.height(4.dp))

            // Afficher le type de cuisine et la localisation
            Text(
                text = "${restaurant.typeCuisine} - ${restaurant.localisation}",
                fontFamily = Sen,
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Icônes de réseaux sociaux
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(15.dp),
                    painter = painterResource(R.drawable.clock),
                    contentDescription = null,
                    contentScale = ContentScale.Crop

                )
                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = "${restaurant.deliverytime}    - ",

                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.width(10.dp))

                Image(
                    modifier = Modifier
                        .size(15.dp),
                    painter = painterResource(R.drawable.bike_icon),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = "${restaurant.deliveryprice}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun OpenRestaurantsSection() {
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
                text = "Open Restaurants",
                fontSize = 20.sp,
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
            items(restaurants) { restaurant ->
                Box(modifier = Modifier.clickable {}) {
                    RestaurantItem(restaurant = restaurant)
                }
                Divider(color = Color.Transparent, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 12.dp))
            }


        }
    }
}