package com.onlineshop.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.onlineshop.data.model.CartItem
import com.onlineshop.data.model.Product
import com.onlineshop.data.source.repository.interfaces.ProductsRepository
import com.onlineshop.networking.Error
import com.onlineshop.networking.Loading
import com.onlineshop.networking.Resource
import com.onlineshop.networking.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Connects view with resources, performs all the logic of [com.onlineshop.ui.productlist.ProductsFragment] screen
 */
class ProductsViewModel @Inject constructor(
    private val repository: ProductsRepository
) : ViewModel() {

    /**
     * [MutableLiveData] with list of [Product] which information displays on the screen
     */
    var products: MutableLiveData<Resource<List<Product>>>
    private var querySearchTask: Job? = null
    private var fullListOfProducts: List<Product> = listOf()

    init {
        products = getProducts() as MutableLiveData<Resource<List<Product>>>
    }

    /**
     * Searches products which names contains passed string
     *
     * @param partOfName part of name to search by
     */
    fun findByPartOfName(partOfName: String) {
        querySearchTask?.cancel()
        querySearchTask = viewModelScope.launch {
            if (partOfName.isNotEmpty()) {
                delay(200)
                products.value = Success(
                    data = repository.loadProductsByPartOfName(partOfName)
                )
            } else {
                setFullList()
            }
        }
        querySearchTask?.start()
    }

    /**
     * Adds items to cart by its id
     *
     * @param productId id of product to be added to cart
     */
    fun addItemToCart(productId: Long) {
        viewModelScope.launch {
            repository.addCartItem(
                CartItem(
                    id = 0,
                    productId = productId,
                    count = 1,
                    product = null
                )
            )
        }
    }

    /**
     * Initiates loading list of products
     */
    private fun getProducts() = liveData(Dispatchers.IO) {
        emit(Loading(data = null))
        try {
            val data = repository.getAllProducts()
            emit(Success(data = data))
            fullListOfProducts = data
        } catch (exception: Exception) {
            emit(Error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    /**
     * Sets full list of products to [products]
     */
    private fun setFullList() {
        products.value = Success(fullListOfProducts)
    }
}
