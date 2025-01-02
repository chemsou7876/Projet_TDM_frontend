package com.example.projet_tdm.screens.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.projet_tdm.R
import kotlinx.coroutines.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.projet_tdm.ui.theme.Sen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(navController: NavController) {
    val images = listOf(R.drawable.onboarding1, R.drawable.onboarding2, R.drawable.onboarding3)
    val titles = listOf(
        "All your favorites",
        "Order from choosen chef",
        "Free delivery offers"
    )
    val descriptions = listOf(
        "Get all your loved foods in one once place,you just place the orer we do the rest",
        "Get all your loved foods in one once place,you just place the order we do the rest",
        "Get all your loved foods in one once place,you just place the orer we do the rest"
    )
    val pagerState = rememberPagerState(pageCount = { images.size })
    val scope = rememberCoroutineScope()


    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = images[page]),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f))
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    // Title
                    Text(
                        text = titles[page],
                        color = Color.White,
                        fontSize = 24.sp,
                        fontFamily = Sen,
                        fontWeight = FontWeight.W800,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))  // Gap between title and desc

                    // Description
                    Text(
                        text = descriptions[page],
                        color = Color.White,
                        fontFamily = Sen,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))  // Gap between desc and pagination

                    // Pagination Indicators
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(images.size) { iteration ->
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (pagerState.currentPage == iteration)
                                            Color.White
                                        else
                                            Color.White.copy(alpha = 0.5f)
                                    )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))  // Gap between pagination and button

                    Button(
                        onClick = {
                            if (pagerState.currentPage < images.size - 1) {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            } else {
                                navController.navigate("login")
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(62.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFFF7622)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = if (pagerState.currentPage < images.size - 1) "NEXT" else "GET STARTED",
                            color = Color.White,
                            fontFamily = Sen,
                            fontWeight = FontWeight.W700,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }

        }
    }
}