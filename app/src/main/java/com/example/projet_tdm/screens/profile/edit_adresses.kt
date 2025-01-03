package com.example.projet_tdm.screens.profile


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Colors
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.projet_tdm.R
import com.example.projet_tdm.components.Adresse_card
import com.example.projet_tdm.components.OrangeButton

@Composable
fun Edit_adresses(navController: NavController){


   var adress by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf(1) }


    Column(modifier = Modifier
        .padding(vertical = 15.dp , horizontal = 6.dp)
        .fillMaxSize()
        //   .verticalScroll(scrollState) ,
        , verticalArrangement = Arrangement.SpaceBetween
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.ic_back_blk),
                contentDescription = "Default Profile Image",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        navController.navigate("adresses") //navigate to the settigns page
                    },
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text ="My Addresses",
                fontSize = 20.sp)

        }
Column {
    Column (modifier = Modifier.padding(vertical = 8.dp)) {
        Text("ADRESSES",
            fontSize = 18.sp ,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 5.dp))
        OutlinedTextField(
            value = adress,
            onValueChange = {adress = it},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("3235 Royal Ln. mesa, new jersy 34567", color = Color(0xFF6B6E82)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_location),
                    contentDescription = "Location Icon",
                    tint = Color(0xFF6B6E82)
                )
            },)
        Spacer(modifier = Modifier.height(10.dp))
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween  ){
            Column ( modifier = Modifier
                .weight(1f) // Prend une partie égale de l'espace disponible
                .padding(end = 8.dp),){
                Text("STREET",
                    fontSize = 18.sp ,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(bottom = 5.dp))
                OutlinedTextField(
                    value = adress,
                    onValueChange = {adress = it},

                    placeholder = { Text("hason nagar", color = Color(0xFF6B6E82)) },
                )
            }
            // Spacer(modifier = Modifier.height(60.dp))
            Column (   modifier = Modifier
                .weight(1f),){
                Text("POST CODE",
                    fontSize = 18.sp ,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(bottom = 5.dp))
                OutlinedTextField(
                    value = adress,
                    onValueChange = {adress = it},

                    placeholder = { Text("34567", color = Color(0xFF6B6E82)) },
                )
            }

        }
        Spacer(modifier = Modifier.height(10.dp))
        Column {
            Text("APPARTMENT",
                fontSize = 18.sp ,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 5.dp))
            OutlinedTextField(
                value = adress,
                onValueChange = {adress = it},
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("345", color = Color(0xFF6B6E82)) },
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Column {
            Text("LABEL AS",
                fontSize = 18.sp ,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 5.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                //  .padding(16.dp),
                ,  horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Les trois éléments
                for (option in 1..3) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 5.dp)
                            .height(40.dp)
                            // .size(80.dp) // Taille de chaque élément
                            .clip(RoundedCornerShape(16.dp)) // Coins arrondis
                            .background(
                                if (option == selectedOption) Color(0xFFF58D1D) else Color(0xFFF0F5FA) // Couleur selon sélection
                            )
                            .clickable {
                                selectedOption = option // Met à jour l'élément sélectionné
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text =  if (option == 2) "WORK" else if (option == 1) "HOME" else "OTHER",
                            color =  if (option == selectedOption)  Color.White else Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
}

            OrangeButton("Save location" , onClick = {})

        }


    }
}


