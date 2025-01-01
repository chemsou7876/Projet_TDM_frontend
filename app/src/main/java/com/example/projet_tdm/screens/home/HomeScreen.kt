package com.example.projet_tdm.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.projet_tdm.screens.profile.ProfilePage

@Composable
fun HomeScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                    label = { Text("Search") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.LocationOn, contentDescription = "Track") },
                    label = { Text("Track") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Notifications, contentDescription = "Notifications") },
                    label = { Text("Notifications") },
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 }
                )
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = selectedTab == 4,
                    onClick = { selectedTab = 4 }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> HomeContent()
                1 -> SearchContent()
                2 -> TrackContent()
                3 -> NotificationsContent()
                4 -> ProfileContent()
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
fun SearchContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        // Search tab content
        Text("Search Content")
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
fun ProfileContent() {
    Column(modifier = Modifier.fillMaxSize()) {
        // Profile tab content
        ProfilePage()
    }
}