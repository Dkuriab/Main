package com.onlineshop.data.dto

/**
 * Data class, appropriate to server data object
 */
data class ProductDTO(
    val id: Long,
    val name: String,
    val price: Double,
    val description: String,
    val rating: Double,
    val categoryId: Int,
    val imagePath: String
)
