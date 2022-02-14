package com.onlineshop.di.module

import androidx.lifecycle.ViewModel
import com.onlineshop.viewmodel.ProductDetailsViewModel
import com.onlineshop.viewmodel.ProductsViewModel
import dagger.Module
import dagger.Provides

/**
 * Dagger module for providing view models
 */
@Module
class ProductsModule {

    @Provides
    fun provideProductRepositoryViewModel(viewModel: ProductDetailsViewModel): ViewModel =
        viewModel

    @Provides
    fun provideProductsViewModel(viewModel: ProductsViewModel): ViewModel =
        viewModel
}
