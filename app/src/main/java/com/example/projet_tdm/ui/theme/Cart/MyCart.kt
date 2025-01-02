package com.example.projet_tdm.ui.theme.Cart
import androidx.compose.animation.animateContentSize
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

data class CartItemModel(
    val name: String,
    val unitPrice: Int,
    var quantity: Int
)

@Composable
fun MyCart() {
    var isEditing by remember { mutableStateOf(false) }
    var cartItems by remember {
        mutableStateOf(
            mutableListOf(
                CartItemModel("Pizza Calzone", 64, 1),
                CartItemModel("Pizza Margherita", 32, 1)
            )
        )
    }
    val deliveryPrice = 4
    val totalPrice by remember(cartItems) {
        derivedStateOf {
            cartItems.sumOf { it.unitPrice * it.quantity } + deliveryPrice
        }
    }

    Scaffold(
        topBar = { CartTopBar(isEditing = isEditing, onToggleEdit = { isEditing = !isEditing }) },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(cartItems.size) { index ->
                        CartItem(
                            item = cartItems[index],
                            isEditing = isEditing,
                            onRemove = {
                                val newList = cartItems.toMutableList()
                                newList.removeAt(index)
                                cartItems = newList
                            },
                            onQuantityChange = { _, newQuantity ->
                                val newList = cartItems.toMutableList()
                                newList[index] = newList[index].copy(quantity = newQuantity)
                                cartItems = newList
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                ExpandableDeliverySection(totalPrice = totalPrice, deliveryPrice = deliveryPrice)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { /* Action passer commande */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
                ) {
                    Text(text = "PLACE ORDER", color = Color.White, fontSize = 16.sp)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartTopBar(isEditing: Boolean, onToggleEdit: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White),
        title = {
            Text(
                text = "Cart",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        },
        actions = {
            TextButton(onClick = onToggleEdit) {
                Text(
                    text = if (isEditing) "DONE" else "EDIT ITEMS",
                    color = Color(0xFFFFA500)
                )
            }
        }
    )
}

@Composable
fun CartItem(
    item: CartItemModel,
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
            Text(text = item.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = "$14\"", fontSize = 14.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "$${item.unitPrice}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
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
                    fontWeight = FontWeight.Bold
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
                fontSize = 14.sp
            )
            IconButton(onClick = { isExpanded = !isExpanded }) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse" else "Expand"
                )
            }
        }

        if (isExpanded) {
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Address") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Enter delivery notes") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }

        Text(
            text = "DELIVERY PRICE: $$deliveryPrice",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Text(
            text = "TOTAL: $$totalPrice",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Black
        )
    }
}