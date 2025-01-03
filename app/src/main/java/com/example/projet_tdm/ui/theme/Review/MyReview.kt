package com.example.projet_tdm.ui.theme.Review

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.tooling.preview.Preview

class MyReview(private val context: Context) {

    private lateinit var stars: Array<ImageView>  // Déclaration globale des étoiles

    fun show() {
        val dialog = Dialog(context)
        dialog.setContentView(createPopupView(dialog))
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.show()
    }

    private fun createPopupView(dialog: Dialog): View {
        val popupLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 40, 40, 40)
            background = GradientDrawable().apply {
                setColor(Color.WHITE)
                cornerRadius = 30f
            }
        }

        // Title
        val titleText = TextView(context).apply {
            text = "Rate Your Experience!"
            textSize = 24f
            setTextColor(Color.parseColor("#FF6D00"))
            gravity = Gravity.CENTER
        }
        popupLayout.addView(titleText)

        // Star Rating
        val starLayout = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setPadding(0, 20, 0, 20)
        }

        stars = Array(5) { i ->
            ImageView(context).apply {
                setImageResource(android.R.drawable.star_off)
                setOnClickListener {
                    updateStars(i + 1)  // Met à jour la note avec l'indice de l'étoile
                }
            }
        }

        stars.forEach { starLayout.addView(it) }
        popupLayout.addView(starLayout)

        // Feedback Input Section (with border radius and gray background)
        val feedbackLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 20, 20, 20)
            background = GradientDrawable().apply {
                setColor(Color.parseColor("#F5F5F5"))
                cornerRadius = 20f
            }
        }

        // Feedback Input (Editable Text) - "Enter your feedback here"
        val feedbackInput = EditText(context).apply {
            hint = "Enter your feedback here"
            setBackgroundColor(Color.parseColor("#F5F5F5"))
            setPadding(20, 10, 20, 10)
            setHeight(200) // Aggrandir la zone de texte
            setTextColor(Color.BLACK)
            setTypeface(null, android.graphics.Typeface.BOLD)
        }

        feedbackLayout.addView(feedbackInput)
        popupLayout.addView(feedbackLayout)

        // Add space between the feedback input and the submit button
        val spacer = View(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                20 // Adjust the space as needed
            )
        }
        popupLayout.addView(spacer)

        // Submit Button
        val submitButton = Button(context).apply {
            text = "SUBMIT FEEDBACK"
            setBackgroundColor(Color.parseColor("#FF6D00"))
            setTextColor(Color.WHITE)
            setPadding(16, 16, 16, 16)
            setOnClickListener {
                // Handle the submission logic here (e.g., send feedback to backend)
                Toast.makeText(context, "Feedback Submitted!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            // Apply rounded corners to the button
            val drawable = GradientDrawable().apply {
                cornerRadius = 20f
                setColor(Color.parseColor("#FF6D00"))
            }
            background = drawable
        }
        popupLayout.addView(submitButton)

        return popupLayout
    }

    private fun updateStars(rating: Int) {
        // Met à jour les étoiles en fonction de la note donnée
        for (i in stars.indices) {
            stars[i].setImageResource(
                if (i < rating) android.R.drawable.star_on else android.R.drawable.star_off
            )
        }
    }
}
