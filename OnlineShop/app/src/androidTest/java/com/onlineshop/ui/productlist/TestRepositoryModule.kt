package com.onlineshop.ui.productlist

import com.onlineshop.data.source.repository.implementations.ProductsRepositoryImpl
import com.onlineshop.data.source.repository.interfaces.ProductsRepository
import com.onlineshop.di.module.RepositoryModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.mockk.coEvery
import io.mockk.mockk
import javax.inject.Singleton

@Module
abstract class TestRepositoryModule {

    @Binds
    @Singleton
    abstract fun provideProductsRepository(repository: TestProductsRepositoryImpl): ProductsRepository
}