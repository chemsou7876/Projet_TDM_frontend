package com.example.projet_tdm.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun FilterDialog(
    onDismiss: () -> Unit, // Callback pour fermer la pop-up
    onFilterApply: (FilterState) -> Unit // Callback pour appliquer les filtres avec l'état des filtres
) {
    // État pour suivre la sélection des filtres
    var selectedDeliveryTime by remember { mutableStateOf(DeliveryTimeState()) }
    var selectedPricing by remember { mutableStateOf(PricingState()) }
    var selectedRating by remember { mutableStateOf(RatingState()) }
    var sliderPosition by remember { mutableStateOf(0f) }

    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Header avec le titre et le bouton "Fermer"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Filter your search",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = { onDismiss() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))

                // Section Delivery Time
                Text(text = "DELIVER TIME", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        label = "10-15 min",
                        selected = selectedDeliveryTime.time1,
                        onSelected = { selectedDeliveryTime = selectedDeliveryTime.copy(time1 = it) }
                    )
                    FilterChip(
                        label = "20 min",
                        selected = selectedDeliveryTime.time2,
                        onSelected = { selectedDeliveryTime = selectedDeliveryTime.copy(time2 = it) }
                    )
                    FilterChip(
                        label = "30 min",
                        selected = selectedDeliveryTime.time3,
                        onSelected = { selectedDeliveryTime = selectedDeliveryTime.copy(time3 = it) }
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Pricing Section
                Text(
                    text = "PRICING",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Slider(
                    value = sliderPosition,
                    onValueChange = { sliderPosition = it },
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFFFFA500),
                        activeTrackColor = Color(0xFFFFA500),
                        inactiveTrackColor = Color.LightGray
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Section Rating
                Text(text = "RATING", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    RatingStar(
                        selected = selectedRating.star1,
                        onSelected = { selectedRating = selectedRating.copy(star1 = it) }
                    )
                    RatingStar(
                        selected = selectedRating.star2,
                        onSelected = { selectedRating = selectedRating.copy(star2 = it) }
                    )
                    RatingStar(
                        selected = selectedRating.star3,
                        onSelected = { selectedRating = selectedRating.copy(star3 = it) }
                    )
                    RatingStar(
                        selected = selectedRating.star4,
                        onSelected = { selectedRating = selectedRating.copy(star4 = it) }
                    )
                    RatingStar(
                        selected = selectedRating.star5,
                        onSelected = { selectedRating = selectedRating.copy(star5 = it) }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Bouton Filter
                Button(
                    onClick = { onFilterApply(FilterState(selectedDeliveryTime, selectedPricing, selectedRating)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500))
                ) {
                    Text(text = "FILTER", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun FilterChip(label: String, selected: Boolean, onSelected: (Boolean) -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (selected) Color(0xFFFFA500) else Color.LightGray)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onSelected(!selected) }
    ) {
        Text(
            text = label,
            color = if (selected) Color.White else Color.Black,
            fontSize = 14.sp
        )
    }
}

@Composable
fun RatingStar(selected: Boolean, onSelected: (Boolean) -> Unit) {
    Icon(
        imageVector = if (selected) Icons.Filled.Star else Icons.Outlined.Star,
        contentDescription = null,
        tint = if (selected) Color(0xFFFFA500) else Color.LightGray,
        modifier = Modifier.clickable { onSelected(!selected) }
    )
}

// États des filtres
data class OffersState(
    val delivery: Boolean = false,
    val pickUp: Boolean = false,
    val offer: Boolean = false,
    val onlinePayment: Boolean = false
)

data class DeliveryTimeState(
    val time1: Boolean = false,
    val time2: Boolean = false,
    val time3: Boolean = false
)

data class PricingState(
    val value: Float = 0f
)


data class RatingState(
    val star1: Boolean = false,
    val star2: Boolean = false,
    val star3: Boolean = false,
    val star4: Boolean = false,
    val star5: Boolean = false
)

// Structure pour transmettre les filtres
data class FilterState(
    val deliveryTime: DeliveryTimeState,
    val pricing: PricingState,
    val rating: RatingState
)