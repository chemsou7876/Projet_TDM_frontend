package com.example.projet_tdm.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OrangeButton(text: String , onClick: () -> Unit){
    TextButton(onClick = {onClick()
    },
        modifier = Modifier.fillMaxWidth().height(60.dp).clip(shape = RoundedCornerShape(10.dp)),
        colors =  ButtonDefaults.textButtonColors(Color(0xFFFF7622))) {
        Text(text, fontSize = 18.sp
            , color = Color.White) }
}