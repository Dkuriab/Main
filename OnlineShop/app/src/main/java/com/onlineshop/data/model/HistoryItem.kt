package com.onlineshop.data.model

/**
 * Data class, using internally in the application, for now using as class for history item View
 */
data class HistoryItem(
    val id: Long,
    val productId: Long,
    val count: Long,
    var product: Product?
)
