package com.onlineshop.ui.productlist

import android.content.Context
import com.onlineshop.ShopApplication
import com.onlineshop.di.component.ApplicationComponent
import com.onlineshop.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        FragmentsModule::class,
        AndroidInjectionModule::class,
        NetworkingModule::class,
        TestRepositoryModule::class,
        DatabaseModule::class,
        DaoModule::class
    ]
)
interface TestApplicationComponent {
    fun inject(productsFragmentTest: ProductsFragmentTest)
    fun inject(testApplication: TestApplication)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): TestApplicationComponent
    }
}