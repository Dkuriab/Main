package com.onlineshop

import android.app.Application
import com.onlineshop.di.component.DaggerApplicationComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * Overrides [Application], contains information about application which needed in other parts of project
 */
class ShopApplication : Application(), HasAndroidInjector {

    @Inject lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerApplicationComponent.factory()
            .create(this)
            .inject(this)
    }

    override fun androidInjector() = androidInjector
}
