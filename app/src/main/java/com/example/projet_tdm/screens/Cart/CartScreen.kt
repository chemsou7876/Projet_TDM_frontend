package com.example.projet_tdm.screens.Cart
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projet_tdm.models.Order
import com.example.projet_tdm.models.Pannier
import com.example.projet_tdm.models.PannierSingleton
import androidx.compose.material.icons.filled.ShoppingCart  // Add this import
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.projet_tdm.R
import com.example.projet_tdm.components.BackButton
import com.example.projet_tdm.components.CustomTextField
import com.example.projet_tdm.components.OrangeButton
import com.example.projet_tdm.ui.theme.Sen

data class CartItemModel(
    val name: String,
    val unitPrice: Int,
    var quantity: Int
)

@Composable
fun CartScreen(navController: NavController) {
    val context = LocalContext.current
    var isEditing by remember { mutableStateOf(false) }
    val pannier = PannierSingleton.pannier
    val deliveryPrice = 4


    // Load orders from local storage when the screen is composed
    LaunchedEffect(Unit) {
        PannierSingleton.initialize(context)
    }

    // Add this to track cart state
    val isEmpty by remember(pannier.orders) {
        derivedStateOf { pannier.orders.isEmpty() }
    }

    val totalPrice by remember(pannier.orders) {
        derivedStateOf {
            pannier.calculateTotal()
            pannier.total
        }
    }




            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxSize()

            ) {
                CartHeader(
                    isEditing = isEditing,
                    onToggleEdit = {
                        isEditing = !isEditing
                        // Force recomposition when toggling edit mode
                        if (isEmpty) {
                            pannier.calculateTotal()
                        }
                    },
                    navController = navController
                )
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    if (isEmpty) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Empty Cart",
                                modifier = Modifier
                                    .size(120.dp)
                                    .padding(bottom = 16.dp),
                                tint = Color.LightGray
                            )

                            Text(
                                text = "Your cart is empty",
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.Gray,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            Text(
                                text = "Add items to start a new order",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        LazyColumn {
                            items(
                                count = pannier.orders.size,
                                key = { index -> pannier.orders[index].hashCode() }
                            ) { index ->
                                val order = pannier.orders.getOrNull(index) ?: return@items
                                CartItem(
                                    item = order,
                                    isEditing = isEditing,
                                    onRemove = {
                                        if (index < pannier.orders.size) {
                                            pannier.orders.removeAt(index)
                                            pannier.calculateTotal()
                                            PannierSingleton.removeOrder(order)
                                            // Force recomposition after removal
                                            if (pannier.orders.isEmpty()) {
                                                isEditing = false
                                            }

                                        }
                                    },
                                    onQuantityChange = { _, newQuantity ->
                                        if (index < pannier.orders.size) {
                                            pannier.orders[index].quantity = newQuantity
                                            pannier.orders[index].totalAmount =
                                                pannier.orders[index].item.price * newQuantity
                                            pannier.calculateTotal()
                                        }
                                        PannierSingleton.updateOrderQuantity(order, newQuantity)

                                    }
                                )
                            }
                        }
                    }
                }

                // Only show these sections if cart is not empty
                if (!isEmpty) {
                    Spacer(modifier = Modifier.height(16.dp))
                    ExpandableDeliverySection(totalPrice = totalPrice.toInt(), deliveryPrice = deliveryPrice)
                    Spacer(modifier = Modifier.height(16.dp))
                    OrangeButton(text = "Place order", onClick = { })
                    Spacer(modifier = Modifier.height(16.dp))


                }
            }


}


@Composable
fun CartHeader(isEditing: Boolean, onToggleEdit: () -> Unit, navController: NavController){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BackButton(
                onClick = { navController.navigateUp()},
                Color_bg = Color.White,
                IconColor = Color(0xFFFF7622),
                BorderColor = Color(0xFFFF7622),
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Cart",
                modifier = Modifier
                    .padding(top = 8.dp),
                color = Color(0xFF303030),
                fontSize = 23.sp,
                fontFamily = Sen,
                fontWeight = FontWeight.Bold
            )
        }
        TextButton(onClick = onToggleEdit) {
            Text(
                text = if (isEditing) "DONE" else "EDIT ITEMS",
                color = Color(0xFFFFA500)
            )
        }
    }
}

@Composable
fun CartItem(
    item: Order,
    isEditing: Boolean,
    onRemove: () -> Unit,
    onQuantityChange: (Int, Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.item.name, fontSize = 16.sp, fontWeight = FontWeight.Bold,fontFamily = Sen)
            Text(text = "$14\"", fontSize = 14.sp, color = Color.Gray,fontFamily = Sen)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "$${item.totalAmount}", fontSize = 16.sp, fontWeight = FontWeight.Bold,fontFamily = Sen)
        Spacer(modifier = Modifier.width(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isEditing) {
                IconButton(onClick = onRemove) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Remove item",
                        tint = Color.Red
                    )
                }
            } else {
                IconButton(
                    onClick = {
                        if (item.quantity > 1) {
                            onQuantityChange(0, item.quantity - 1)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Decrease"
                    )
                }
                Text(
                    text = "${item.quantity}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Sen
                )
                IconButton(
                    onClick = { onQuantityChange(0, item.quantity + 1) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increase"
                    )
                }
            }
        }
    }
}

@Composable
fun ExpandableDeliverySection(totalPrice: Int, deliveryPrice: Int) {
    var isExpanded by remember { mutableStateOf(false) }
    var address by remember { mutableStateOf("2118 Thornridge Cir. Syracuse") }
    var notes by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF7F7F7), shape = RoundedCornerShape(8.dp))
            .animateContentSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = if (isExpanded) 8.dp else 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "DELIVERY ADDRESS",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                fontFamily = Sen,
                color = Color(0xFFA0A5BA)
            )
            IconButton(onClick = { isExpanded = !isExpanded }) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse" else "Expand"
                )
            }
        }

        if (isExpanded) {
            CustomTextField(
                value = address,
                onValueChange = { address = it },
                label = "Address",
                placeholder = "Enter your delivery address",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            CustomTextField(
                value = notes,
                onValueChange = { notes = it },
                label = "delivery notes",
                placeholder = "Enter delivery notes for easier locating (e.g., 'White house with blue door').",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

        }

        Text(
            text = "DELIVERY PRICE: $$deliveryPrice",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            fontFamily = Sen,
            modifier = Modifier.padding(vertical = 4.dp),
        )
        Text(
            text = "TOTAL: $totalPrice DA",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black,
            fontFamily = Sen
        )
    }
}