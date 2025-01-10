package com.example.projet_tdm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BackButton(onClick: () -> Unit, Color_bg: Color, IconColor: Color, BorderColor: Color = Color.Transparent) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .size(45.dp)
            .border(0.5.dp, BorderColor, CircleShape)
            .background(Color_bg, CircleShape)
            .clickable(indication = null,interactionSource = remember { MutableInteractionSource() },) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowLeft,
            contentDescription = "Back",
            tint = IconColor,
            modifier = Modifier.size(24.dp)
        )
    }
}