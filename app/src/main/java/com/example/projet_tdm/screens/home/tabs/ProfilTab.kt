package com.example.projet_tdm.screens.home.tabs

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projet_tdm.R
import com.example.projet_tdm.services.UserSession
import com.example.projet_tdm.ui.theme.Sen


fun logout(context: Context, navController: NavController) {
    // Clear UserSession
    UserSession.userId = null
    UserSession.isLoggedIn = false

    // Clear SharedPreferences
    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.clear()  // Removes all stored data in "user_session"
    editor.apply()

    navController.navigate("onboarding") {
        popUpTo("home") { inclusive = true }
    }
}

@Composable
fun ProfilTab(navController: NavController){
    val context = LocalContext.current
    val defaultProfileImage = painterResource(id = R.drawable.profile_pic)
    val sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    val userName = sharedPreferences.getString("user_name", "No Name")
    val userEmail = sharedPreferences.getString("user_email", "No Email")
    val userPhone = sharedPreferences.getString("user_phoneNumber", "No phone number")
    val userBio = sharedPreferences.getString("user_bio", "Write your bio")


    Column(modifier = Modifier.padding(vertical = 10.dp , horizontal = 20.dp).fillMaxSize()
        //   .verticalScroll(scrollState) ,
        , verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
            Text(
                text = "Profile",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 8.dp),
                color = Color(0xFF303030),
                fontSize = 23.sp,
                fontFamily = Sen,
                fontWeight = FontWeight.Bold
            )
            Image(
                painter = painterResource(id = R.drawable.ic_more),
                contentDescription = "Default Profile Image",
                modifier = Modifier.size(55.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        //navController.navigate("home") // Navigate to the HomeScreen when clicked
                    },
                contentScale = ContentScale.Crop
            )
        }

        Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
            Image(
                painter = defaultProfileImage,
                contentDescription = "Default Profile Image",
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column (){
                if (userName != null) {
                    Text(userName, fontSize = 20.sp, fontWeight = FontWeight.W700,fontFamily = Sen)
                }
                Spacer(modifier = Modifier.height(12.dp))
                if (userBio != null) {
                    Text(userBio, fontSize = 16.sp, fontWeight = FontWeight.W500,fontFamily = Sen)
                }
            } }

        Column (modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = Color(0xFFF6F8FA))
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
                    Text("FULL NAME", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, fontFamily = Sen)
                    Spacer(modifier = Modifier.height(8.dp))
                    if (userName != null) {
                        Text(userName, fontFamily = Sen)
                    }
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
                    Text("EMAIL", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, fontFamily = Sen)
                    Spacer(modifier = Modifier.height(8.dp))
                    if (userEmail != null) {
                        Text(userEmail)
                    }
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
                    Text("PHONE NUMBER", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, fontFamily = Sen)
                    Spacer(modifier = Modifier.height(8.dp))
                    if (userPhone != null) {
                        Text(userPhone, fontFamily = Sen)
                    }
                } }
        }

        //  Spacer(modifier = Modifier.height(25.dp))
        Column (modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = Color(0xFFF6F8FA))
            .padding(20.dp)

        ){
            Row (modifier = Modifier.fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    navController.navigate("edit_profile") // Navigate to the HomeScreen when clicked
                },
                verticalAlignment = Alignment.CenterVertically
                , horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically){
                    Image(
                        painter = painterResource(id = R.drawable.ic_pen),
                        contentDescription = "Default Profile Image",
                        modifier = Modifier.size(50.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(18.dp))

                    Text("Edit profile", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, fontFamily = Sen)
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = "Default Profile Image",

                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row (modifier = Modifier.fillMaxWidth()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    navController.navigate("adresses")
                }
                , verticalAlignment = Alignment.CenterVertically
                , horizontalArrangement = Arrangement.SpaceBetween) {

                Row(verticalAlignment = Alignment.CenterVertically){
                    Image(
                        painter = painterResource(id = R.drawable.ic_adresses),
                        contentDescription = "Default Profile Image",
                        modifier = Modifier.size(50.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(18.dp))

                    Text("Addresses", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, fontFamily = Sen)
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = "Default Profile Image",
                    modifier = Modifier,
                    contentScale = ContentScale.Crop
                )
            }
        }
        Column (modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(16.dp))
            .background(color = Color(0xFFF6F8FA))
            .padding(20.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {

                logout(context = context, navController)
            }

        ){
            Row (modifier = Modifier
                .fillMaxWidth(),

                verticalAlignment = Alignment.CenterVertically
                , horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically){
                    Image(
                        painter = painterResource(id = R.drawable.ic_logout),
                        contentDescription = "Default Profile Image",
                        modifier = Modifier.size(50.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(18.dp))

                    Text("Log Out", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, fontFamily = Sen)
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = "Default Profile Image",

                    contentScale = ContentScale.Crop
                )
            }




        }

    }
}