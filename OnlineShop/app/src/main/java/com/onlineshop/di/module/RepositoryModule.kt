package com.onlineshop.di.module

import com.onlineshop.data.source.repository.implementations.ProductsRepositoryImpl
import com.onlineshop.data.source.repository.interfaces.ProductsRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Dagger module for providing repository implementation
 */
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindProductsRepository(repository: ProductsRepositoryImpl): ProductsRepository
}
