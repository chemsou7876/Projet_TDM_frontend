package com.example.projet_tdm.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.TextButton
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
import com.example.projet_tdm.ui.theme.Sen
import androidx.compose.material3.RangeSlider

data class PricingState(
    val priceRange: ClosedFloatingPointRange<Float> = 0f..10000f,
    val currentRange: ClosedFloatingPointRange<Float> = 0f..10000f
)

@Composable
fun FilterDialog(
    onDismiss: () -> Unit,
    onFilterApply: (FilterState) -> Unit
) {
    var selectedDeliveryTime by remember { mutableStateOf(DeliveryTimeState()) }
    var selectedPricing by remember { mutableStateOf(PricingState()) }
    var selectedRating by remember { mutableStateOf(RatingState()) }
    var sliderPosition by remember { mutableStateOf(0f) }

    // Calculate the current price based on slider position

    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Previous header code remains the same...
                Row(
                    modifier = Modifier.fillMaxWidth(),

                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Filter your search",
                        fontSize = 20.sp,
                        fontFamily = Sen,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = { onDismiss() }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color(0xFFA0A5BA)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))

                // Delivery Time section remains the same...
                Text(
                    text = "DELIVER TIME",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    fontFamily = Sen,
                    color = Color(0xFF32343E)
                )
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

                // Updated Pricing Section
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PRICING",
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            fontFamily = Sen,
                            color = Color(0xFF32343E)
                        )
                        Text(
                            text = "DA ${selectedPricing.currentRange.start.toInt()} - ${selectedPricing.currentRange.endInclusive.toInt()}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            fontFamily = Sen,
                            color = Color(0xFFFF7622)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    RangeSlider(
                        value = selectedPricing.currentRange,
                        onValueChange = { range ->
                            selectedPricing = selectedPricing.copy(currentRange = range)
                        },
                        valueRange = selectedPricing.priceRange,
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFFFF7622),
                            activeTrackColor = Color(0xFFFF7622),
                            inactiveTrackColor = Color.LightGray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "DA ${selectedPricing.priceRange.start.toInt()}",
                            fontSize = 12.sp,
                            fontFamily = Sen,
                            color = Color.Gray
                        )
                        Text(
                            text = "DA ${selectedPricing.priceRange.endInclusive.toInt()}",
                            fontSize = 12.sp,
                            fontFamily = Sen,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Rating section remains the same...
                Text(
                    text = "RATING",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    fontFamily = Sen,
                    color = Color(0xFF32343E)
                )
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

                // Filter button remains the same...
                TextButton(
                    onClick = { onFilterApply(FilterState(selectedDeliveryTime, selectedPricing, selectedRating)) },
                    modifier = Modifier.fillMaxWidth().height(60.dp).clip(shape = RoundedCornerShape(10.dp)),
                    colors =  androidx.compose.material.ButtonDefaults.textButtonColors(Color(0xFFFF7622))
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
            .background(if (selected) Color(0xFFFFA500) else Color.Transparent)
            .border(1.dp,if (selected) Color(0x00FFA500) else Color(0xFFEDEDED), RoundedCornerShape(50))
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onSelected(!selected) }

    ) {
        Text(
            text = label,
            color = if (selected) Color.White else Color.Black,
            fontSize = 14.sp,
            fontFamily = Sen,
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