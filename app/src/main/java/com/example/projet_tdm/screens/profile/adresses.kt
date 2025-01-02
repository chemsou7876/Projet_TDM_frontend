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

@Composable
fun Adresses(){

    var items by remember {
        mutableStateOf(
            listOf(
                Triple("HOME", "2464 Royal Ln. Mesa, New Jersey 45463", 1),
                Triple("WORK", "1321 Park Ave. San Diego, California 92101", 2),
                Triple("SCHOOL", "785 High School Rd. Seattle, Washington 98103", 1)
            )
        )
    }
    //val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .padding(vertical = 15.dp , horizontal = 6.dp)
        .fillMaxSize()
        //   .verticalScroll(scrollState) ,
        , verticalArrangement = Arrangement.SpaceBetween
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.back_icon),
                contentDescription = "Default Profile Image",
                modifier = Modifier
                    .size(55.dp)
                    .clickable {
                        //navController.navigate("settings") // Navigate to the HomeScreen when clicked
                    },
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text ="My Addresses",
                fontSize = 20.sp)

        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(items.size) { index -> // Loop through items
                val item = items[index]
                Adresse_card(
                    title = item.first,
                    desc = item.second,
                    type = item.third,
                    onEditClick = {
                        println("Edit clicked for ${item.first}")
                    },
                    onDeleteClick = {
                        // Update the state to remove the clicked item
                        items = items.filterIndexed { i, _ -> i != index }
                    }
                )
            }
        }

    }
}


