package com.example.projet_tdm.screens.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.projet_tdm.R
import com.example.projet_tdm.ui.theme.Sen
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun LocationScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Permission states
    var hasLocationPermission by remember {
        mutableStateOf(
            checkLocationPermission(context)
        )
    }

    // Permission launcher
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        hasLocationPermission = permissions.values.all { it }
        if (hasLocationPermission) {
            // Get location immediately after permission is granted
            scope.launch {
                getCurrentLocation(context) { lat, lng ->
                    saveLocation(context, lat, lng)
                    navController.navigate("home/0") {
                        popUpTo("splash") { inclusive = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    }

    // Check permission on launch
    LaunchedEffect(Unit) {
        if (!hasLocationPermission) {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.localisaton_illustration),
            contentDescription = "Location",
            modifier = Modifier.size(245.dp)
        )
        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                if (hasLocationPermission) {
                    scope.launch {
                        isLoading = true
                        try {
                            getCurrentLocation(context) { lat, lng ->
                                saveLocation(context, lat, lng)
                                navController.navigate("home/0") {
                                    popUpTo("splash") { inclusive = true }
                                }
                            }
                        } catch (e: Exception) {
                            errorMessage = e.message
                        } finally {
                            isLoading = false
                        }
                    }
                } else {
                    locationPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF7622)),
            shape = RoundedCornerShape(12.dp),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White)
            } else {
                Text(
                    if (hasLocationPermission) "ACCESS LOCATION" else "GRANT LOCATION PERMISSION",
                    color = Color.White,
                    fontWeight = FontWeight.W800,
                    fontFamily = Sen
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        errorMessage?.let { error ->
            Text(
                text = error,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "DFOOD WILL ACCESS YOUR LOCATION ONLY WHILE USING THE APP",
            textAlign = TextAlign.Center,
            fontFamily = Sen,
            fontWeight = FontWeight.W400,
            color = Color(0xFF646982),
            modifier = Modifier.padding(bottom = 32.dp)
        )
    }
}

// Helper functions
private fun checkLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
}


private suspend fun getCurrentLocation(
    context: Context,
    onLocationReceived: (Double, Double) -> Unit
) {
    // Explicitly check permissions before accessing location
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED ||
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        throw SecurityException("Location permissions not granted")
    }

    try {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        val location = fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            object : CancellationToken() {
                override fun onCanceledRequested(listener: OnTokenCanceledListener) =
                    CancellationTokenSource().token
                override fun isCancellationRequested() = false
            }
        ).await()

        location?.let {
            onLocationReceived(it.latitude, it.longitude)
        } ?: throw Exception("Unable to get location")

    } catch (e: SecurityException) {
        throw Exception("Location permission denied: ${e.message}")
    } catch (e: Exception) {
        throw Exception("Failed to get location: ${e.message}")
    }
}

private fun saveLocation(context: Context, latitude: Double, longitude: Double) {
    val sharedPrefs = context.getSharedPreferences("location_prefs", Context.MODE_PRIVATE)
    with(sharedPrefs.edit()) {
        putFloat("latitude", latitude.toFloat())
        putFloat("longitude", longitude.toFloat())
        apply()
    }
}
fun getLocationFromLocalStorage(context: Context): String {
    val sharedPrefs = context.getSharedPreferences("location_prefs", Context.MODE_PRIVATE)
    val latitude = sharedPrefs.getFloat("latitude", 0.0f)
    val longitude = sharedPrefs.getFloat("longitude", 0.0f)
    return "Lat: $latitude, Lng: $longitude"
}