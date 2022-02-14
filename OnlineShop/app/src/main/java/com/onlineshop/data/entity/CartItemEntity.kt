package com.onlineshop.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class, using for connection with local database ("cart" schema)
 */
@Entity(tableName = "cart")
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo val productId: Long,
    @ColumnInfo val count: Long
)
