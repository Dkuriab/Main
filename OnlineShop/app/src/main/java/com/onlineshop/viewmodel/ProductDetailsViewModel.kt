package com.onlineshop.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.onlineshop.data.model.Product
import com.onlineshop.data.source.repository.interfaces.ProductsRepository
import com.onlineshop.networking.Error
import com.onlineshop.networking.Loading
import com.onlineshop.networking.Resource
import com.onlineshop.networking.Success
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Connects view with resources, performs all the logic of [com.onlineshop.ui.ProductDetailsFragment] screen
 */
class ProductDetailsViewModel @Inject constructor(
    private val repository: ProductsRepository
) : ViewModel() {

    /**
     * [MutableLiveData] with a product which information displays on the screen
     */
    var product: MutableLiveData<Resource<Product>>? = null

    /**
     * Initiates getting information about product
     *
     * @param productId id of product to search
     */
    fun initialize(productId: Long) {
        product = liveData(Dispatchers.IO) {
            emit(Loading(data = null))
            try {
                emit(Success(data = repository.getProductById(productId)))
            } catch (exception: Exception) {
                emit(Error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        } as MutableLiveData<Resource<Product>>
    }
}
