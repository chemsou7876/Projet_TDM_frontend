package com.example.projet_tdm.screens.categories


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projet_tdm.models.Restaurant
import com.example.projet_tdm.models.getData
import com.example.projet_tdm.screens.home.tabs.RestaurantItem
import com.example.projet_tdm.screens.home.tabs.SearchBarwithoutFilter
import com.example.projet_tdm.ui.theme.Sen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun CategoryScreen(navController: NavController, category: String) {
    var searchText by remember { mutableStateOf(TextFieldValue()) }
    var filteredRestaurants by remember { mutableStateOf(filterRestaurantsByCategory(category)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        // Header
        Text(
            text = category,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Sen,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Search bar
        SearchBarwithoutFilter(
            searchText = searchText,
            onValueChange = { newValue ->
                searchText = newValue
                filteredRestaurants = filterRestaurantsByCategory(category)
                    .filter { restaurant ->
                        restaurant.name.contains(newValue.text, ignoreCase = true) ||
                                restaurant.description.contains(newValue.text, ignoreCase = true)
                    }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Results count
        Text(
            text = "${filteredRestaurants.size} restaurants found",
            color = Color.Gray,
            fontFamily = Sen,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Restaurant list
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filteredRestaurants.size) { index ->
                RestaurantItem(
                    navController = navController,
                    restaurant = filteredRestaurants[index]
                )
            }
        }
    }
}

private fun filterRestaurantsByCategory(category: String): List<Restaurant> {
    return getData().filter { restaurant ->
        when (category.lowercase()) {
            "burgers" -> restaurant.menus.any { it.category.lowercase() == "burger" }
            "pizzas" -> restaurant.menus.any { it.category.lowercase() == "pizza" }
            "poulet" -> restaurant.menus.any { it.name.lowercase().contains("poulet") ||
                    it.description.lowercase().contains("poulet") }
            "tacos" -> restaurant.menus.any { it.name.lowercase().contains("tacos") }
            "sandwichs" -> restaurant.menus.any { it.category.lowercase() == "sandwich" }
            "asiatique" -> restaurant.typeCuisine.lowercase() == "japonaise"
            "traditional" -> restaurant.typeCuisine.lowercase() == "algérienne"
            "healthy" -> restaurant.menus.any { it.category.lowercase() == "healthy" ||
                    it.name.lowercase().contains("salade") }
            "pâtes" -> restaurant.menus.any { it.category.lowercase() == "pâtes" }
            "indien" -> restaurant.typeCuisine.lowercase() == "indienne"
            "syrienne" -> restaurant.typeCuisine.lowercase() == "syrienne"
            "poissons" -> restaurant.menus.any { it.name.lowercase().contains("poisson") ||
                    it.description.lowercase().contains("poisson") }
            "crêpes" -> restaurant.menus.any { it.name.lowercase().contains("crêpe") }
            "pancakes" -> restaurant.menus.any { it.name.lowercase().contains("pancake") }
            "libanais" -> restaurant.typeCuisine.lowercase() == "libanaise"
            "dessert" -> restaurant.menus.any { it.category.lowercase() == "dessert" }
            else -> false
        }
    }
}