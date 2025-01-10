package com.example.projet_tdm.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.projet_tdm.R
import com.example.projet_tdm.screens.home.tabs.HomeTab
import com.example.projet_tdm.screens.home.tabs.NotificationsTab
import com.example.projet_tdm.screens.home.tabs.SearchTab
import com.example.projet_tdm.screens.home.tabs.TrackTab
import com.example.projet_tdm.screens.profile.ProfilePage
import com.example.projet_tdm.screens.settings.Setting

@Composable
fun HomeScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }
    Scaffold(
        bottomBar = {
            BottomNavigation(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .shadow(20.dp, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp), clip = false),
                backgroundColor = Color(0xFFFFFFFF),
                elevation = 50.dp // Optional: Adjust elevation if needed
            ) {
                BottomNavigationItem(

                    icon = { Icon( painter = painterResource(id = R.drawable.home_nav),modifier=Modifier.size(24.dp),
                        contentDescription = "Home")},
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                BottomNavigationItem(
                    icon = { Icon( painter = painterResource(id = R.drawable.search),modifier=Modifier.size(24.dp),
                        contentDescription = "Cart") },
                    modifier=Modifier.size(50.dp),
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
                BottomNavigationItem(
                    icon = { Icon( painter = painterResource(id = R.drawable.track_nav),modifier=Modifier.size(24.dp),
                        contentDescription = "Track") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )
                BottomNavigationItem(
                    icon = { Icon( painter = painterResource(id = R.drawable.notif_nav),modifier=Modifier.size(24.dp),
                        contentDescription = "Notifications") },
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 }
                )
                BottomNavigationItem(
                    icon = { Icon( painter = painterResource(id = R.drawable.profile),modifier=Modifier.size(24.dp), contentDescription = "Profile") },
                    selected = selectedTab == 4,
                    onClick = { selectedTab = 4 }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> HomeTab(navController)
                1 -> SearchTab(navController)
                2 -> TrackTab(
                startTrackingTime = true,
                onBackClick = { selectedTab = 0 },  // Navigate back to home tab
                driverNumber = "+1234567890"  // Replace with actual driver number
            )
                3 -> NotificationsTab()
                4 -> ProfileContent(navController)
            }
        }
    }
}

// Create separate composables for each tab content
@Composable
fun HomeContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        // Home tab content
        Text("Home Content")
    }
}

@Composable
fun CartContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        // Search tab content
        Text("Carttttt Content")
    }
}

@Composable
fun TrackContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        // Track tab content
        Text("Track Content")
    }
}

@Composable
fun NotificationsContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        // Notifications tab content
        Text("Notifications Content")
    }
}

@Composable
fun ProfileContent(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Profile tab content
        Setting(navController)
    }
}