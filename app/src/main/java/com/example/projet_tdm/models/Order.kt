package com.example.projet_tdm.models

import android.content.Context

data class Order(
    val item: Menu,
    var quantity: Int,
    var totalAmount: Int,
    var deliveryAddress: String = "",
    var status: String = "Pending",
    var preferences: String = "",
    var deliveryFee: Double = 0.0
) {
    companion object {
        fun createOrder(context: Context, menu: Menu, quantity: Int, preferences: String): Order {
            return Order(
                item = menu,
                quantity = quantity,
                totalAmount = menu.price * quantity,
                preferences = preferences,
                // Use default values for other fields initially
                deliveryAddress = "",
                deliveryFee = 0.0
            )
        }
    }
}
