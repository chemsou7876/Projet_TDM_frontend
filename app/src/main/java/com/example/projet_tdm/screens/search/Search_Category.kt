package com.example.projet_tdm.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.projet_tdm.R
import com.example.projet_tdm.models.Menu
import com.example.projet_tdm.models.Restaurant
import com.example.projet_tdm.models.getData
import com.example.projet_tdm.screens.home.tabs.RestaurantItem
import com.example.projet_tdm.ui.theme.Sen


@Composable
fun SearchCategorieView(navController: NavController){
    var selectedCategory by remember { mutableStateOf("Catégories") }
    LazyColumn(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Row {
                HeaderSearchCategory(
                    navController = navController,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { newCategory ->
                        selectedCategory = newCategory
                    }
                )
            }
        }

        item {
            MenuListView(restaurants = getData(),navController)
        }

        item {
            OpenRestaurantsCategory(navController = navController)
        }
    }
}

@Composable
fun MenuListView(restaurants: List<Restaurant>,navController: NavController) {
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
            TextButton (onClick = { /**TODO*/}) {
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

        // Affichage de la grille fixe avec 2 colonnes, sans scrolling
        val rows = restaurants.take(6).chunked(2) // Crée des groupes de 2 éléments
        for (row in rows) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                row.forEach { restaurant ->
                    MenuBox(restaurant = restaurant,navController = navController)
                }
            }
        }
    }
}

@Composable
fun MenuBox(restaurant: Restaurant,navController: NavController) {
    // On prend seulement le premier menu du restaurant (le premier dans la liste)
    restaurant.menus.firstOrNull()?.let { menu ->
        Card(
            modifier = Modifier
                // Chaque élément prend une proportion égale de l'espace disponible
                .padding(5.dp)
                .clickable(onClick = { navController.navigate("menuView/${menu.id}/${restaurant.id}") },)
                .height(250.dp),

            elevation = CardDefaults.elevatedCardElevation(4.dp)
        ) {
            // Use a Box to set the background color
            Box(
                modifier = Modifier
                    .width(165.dp)
                    .fillMaxSize()
                    .background(Color.White) // Set the background color here
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = menu.imageUrl), // Image du menu
                        contentDescription = menu.name,
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                    )

                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = menu.name,
                            fontSize = 15.sp,
                            fontFamily = Sen,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 10.dp)
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = "${restaurant.name}", // Nom du restaurant
                            fontFamily = Sen,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 10.dp)
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Row(
                            modifier = Modifier
                                .padding(end = 10.dp, start = 10.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "${menu.price} DA", // Prix du menu
                                fontFamily = Sen,
                                color = Color.Black
                            )

                            Box(
                                modifier = Modifier
                                    .size(24.dp) // Taille du cercle
                                    .background(
                                        color = Color(0xFFF58D1D),
                                        shape = CircleShape
                                    ), // Fond noir en forme de cercle
                                contentAlignment = Alignment.Center // Alignement de l'icône au centre
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "ADD to cart Icon",
                                    tint = Color.White, // Couleur blanche pour l'icône
                                    modifier = Modifier.size(14.dp) // Taille de l'icône
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MenuBoxx(menu:Menu,restaurant: Restaurant,navController: NavController) {

    Card(
            modifier = Modifier
                .clickable {
                    navController.navigate("menuView/${menu.id}/${restaurant.id}")
                }
                // Chaque élément prend une proportion égale de l'espace disponible
                .padding(5.dp)
                .height(250.dp),

            elevation = CardDefaults.elevatedCardElevation(4.dp)
        ) {
            // Use a Box to set the background color
            Box(
                modifier = Modifier
                    .width(165.dp)
                    .fillMaxSize()
                    .background(Color.White) // Set the background color here
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = menu.imageUrl), // Image du menu
                        contentDescription = menu.name,
                        modifier = Modifier
                            .size(150.dp) // Fixer la taille de l'image
                            .padding(bottom = 10.dp)
                            .clip(RoundedCornerShape(8.dp)) ,// Ajouter un bord arrondi de 16px
                        contentScale = ContentScale.Crop,
                    )

                    Column(
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = menu.name,
                            fontSize = 15.sp,
                            fontFamily = Sen,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 10.dp)
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = "${restaurant.name}", // Nom du restaurant
                            fontFamily = Sen,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 10.dp)
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Row(
                            modifier = Modifier
                                .padding(end = 10.dp, start = 10.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "${menu.price} DA", // Prix du menu
                                fontFamily = Sen,
                                color = Color.Black
                            )

                            Box(
                                modifier = Modifier
                                    .size(24.dp) // Taille du cercle
                                    .background(
                                        color = Color(0xFFF58D1D),
                                        shape = CircleShape
                                    ), // Fond noir en forme de cercle
                                contentAlignment = Alignment.Center // Alignement de l'icône au centre
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "ADD to cart Icon",
                                    tint = Color.White, // Couleur blanche pour l'icône
                                    modifier = Modifier.size(14.dp) // Taille de l'icône
                                )
                            }
                        }
                    }
                }
            }
        }

}

@Composable
fun OpenRestaurantsCategory(navController: NavController) {
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
            TextButton (onClick = { /**TODO*/}) {
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

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            restaurants.take(6).forEach { restaurant ->
                RestaurantItem(navController = navController,restaurant = restaurant)
                Divider(color = Color.Transparent, thickness = 0.5.dp, modifier = Modifier.padding(vertical = 12.dp))
            }
        }
    }
}

@Composable
fun PopularBurgersGrid(selectedCategory: String, onCategorySelected: (String) -> Unit) {

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
                text = selectedCategory,
                fontSize = 18.sp,
                fontFamily = Sen
            )
            TextButton (onClick = { /**TODO*/}) {
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


@Composable
fun HeaderSearchCategory(navController: NavController,selectedCategory: String , onCategorySelected: (String) -> Unit){
    var isFilterDialogVisible by remember { mutableStateOf(false) }
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
                modifier = Modifier.size(40.dp)    .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { navController.popBackStack() }
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = selectedCategory,
                modifier = Modifier
                    .padding(top = 8.dp),
                color = Color(0xFF303030),
                fontSize = 18.sp,
                fontFamily = Sen
            )
        }

        Row(
            horizontalArrangement = Arrangement.Start,
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp) // Taille du cercle
                    .background(
                        color = Color.Black,
                        shape = CircleShape
                    ), // Fond noir en forme de cercle
                contentAlignment = Alignment.Center // Alignement de l'icône au centre
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.search), // Remplacez par votre ressource
                    contentDescription = "Search Icon",
                    tint = Color.White, // Couleur blanche pour l'icône
                    modifier = Modifier.size(18.dp) // Taille de l'icône
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .size(40.dp) // Taille du cercle
                    .background(
                        color = Color(0xFFECF0F4),
                        shape = CircleShape
                    ), // Fond noir en forme de cercle
                contentAlignment = Alignment.Center // Alignement de l'icône au centre
            ) {
                IconButton(onClick = { isFilterDialogVisible = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.filter),
                        contentDescription = "Filter Icon",
                        tint = Color(0xFFFFA500),
                        modifier = Modifier
                            .padding(start = 2.dp)
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
    }
}