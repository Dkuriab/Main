package com.onlineshop.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.onlineshop.data.entity.CartItemEntity

/**
 * Interface for [androidx.room.Room] database generation
 */
@Dao
interface CartItemsDao {
    /**
     * Gets all products from local database
     *
     * @return List of [CartItemEntity]
     */
    @Query("SELECT * FROM cart")
    suspend fun getAll(): List<CartItemEntity>

    /**
     * Gets products from local database by it's ids
     *
     * @param productIds ids of the products to be searched by
     *
     * @return List of [CartItemEntity]
     */
    @Query("SELECT * FROM cart WHERE id IN (:productIds)")
    suspend fun loadAllByIds(productIds: IntArray): List<CartItemEntity>

    /**
     * Inserts products to local database
     *
     * @param products products to insert in database
     */
    @Insert
    suspend fun insertAll(vararg products: CartItemEntity)

    /**
     * Deletes all information
     */
    @Query("DELETE FROM cart")
    suspend fun deleteAll()
}
