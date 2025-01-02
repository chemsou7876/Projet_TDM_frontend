package com.example.projet_tdm.screens.settings

import android.text.Layout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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

@Composable
fun Setting(navController: NavController){
    val defaultProfileImage = painterResource(id = R.drawable.profile_pic)

    Column(modifier = Modifier.padding((12.dp)).fillMaxSize()
        //   .verticalScroll(scrollState) ,
        , verticalArrangement = Arrangement.SpaceBetween
    ) {
    Row (modifier = Modifier.fillMaxWidth().padding(top = 20.dp), horizontalArrangement = Arrangement.SpaceBetween){
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.back_icon),
                contentDescription = "Default Profile Image",
                modifier = Modifier.size(55.dp).clickable {
                    //navController.navigate("home") // Navigate to the HomeScreen when clicked
                },
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Profile",
                fontSize = 20.sp
            )

        }
        Image(
            painter = painterResource(id = R.drawable.ic_more),
            contentDescription = "Default Profile Image",
            modifier = Modifier.size(55.dp)
                .clickable {
                //navController.navigate("home") // Navigate to the HomeScreen when clicked
            },
            contentScale = ContentScale.Crop
        )}

        Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
            Image(
                painter = defaultProfileImage,
                contentDescription = "Default Profile Image",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column (){
                Text("Vishal Khadok", fontSize = 20.sp, fontWeight = FontWeight.W700)
                Spacer(modifier = Modifier.height(12.dp))
                Text("I love fast food")
            } }

        Column (modifier = Modifier.background(color = Color(0xFFF0F5FA))
            .fillMaxWidth()
            .padding(20.dp)
        ){
            Row(verticalAlignment = Alignment.CenterVertically){
                Image(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "Default Profile Image",
                    modifier = Modifier.size(50.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(18.dp))
                Column (){
                    Text("FULL NAME", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("I love fast food")
                } }
            Spacer(modifier = Modifier.height(15.dp))
            Row(verticalAlignment = Alignment.CenterVertically){
                Image(
                    painter = painterResource(id = R.drawable.ic_email),
                    contentDescription = "Default Profile Image",
                    modifier = Modifier.size(50.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(18.dp))
                Column (){
                    Text("EMAIL", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("I love fast food")
                } }
            Spacer(modifier = Modifier.height(15.dp))

            Row(verticalAlignment = Alignment.CenterVertically){
                Image(
                    painter = painterResource(id = R.drawable.ic_phone),
                    contentDescription = "Default Profile Image",
                    modifier = Modifier.size(50.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(18.dp))
                Column (){
                    Text("PHONE NUMBER", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("I love fast food")
                } }
        }

      //  Spacer(modifier = Modifier.height(25.dp))
        Column (modifier = Modifier.background(color = Color(0xFFF0F5FA))
            .fillMaxWidth()
            .padding(20.dp)
        ){
            Row (modifier = Modifier.fillMaxWidth() , verticalAlignment = Alignment.CenterVertically
                , horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically){
                    Image(
                        painter = painterResource(id = R.drawable.ic_pen),
                        contentDescription = "Default Profile Image",
                        modifier = Modifier.size(50.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(18.dp))

                    Text("Edit profile", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = "Default Profile Image",
                    modifier = Modifier.clickable {
                        //navController.navigate("home") // Navigate to the HomeScreen when clicked
                    },
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row (modifier = Modifier.fillMaxWidth() , verticalAlignment = Alignment.CenterVertically
                , horizontalArrangement = Arrangement.SpaceBetween) {

                Row(verticalAlignment = Alignment.CenterVertically){
                    Image(
                        painter = painterResource(id = R.drawable.ic_adresses),
                        contentDescription = "Default Profile Image",
                        modifier = Modifier.size(50.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(18.dp))

                    Text("Addresses", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = "Default Profile Image",
                    modifier = Modifier,
                    contentScale = ContentScale.Crop
                )
            }
        }
        Column (modifier = Modifier.background(color = Color(0xFFF0F5FA))
            .fillMaxWidth()
            .padding(20.dp)
        ){
            Row (modifier = Modifier.fillMaxWidth() , verticalAlignment = Alignment.CenterVertically
                , horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically){
                    Image(
                        painter = painterResource(id = R.drawable.ic_logout),
                        contentDescription = "Default Profile Image",
                        modifier = Modifier.size(50.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(18.dp))

                    Text("Log Out", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = "Default Profile Image",
                    modifier = Modifier.clickable {
                        //navController.navigate("home") // Navigate to the HomeScreen when clicked
                    },
                    contentScale = ContentScale.Crop
                )
            }




        }

    }
}