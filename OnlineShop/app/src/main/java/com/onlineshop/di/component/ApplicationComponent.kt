package com.onlineshop.di.component

import android.content.Context
import com.onlineshop.ShopApplication
import com.onlineshop.di.module.DaoModule
import com.onlineshop.di.module.DatabaseModule
import com.onlineshop.di.module.FragmentsModule
import com.onlineshop.di.module.NetworkingModule
import com.onlineshop.di.module.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Dagger component, provides all non-testing modules into application
 */
@Singleton
@Component(
    modules = [
        FragmentsModule::class,
        AndroidInjectionModule::class,
        NetworkingModule::class,
        RepositoryModule::class,
        DatabaseModule::class,
        DaoModule::class
    ]
)
interface ApplicationComponent {

    fun inject(application: ShopApplication)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}
