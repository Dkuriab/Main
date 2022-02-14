package com.onlineshop.di.module

import android.content.Context
import com.onlineshop.data.source.local.ProductsRoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module for providing initialized database
 */
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): ProductsRoomDatabase {
        return ProductsRoomDatabase.initialize(context)
    }
}
