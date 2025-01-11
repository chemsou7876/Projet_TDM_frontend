package com.example.projet_tdm.screens.Review

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.ViewCompat

class MyReview(private val context: Context) {

    private lateinit var stars: Array<ImageView>

    fun show() {
        val dialog = Dialog(context, android.R.style.Theme_DeviceDefault_Light_NoActionBar)
        dialog.setContentView(createPopupView(dialog))
        dialog.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT) // Full-screen dialog
            setGravity(Gravity.CENTER) // Center the popup
            setBackgroundDrawableResource(android.R.color.transparent) // Transparent background for the window
        }
        dialog.show()
    }

    private fun createPopupView(dialog: Dialog): View {
        // Main container with gray background that covers the entire screen
        val mainContainer = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.parseColor("#D3D3D3")) // Light gray background for the entire screen
            gravity = Gravity.CENTER // Center the popup layout within the gray background
            // Use MATCH_PARENT to make sure the background fills the entire screen
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        // Popup Layout with rounded corners on top
        val popupLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            background = GradientDrawable().apply {
                setColor(Color.WHITE)
                cornerRadii = floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f) // Rounded corners only at the top
            }
            setPadding(40, 40, 40, 40)
            // Add fixed height for the popup or use wrap content to adjust dynamically
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        // Title TextView
        val titleText = TextView(context).apply {
            text = "Rate Your Experience!"
            textSize = 20f
            setTextColor(Color.parseColor("#FF6D00"))
            gravity = Gravity.CENTER
            setPadding(0, 10, 0, 20)
        }
        popupLayout.addView(titleText)

        // Star Rating layout
        val starLayout = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(0, 20, 0, 10)
        }

        stars = Array(5) { i ->
            ImageView(context).apply {
                setImageResource(android.R.drawable.star_big_off)
                setOnClickListener {
                    updateStars(i + 1) // Update the star rating when clicked
                }
                setPadding(10, 0, 10, 0)  // Padding between stars
            }
        }

        // Add the stars to the star layout
        stars.forEach { starLayout.addView(it) }
        popupLayout.addView(starLayout)

        // Feedback Input Section
        val feedbackInput = EditText(context).apply {
            hint = "Feel free to share your feedback"
            setPadding(20, 20, 20, 100)
            setBackgroundColor(Color.parseColor("#F5F5F5"))
            background = GradientDrawable().apply {
                setColor(Color.parseColor("#F5F5F5"))
                cornerRadius = 20f
            }
            minHeight = 150 // Larger feedback box
            setTextColor(Color.BLACK)
            setHintTextColor(Color.GRAY)
        }
        popupLayout.addView(feedbackInput)

        // Submit Button
        val submitButton = Button(context).apply {
            text = "SUBMIT FEEDBACK"
            setBackgroundColor(Color.parseColor("#FF6D00"))
            setTextColor(Color.WHITE)
            textSize = 16f
            setPadding(16, 16, 16, 16)
            gravity = Gravity.CENTER
            background = GradientDrawable().apply {
                setColor(Color.parseColor("#FF6D00"))
                cornerRadius = 20f

            }
            setOnClickListener {
                Toast.makeText(context, "Feedback Submitted!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }

        val buttonLayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, 20, 0, 0)
        }

        popupLayout.addView(submitButton, buttonLayoutParams)

        mainContainer.addView(popupLayout)
        return mainContainer
    }

    // Update the stars based on the rating
    private fun updateStars(rating: Int) {
        for (i in stars.indices) {
            stars[i].setImageResource(
                if (i < rating) android.R.drawable.star_big_on else android.R.drawable.star_big_off
            )
        }
    }
}
