package com.example.projet_tdm
// app/src/main/java/com/yourpackage/MainActivity.kt


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import com.example.projet_tdm.navigation.AppNavigation
import com.example.projet_tdm.services.ApiInfoClient
import com.example.projet_tdm.services.InfoRequest
import com.example.projet_tdm.services.InfoResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {



    // Add this to track the current deep link URI
    private val deepLinkUri = mutableStateOf<Uri?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set initial deep link URI
        deepLinkUri.value = intent?.data
        handleIntent(intent)

        setContent {
            AppNavigation(deepLinkUri.value)
        }
    }

    override fun onNewIntent(newIntent: Intent) {
        super.onNewIntent(newIntent)
        setIntent(newIntent)
        deepLinkUri.value = newIntent.data
        handleIntent(newIntent)
    }

    private fun handleIntent(intent: Intent?) {
        Log.d("DeepLinkDebug", "handleIntent called")

        if (intent == null) {
            Log.d("DeepLinkDebug", "Intent is null")
            return
        }

        val data = intent.data
        if (data == null) {
            Log.d("DeepLinkDebug", "Intent data is null")
            return
        }

        if (data.scheme == "myapp" && data.host == "auth" && data.path == "/google/callback") {
            val userId = data.getQueryParameter("id")
            if (userId != null) {
                Log.d("DeepLinkDebug", "User ID: $userId")
                getInfo(userId)
            }
        }
    }

    private fun getInfo(id: String?) {
        val infoService = ApiInfoClient.authService
        val request = InfoRequest(id = id)
        println("request")
        println(request)
        infoService.getInfo(request).enqueue(object : Callback<InfoResponse> {
            override fun onResponse(call: Call<InfoResponse>, response: Response<InfoResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    println(result)
                    if (result?.user != null) {
                        val sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()

                        editor.putBoolean("is_logged_in", true)
                        editor.putString("user_id", id)
                        editor.putString("user_name", result.user.name)
                        editor.putString("user_email", result.user.email)
                        editor.putString("user_phoneNumber", result.user.phoneNumber)
                        editor.putString("user_profilePicture", result.user.profilePicture)
                        editor.putString("user_bio", result.user.bio)
                        val gson = Gson()
                        val addressesJson = gson.toJson(result.user.addresses) // user.addresses is a List<Address>
                        editor.putString("user_addresses", addressesJson)
                        editor.apply()
                    }
                }
            }

            override fun onFailure(call: Call<InfoResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }
}