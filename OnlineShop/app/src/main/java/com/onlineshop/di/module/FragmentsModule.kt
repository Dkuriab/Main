package com.onlineshop.di.module

import com.onlineshop.ui.ProductDetailsFragment
import com.onlineshop.ui.cart.CartFragment
import com.onlineshop.ui.login.LoginFragment
import com.onlineshop.ui.login.SignUpFragment
import com.onlineshop.ui.history.HistoryFragment
import com.onlineshop.ui.productlist.ProductsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Dagger module for providing all fragments
 */
@Module
abstract class FragmentsModule {

    @ContributesAndroidInjector(modules = [ProductsModule::class])
    abstract fun bindProductsFragment(): ProductsFragment

    @ContributesAndroidInjector(modules = [ProductsModule::class])
    abstract fun bindProductDetailsFragment(): ProductDetailsFragment

    @ContributesAndroidInjector(modules = [ProductsModule::class])
    abstract fun bindCartFragment(): CartFragment

    @ContributesAndroidInjector(modules = [ProductsModule::class])
    abstract fun bindLoginFragment(): LoginFragment

    @ContributesAndroidInjector(modules = [ProductsModule::class])
    abstract fun bindSignUpFragment(): SignUpFragment

    @ContributesAndroidInjector(modules = [ProductsModule::class])
    abstract fun bindHistoryFragment(): HistoryFragment
}
