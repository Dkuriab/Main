package com.onlineshop.di.module

import com.onlineshop.networking.api.ProductsApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Dagger module for providing network services
 */
@Module
class NetworkingModule {

    @Provides
    fun provideBaseUrl() = "http://10.0.2.2:5000/api/"

    @Singleton
    @Provides
    fun provideRetrofitInterface(baseUrl: String): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideProductsApiService(retrofitService: Retrofit): ProductsApiService = retrofitService
        .create(ProductsApiService::class.java)
}
