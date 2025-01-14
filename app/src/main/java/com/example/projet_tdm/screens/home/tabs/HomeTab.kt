package com.example.projet_tdm.screens.home.tabs

import com.example.projet_tdm.ui.theme.Sen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
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
import com.example.projet_tdm.screens.search.FilterDialog
import com.example.projet_tdm.screens.search.FilterState


val name: String= "User"

@Composable
fun HomeTab(navController: NavController) {
    var searchText by remember { mutableStateOf(TextFieldValue()) }
    var filteredRestaurants by remember { mutableStateOf(getData()) }
    val isSearching = searchText.text.isNotEmpty()
    var isFilterDialogVisible by remember { mutableStateOf(false) }
    var appliedFilters by remember { mutableStateOf<FilterState?>(null) }
    var promotion by remember { mutableStateOf(false) }
    // Combined filter function that handles both search and filters
    fun filterRestaurants(query: String, filters: FilterState? = appliedFilters) {
        var results = getData()

        // Apply text search if query is not empty
        if (query.isNotEmpty()) {
            results = results.filter { restaurant ->
                restaurant.name.contains(query, ignoreCase = true) ||
                        restaurant.typeCuisine.contains(query, ignoreCase = true) ||
                        restaurant.localisation.contains(query, ignoreCase = true)
            }
        }

        // Apply filters if they exist
        filters?.let { filterState ->
            results = results.filter { restaurant ->
                // Filter by delivery time
                val deliveryTimeMatch = when {
                    filterState.deliveryTime.time1 -> restaurant.deliverytime == "10-15 min"
                    filterState.deliveryTime.time2 -> restaurant.deliverytime == "20 min"
                    filterState.deliveryTime.time3 -> restaurant.deliverytime == "30 min"
                    else -> true // If no delivery time is selected, include all
                }

                // Filter by price range
                val priceInRange = restaurant.menus.any { menu ->
                    menu.price >= filterState.pricing.currentRange.start &&
                            menu.price <= filterState.pricing.currentRange.endInclusive
                }

                // Filter by rating
                val ratingMatch = restaurant.noteMoy >= 3.5
                val cuisineTypeMatch = filterState.cuisineTypes.selectedTypes.isEmpty() ||
                        filterState.cuisineTypes.selectedTypes.contains(restaurant.typeCuisine)
                deliveryTimeMatch && priceInRange && ratingMatch && cuisineTypeMatch
            }
        }

        filteredRestaurants = results
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Row { HeaderHome(navController) }
        Row {
            SearchBar(
                searchText = searchText,
                onValueChange = { newValue ->
                    searchText = newValue
                    filterRestaurants(newValue.text)
                },
                onFilterClick = { isFilterDialogVisible = true }
            )
        }

        if (isFilterDialogVisible) {
            FilterDialog(
                onDismiss = { isFilterDialogVisible = false },
                onFilterApply = { filterState ->
                    appliedFilters = filterState
                    filterRestaurants(searchText.text, filterState)
                    isFilterDialogVisible = false
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            if (!isSearching && appliedFilters == null) {
                if(promotion == true) {
                    item { PromotionBanner() }
                }
                item { CategoriesSection(navController) }
                item { OpenRestaurantsSection(navController) }

            } else {
                item {
                    OpenRestaurantsSectionWithSearch(
                        restaurants = filteredRestaurants,
                        searchQuery = searchText.text,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun HeaderHome(navController: NavController) {
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
        Text(
            text = "Hey ${name},",
            modifier = Modifier
                .padding(top = 5.dp),
            color = Color(0xFF303030),
            fontSize = 23.sp,
            fontFamily = Sen,
            fontWeight = FontWeight.Bold
        )
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

// Update SearchBar to expose onFilterClick
@Composable
fun SearchBar(
    searchText: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onFilterClick: () -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFF5F5F5),
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = if (isFocused) 2.dp else 0.dp,
                color = if (isFocused) Color(0xFFE3EBF2) else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.search),
            contentDescription = "Search Icon",
            tint = Color(0xFFA0A5BA),
            modifier = Modifier.size(24.dp)
        )
        TextField(
            value = searchText,
            onValueChange = onValueChange,
            placeholder = { Text("Search restaurant...", fontFamily = Sen) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                unfocusedContainerColor = Color(0xFFF6F6F6),
                focusedContainerColor = Color(0xFFF6F6F6),
            ),
            modifier = Modifier
                .weight(1f)
                .onFocusChanged { isFocused = it.isFocused }
        )

        IconButton(onClick = onFilterClick) {
            Icon(
                painter = painterResource(id = R.drawable.filter),
                contentDescription = "Filter Icon",
                tint = Color(0xFFA0A5BA),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun SearchBarwithoutFilter(
    searchText: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
) {
    var isFocused by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFF5F5F5),
                shape = RoundedCornerShape(10.dp)
            )
            .border(
                width = if (isFocused) 2.dp else 0.dp,
                color = if (isFocused) Color(0xFFE3EBF2) else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.search),
            contentDescription = "Search Icon",
            tint = Color(0xFFA0A5BA),
            modifier = Modifier.size(24.dp)
        )
        TextField(
            value = searchText,
            onValueChange = onValueChange,
            placeholder = { Text("Search restaurant...", fontFamily = Sen) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                unfocusedContainerColor = Color(0xFFF6F6F6),
                focusedContainerColor = Color(0xFFF6F6F6),
            ),
            modifier = Modifier
                .weight(1f)
                .onFocusChanged { isFocused = it.isFocused }
        )


    }
}
@Composable
fun CategoriesSection(navController: NavController) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "All Categories",
                fontSize = 20.sp,
                fontFamily = Sen,
                //fontWeight = FontWeight.Bold
            )
            TextButton (onClick = { navController.navigate("categorie") }) {
                Row (

                    verticalAlignment = Alignment.CenterVertically,

                ){
                    Text(text = "See All" , fontFamily = Sen, color = Color(0xFFA0A5BA))
                    Spacer(modifier = Modifier.width(4.dp))
                    Image(
                        painter = painterResource(id = R.drawable.arrow_right_simple),
                        contentDescription = "Arrow Right Icon",
                        modifier = Modifier
                            .size(7.dp)
                    )
                }

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
                    modifier = Modifier.padding(vertical = 8.dp).clickable {
                        navController.navigate("category/${category}")
                    }
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
                    fontSize = 16.sp,
                    fontFamily = Sen,
                )
                Button(
                    onClick = { /* Action du bouton */ },
                    colors = ButtonDefaults.buttonColors(Color.Black),
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .width(150.dp)
                        .clip(shape = RoundedCornerShape(16.dp))
                ) {
                    Text(text = "Order Now", color = Color.White, fontFamily = Sen)
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
fun RestaurantItem( navController: NavController,restaurant: Restaurant) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(16.dp))
            .clickable {
                navController.navigate("restaurantDetails/${restaurant.id}")
            }
            .fillMaxWidth()

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
                    .clip(RoundedCornerShape(16.dp))
                    .height(150.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(10.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
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
            Column(
                modifier = Modifier.padding(top = 0.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
            ){
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
}

@Composable
fun OpenRestaurantsSectionWithSearch(
    restaurants: List<Restaurant>,
    searchQuery: String,
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Available Restaurants",
                fontSize = 20.sp,
                fontFamily = Sen,
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

        if (restaurants.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No restaurants found for \"$searchQuery\"",
                    color = Color.Gray,
                    fontFamily = Sen
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                restaurants.forEach { restaurant ->
                    Box(modifier = Modifier.clickable {}) {
                        RestaurantItem(navController= navController,restaurant = restaurant)
                    }
                    Divider(
                        color = Color(0xFFE3EBF2),
                        thickness = 0.5.dp,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }
            }
        }
    }
}
@Composable
fun OpenRestaurantsSection(navController: NavController) {
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
                fontFamily = Sen,
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

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            restaurants.forEach { restaurant ->
                Box(modifier = Modifier.clickable {}) {
                    RestaurantItem(navController= navController ,restaurant = restaurant)
                }
                Divider(
                    color = Color(0xFFE3EBF2),
                    thickness = 0.5.dp,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
            }
        }
    }
}
