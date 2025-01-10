package com.example.projet_tdm.models

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Pannier(
    val orders: MutableList<Order>,
    var deliveryNote: String,
    var total: Double
) {
    fun calculateTotal() {
        total = orders.sumOf { it.totalAmount + it.deliveryFee }
    }
}



object PannierSingleton {
    // Changed from private var to private lateinit var
    private lateinit var _pannier: Pannier
    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()

    // Public read-only access to pannier
    val pannier: Pannier
        get() = if (::_pannier.isInitialized) _pannier
        else throw IllegalStateException("PannierSingleton must be initialized first")

    fun initialize(context: Context) {
        if (!::_pannier.isInitialized) {
            _pannier = Pannier(mutableListOf(), "", 0.0)
        }
        sharedPreferences = context.getSharedPreferences("pannier_prefs", Context.MODE_PRIVATE)
        loadOrdersFromLocalStorage()
    }

    private fun saveOrdersToLocalStorage() {
        try {
            val editor = sharedPreferences.edit()
            val ordersJson = gson.toJson(_pannier.orders)
            editor.putString("orders", ordersJson)
            editor.putString("delivery_note", _pannier.deliveryNote)
            editor.putFloat("total", _pannier.total.toFloat())
            editor.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadOrdersFromLocalStorage() {
        try {
            val ordersJson = sharedPreferences.getString("orders", null)
            if (!ordersJson.isNullOrEmpty()) {
                val type = object : TypeToken<List<Order>>() {}.type
                val orders = gson.fromJson<List<Order>>(ordersJson, type)
                _pannier.orders.clear()
                _pannier.orders.addAll(orders)
                _pannier.deliveryNote = sharedPreferences.getString("delivery_note", "") ?: ""
                _pannier.total = sharedPreferences.getFloat("total", 0f).toDouble()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _pannier.orders.clear()
            _pannier.calculateTotal()
        }
    }

    fun addOrder(order: Order) {
        if (!::_pannier.isInitialized) {
            throw IllegalStateException("PannierSingleton must be initialized first")
        }
        _pannier.orders.add(order)
        _pannier.calculateTotal()
        saveOrdersToLocalStorage()
    }


    fun removeOrder(order: Order) {
        _pannier.orders.remove(order)
        _pannier.calculateTotal()
        saveOrdersToLocalStorage()
    }

    fun updateOrderQuantity(order: Order, quantity: Int) {
        order.quantity = quantity
        order.totalAmount = order.item.price * quantity
        _pannier.calculateTotal()
        saveOrdersToLocalStorage()
    }

    fun clearCart() {
        _pannier.orders.clear()
        _pannier.calculateTotal()
        saveOrdersToLocalStorage()
    }
}