package com.example.projet_tdm.screens.home


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
import androidx.navigation.NavController
import com.example.projet_tdm.R

@Composable
fun HomeScreen(navController: NavController) {
    //  val images = listOf(R.drawable.onboarding1, R.drawable.onboarding2, R.drawable.onboarding3)
    //  val pagerState = rememberPagerState(pageCount = { images.size })

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
//        HorizontalPager(
//            state = pagerState,
//            modifier = Modifier
//                .weight(1f)
//                .fillMaxWidth()
//        ) { page ->
//            Image(
//                painter = painterResource(id = images[page]),
//                contentDescription = null,
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.Crop
//            )
//        }
//
//        Button(
//            onClick = {
//                if (pagerState.currentPage < images.size - 1) {
//                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
//                } else {
//                    navController.navigate("login")
//                }
//            },
//            modifier = Modifier
//                .padding(16.dp)
//                .fillMaxWidth()
//        ) {
//            Text(if (pagerState.currentPage < images.size - 1) "Next" else "Get Started")
//        }
        Text("this is home page ")
    }
}