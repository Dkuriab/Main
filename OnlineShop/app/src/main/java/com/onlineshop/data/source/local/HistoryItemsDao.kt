package com.onlineshop.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.onlineshop.data.entity.HistoryItemEntity

/**
 * Interface for [androidx.room.Room] database generation
 */
@Dao
interface HistoryItemsDao {
    /**
     * Gets all cart items from local database
     *
     * @return List of [HistoryItemEntity]
     */
    @Query("SELECT * FROM history")
    suspend fun getAll(): List<HistoryItemEntity>

    /**
     * Inserts cart items to local database
     *
     * @param historyItems historyItems to insert in database
     */
    @Insert
    suspend fun insertAll(vararg historyItems: HistoryItemEntity)
}
