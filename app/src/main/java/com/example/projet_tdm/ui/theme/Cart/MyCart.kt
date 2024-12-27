package com.example.projet_tdm.ui.theme.Cart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCart() {
    Scaffold(
        topBar = { CartTopBar() },
        content = { innerPadding ->
            CartContent(Modifier.padding(innerPadding))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartTopBar() {
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
        navigationIcon = {
            IconButton(onClick = { /* Action retour */ }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        },
        actions = {
            TextButton(onClick = { /* Action modifier les articles */ }) {
                Text(text = "EDIT ITEMS", color = Color(0xFFFFA500))
            }
        }
    )
}

@Composable
fun CartContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Liste des articles dans le panier
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(2) { index -> // Exemple d'articles fictifs
                CartItem(
                    name = if (index == 0) "Pizza Calzone" else "Pasta Carbonara",
                    price = if (index == 0) 64 else 32,
                    quantity = if (index == 0) 2 else 1
                )
            }
        }

        // Section d'informations sur la livraison
        Spacer(modifier = Modifier.height(16.dp))
        DeliveryInfoSection(


        )

        // Bouton de commande
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Action passer la commande */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
        ) {
            Text(text = "PLACE ORDER", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun CartItem(name: String, price: Int, quantity: Int) {
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
            Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = "Description", fontSize = 14.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "$$price", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { /* Diminuer la quantité */ }) {

            }
            Text(text = "$quantity", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            IconButton(onClick = { /* Augmenter la quantité */ }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Plus"
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeliveryInfoSection() {
    var address by remember { mutableStateOf("2118 Thornridge Cir. Syracuse") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF7F7F7), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "DELIVERY ADDRESS", fontSize = 14.sp, fontWeight = FontWeight.Bold)
//            TextButton(onClick = { /* Modifier l'adresse */ }) {
//                Text(text = "EDIT", color = Color(0xFFFFA500))
//            }
        }

        TextField(
            value = address,
            onValueChange = { address = it },
            placeholder = { Text(text = "Enter your address") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(1.dp, Color.Transparent, RoundedCornerShape(8.dp)),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "DELIVERY PRICE: $4", fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "TOTAL: $100", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//            TextButton(onClick = { /* Voir les détails */ }) {
//                Text(text = "Breakdown >", color = Color(0xFFFFA500))
//            }
        }
    }
}

