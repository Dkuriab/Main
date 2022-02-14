package com.onlineshop.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data class, using for connection with local database ("history" schema)
 */
@Entity(tableName = "history")
data class HistoryItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo val productId: Long,
    @ColumnInfo val count: Long
)
