package com.onlineshop.di.module

import com.onlineshop.data.source.local.ProductsRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module for providing local database implementations
 */
@Module
object DaoModule {

    @Singleton
    @Provides
    fun provideProductsDao(applicationDatabase: ProductsRoomDatabase) = applicationDatabase.productsDao()

    @Singleton
    @Provides
    fun provideCartItemsDao(applicationDatabase: ProductsRoomDatabase) = applicationDatabase.cartItemsDao()

    @Singleton
    @Provides
    fun provideHistoryItemsDao(applicationDatabase: ProductsRoomDatabase) = applicationDatabase.historyItemsDao()
}
