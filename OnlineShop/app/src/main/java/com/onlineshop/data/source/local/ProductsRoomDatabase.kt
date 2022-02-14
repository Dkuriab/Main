package com.onlineshop.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.onlineshop.data.entity.CartItemEntity
import com.onlineshop.data.entity.HistoryItemEntity
import com.onlineshop.data.entity.ProductEntity

/**
 * class for [RoomDatabase] auto generation
 */
@Database(entities = [ProductEntity::class, CartItemEntity::class, HistoryItemEntity::class], version = 3)
abstract class ProductsRoomDatabase : RoomDatabase() {
    companion object {
        /**
         * Generated Implementation of Database
         */
        fun initialize(context: Context): ProductsRoomDatabase {
            return Room.databaseBuilder(
                context,
                ProductsRoomDatabase::class.java, "database"
            ).build()
        }
    }

    /**
     * Returns generated Implementation of [ProductsDao]
     */
    abstract fun productsDao(): ProductsDao

    /**
     * Returns generated Implementation of [CartItemsDao]
     */
    abstract fun cartItemsDao(): CartItemsDao
    abstract fun historyItemsDao(): HistoryItemsDao
}
