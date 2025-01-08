package com.example.projet_tdm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projet_tdm.ui.theme.Sen

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    isEmail: Boolean = false,
    error: String? = null,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Label
        Text(
            text = label.uppercase(),
            fontFamily = Sen,
            fontWeight = FontWeight.W400,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            color = Color(0xFF32343E),
            fontSize = 12.sp
        )

        // Text field
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    placeholder,
                    color = Color(0xFFA0A5BA),
                    fontWeight = FontWeight.W400,
                    fontFamily = Sen
                )
            },
            isError = error != null,
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isEmail) KeyboardType.Email else KeyboardType.Text
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = if (error != null) 4.dp else 16.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(Color(0xFFF0F5FA)),
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFE3EBF2),
                unfocusedBorderColor = Color(0x00F0F5FA),
                errorBorderColor = Color(0xFFB00020)
            )
        )

        // Error message
        if (error != null) {
            Text(
                text = error,
                color = Color(0xFFB00020),
                fontSize = 12.sp,
                fontFamily = Sen,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, start = 4.dp)
            )
        }
    }
}

@Composable
fun PasswordField(
    password: String,
    label: String,
    onPasswordChange: (String) -> Unit,
    passwordError: String?,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label.uppercase(),
            fontWeight = FontWeight.W400,
            fontFamily = Sen,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            color = Color(0xFF32343E),
            fontSize = 12.sp
        )

        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            placeholder = {
                Text(
                    "* * * * * * * * * ",
                    color = Color(0xFFA0A5BA),
                    fontWeight = FontWeight.W400,
                    fontFamily = Sen
                )
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            isError = passwordError != null,
            trailingIcon = {
                IconButton(onClick = { onPasswordVisibilityChange(!passwordVisible) }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle password visibility",
                        tint = Color(0xFFA0A5BA)
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp))
                .padding(bottom = if (passwordError != null) 4.dp else 16.dp)
                .background(Color(0xFFF0F5FA)),
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFE3EBF2),
                unfocusedBorderColor = Color(0x00F0F5FA),
                errorBorderColor = Color(0xFFB00020)
            )
        )

        if (passwordError != null) {
            Text(
                text = passwordError,
                color = Color(0xFFB00020),
                fontSize = 12.sp,
                fontFamily = Sen,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 16.dp, start = 4.dp)
            )
        }
    }
}
