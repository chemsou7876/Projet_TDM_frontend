package com.example.projet_tdm.screens.MenuView

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projet_tdm.components.CustomTextField
import com.example.projet_tdm.models.Menu
import com.example.projet_tdm.models.Order
import com.example.projet_tdm.models.PannierSingleton
import com.example.projet_tdm.models.Restaurant
import com.example.projet_tdm.ui.theme.Sen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuView(navController: NavController, menu: Menu, restaurant: Restaurant) {
    var quantity by remember { mutableStateOf(1) }
    var isExpanded by remember { mutableStateOf(false) }
    var preferences by remember { mutableStateOf("") }
    val context = LocalContext.current

    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = "arrow rotation"
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Main content
        Column(
            modifier = Modifier
                .weight(1f)
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
                        .clickable(interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }, indication = null) { navController.navigateUp() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
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
                fontFamily = Sen,
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
                    text = "4.5",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontFamily = Sen
                )

                Spacer(modifier = Modifier.width(25.dp))

                Text(
                    text = "${menu.preparationTime} mins",
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontFamily = Sen
                )

                Spacer(modifier = Modifier.width(25.dp))

                Text(
                    text = "${restaurant.deliveryprice}",
                    fontSize = 15.sp,
                    color = Color.Black,
                    fontFamily = Sen
                )
            }

            Text(
                text = menu.description,
                fontSize = 16.sp,
                lineHeight = 22.sp,
                fontFamily = Sen,
                color = Color(0xFF505050)
            )
        }

        // Bottom section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF0F5FA), RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .padding(top = 0.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),

            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Arrow up indicator
            Box(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { isExpanded = !isExpanded }
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Expand",
                        modifier = Modifier.rotate(rotationState)
                    )
                }
            }

            // Price and quantity controls
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                 //   text = "Total: $${String.format("%.2f", menu.price * quantity)}",
                    text = "${menu.price * quantity} DA",
                    fontSize = 28.sp,
                    fontFamily = Sen,
                    fontWeight = FontWeight.Bold,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).background(Color.Black, RoundedCornerShape(50.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(30.dp)
                            .background(Color.DarkGray, CircleShape)
                            .clickable {if (quantity > 1) quantity--  },
                        contentAlignment = Alignment.Center
                    ){
                        Text(text = "-", fontSize = 20.sp,color= Color.White,fontFamily = Sen)
                    }

                    Text(
                        text = quantity.toString(),
                        fontSize = 18.sp,
                        fontFamily = Sen,
                        color = Color.White
                    )

                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(30.dp)
                            .background(Color.DarkGray, CircleShape)
                            .clickable {quantity++ },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "+", fontSize = 20.sp,color= Color.White,fontFamily = Sen)
                    }
                }
            }

            // Preferences field (only visible when expanded)
            if (isExpanded) {

                androidx.compose.material.OutlinedTextField(
                    value = preferences,
                    onValueChange =  { preferences = it },
                    placeholder = {
                        androidx.compose.material.Text(
                            "Feel free to share any preferences or instructions (e.g., no cheese, well-done steak)",
                            color = Color(0xFFA0A5BA),
                            fontWeight = FontWeight.W400,
                            fontFamily = Sen
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType =  KeyboardType.Text
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom =  4.dp )
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(Color.White),
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFFE3EBF2),
                        unfocusedBorderColor = Color(0x00F0F5FA),
                        errorBorderColor = Color(0xFFB00020)
                    )
                )

            }


            // Add to cart button
            Button(
                onClick = {
                    try {
                        val order = Order(
                            item = menu,
                            quantity = quantity,
                            totalAmount = menu.price * quantity,
                            preferences = preferences,
                            deliveryFee = 0.0
                        )
                        PannierSingleton.addOrder(order)
                       // navController.navigate("cart")
                    } catch (e: Exception) {
                        // Show error message to user
                        Toast.makeText(
                            context,
                            "Failed to add item to cart. Please try again.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF7622),),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "ADD TO CART",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}