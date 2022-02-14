package com.onlineshop.data.model

import java.text.NumberFormat
import java.util.Locale

/**
 * Data class, using internally in the application, for now using as class for View
 */
data class Product(
    val id: Long,
    val name: String,
    val price: Double,
    val description: String,
    val rating: Double,
    val categoryId: Int,
    val imagePath: String
) {
    /**
     * Price in russian format. Uses [NumberFormat] for formatting
     */
    val formattedPrice: String
        get() = NumberFormat.getCurrencyInstance(Locale("ru", "RU")).format(price)
}
