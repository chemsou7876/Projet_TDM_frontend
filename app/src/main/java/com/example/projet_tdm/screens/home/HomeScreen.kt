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
import com.example.projet_tdm.screens.home.tabs.ProfilTab
import com.example.projet_tdm.screens.home.tabs.SearchTab
import com.example.projet_tdm.screens.home.tabs.TrackTab
import com.example.projet_tdm.screens.profile.ProfilePage
import com.example.projet_tdm.screens.settings.Setting

@Composable
fun HomeScreen(navController: NavController,  selectTab: Int = 0) {

    // Get the selectedTab parameter from navigation
    var selectedTab by remember { mutableStateOf(selectTab) }
    val selectedTabParam = navController.currentBackStackEntry
        ?.arguments?.getString("selectedTab")?.toIntOrNull()



    LaunchedEffect(selectedTabParam) {
        TabNavigationState.setTabSelector { newTab ->
            selectedTab = newTab
        }
    }

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 24.dp,
                        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                        spotColor = Color.Black.copy(alpha = 0.25f),
                        ambientColor = Color.Black.copy(alpha = 0.25f)
                    )
            ) {
                BottomNavigation(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                    backgroundColor = Color(0xFFFaFaFa),
                    elevation = 0.dp
                ) {
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.home_nav),
                                modifier = Modifier.size(24.dp),
                                contentDescription = "Home"
                            )
                        },
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        alwaysShowLabel = false,
                        selectedContentColor = Color(0xFFFFA500),
                        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                    )
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.search),
                                modifier = Modifier.size(24.dp),
                                contentDescription = "Cart"
                            )
                        },
                        modifier = Modifier.size(50.dp),
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        alwaysShowLabel = false,
                        selectedContentColor = Color(0xFFFFA500),
                        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                    )
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.track_nav),
                                modifier = Modifier.size(24.dp),
                                contentDescription = "Track"
                            )
                        },
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        alwaysShowLabel = false,
                        selectedContentColor = Color(0xFFFFA500),
                        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                    )
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.notif_nav),
                                modifier = Modifier.size(24.dp),
                                contentDescription = "Notifications"
                            )
                        },
                        selected = selectedTab == 3,
                        onClick = { selectedTab = 3 },
                        alwaysShowLabel = false,
                        selectedContentColor = Color(0xFFFFA500),
                        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                    )
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.profile),
                                modifier = Modifier.size(24.dp),
                                contentDescription = "Profile"
                            )
                        },
                        selected = selectedTab == 4,
                        onClick = { selectedTab = 4 },
                        alwaysShowLabel = false,
                        selectedContentColor = Color(0xFFFFA500),
                        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled)
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedTab) {
                0 -> HomeTab(navController)
                1 -> SearchTab(navController)
                2 -> TrackTab(
                    startTrackingTime = true,
                  //  onBackClick = { selectedTab = 0 },
                )
                3 -> NotificationsTab()
                4 -> ProfilTab(navController)
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