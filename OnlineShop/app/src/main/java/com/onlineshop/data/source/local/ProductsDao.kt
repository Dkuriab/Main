package com.onlineshop.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.onlineshop.data.entity.ProductEntity

/**
 * Interface for [androidx.room.Room] database generation
 */
@Dao
interface ProductsDao {
    /**
     * Gets all products from local database
     *
     * @return List of [ProductEntity]
     */
    @Query("SELECT * FROM products")
    suspend fun getAll(): List<ProductEntity>

    /**
     * Gets products from local database by it's ids
     *
     * @param productIds ids of the products to be searched by
     *
     * @return List of [ProductEntity]
     */
    @Query("SELECT * FROM products WHERE id IN (:productIds)")
    suspend fun loadAllByIds(productIds: IntArray): List<ProductEntity>

    /**
     * Gets product from local database by it's id
     *
     * @param productId id of the product to be searched by
     *
     * @return [ProductEntity]
     */
    @Query("SELECT * FROM products WHERE id=:productId")
    suspend fun loadById(productId: Long): ProductEntity

    /**
     * Gets list of products from local database by it's id
     *
     * @param ids ids of the products to be searched by
     *
     * @return list of [ProductEntity]
     */
    @Query("SELECT * FROM products WHERE id IN (:ids)")
    suspend fun loadProductsByIds(ids: List<Long>): List<ProductEntity>

    /**
     * Gets products from local database by part of it's name
     *
     * @param partOfName part of the name of the product to be searched by
     *
     * @return List of [ProductEntity]
     */
    @Query("SELECT * FROM products WHERE name LIKE '%' || :partOfName || '%'")
    fun loadByPartOfName(partOfName: String): List<ProductEntity>

    /**
     * Inserts products to local database
     *
     * @param products products to insert in database
     */
    @Insert
    suspend fun insertAll(vararg products: ProductEntity)

    /**
     * Deletes products from local database
     *
     * @param product product to delete from database if exists
     */
    @Delete
    suspend fun delete(product: ProductEntity)

    /**
     * Deletes all products from local database
     */
    @Query("DELETE FROM products")
    suspend fun deleteAll()
}
