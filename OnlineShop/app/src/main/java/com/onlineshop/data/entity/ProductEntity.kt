package com.onlineshop.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class, using for connection with local database ("products" schema)
 */
@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo val name: String,
    @ColumnInfo val price: Double,
    @ColumnInfo val description: String,
    @ColumnInfo val rating: Double,
    @ColumnInfo val categoryId: Int,
    @ColumnInfo val imagePath: String
)
