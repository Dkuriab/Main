package com.onlineshop.networking.api

import com.onlineshop.data.dto.ProductDTO
import retrofit2.http.GET

/**
 * Declarations of functions using for connecting with server
 */
interface ProductsApiService {

    /**
     * Gets list of products from server
     *
     * @return list of [ProductDTO]
     */
    @GET("Products")
    suspend fun getAllProducts(): List<ProductDTO>
}
