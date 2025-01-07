package com.example.projet_tdm.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.projet_tdm.R

@Composable
fun Adresse_card(title : String , desc : String , type : Int , onEditClick: () -> Unit,
                 onDeleteClick: () -> Unit){
    val pic =if (type==1) R.drawable.ic_home else R.drawable.ic_work
    Column (modifier = Modifier.padding(vertical = 10.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(16.dp))
                .background(Color(0xFFF0F5FA))
                .padding(12.dp)
        ) {


            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id =pic),
                    contentDescription = "Default Profile Image",
                    modifier = Modifier.size(60.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(18.dp))
                Column() {
                 Row ( modifier = Modifier.fillMaxWidth(),
                     horizontalArrangement = Arrangement.SpaceBetween) {

                    Text(title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                     Row ( ) {
                         Image(
                             painter = painterResource(id = R.drawable.ic_edit_adr),
                             contentDescription = "Default Profile Image",
                             modifier = Modifier.clickable(
                                 onClick = {onEditClick()}
                             )
                          //   contentScale = ContentScale.Crop
                         )
                         Spacer(modifier = Modifier.width(18.dp))
                         Image(
                             painter = painterResource(id = R.drawable.ic_delete_adr),
                             contentDescription = "Default Profile Image",
                             modifier = Modifier.clickable(
                                 onClick = {onDeleteClick()}
                             )
                             //   contentScale = ContentScale.Crop
                         )
                     }
                 }
                    Spacer(modifier = Modifier.height(18.dp))
                    Text(desc , color = Color(0xFF32343E))
                }
            }
        }
    }
}


