package com.onlineshop.ui.productlist

import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class TestApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerTestApplicationComponent.factory()
            .create(this)
            .inject(this)
    }

    override fun androidInjector() = androidInjector
}