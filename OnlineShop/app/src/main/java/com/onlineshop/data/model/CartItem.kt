package com.onlineshop.data.model

/**
 * Data class, using internally in the application, for now using as class for cart item View
 */
data class CartItem(
    val id: Long,
    val productId: Long,
    val count: Long,
    var product: Product?
)
