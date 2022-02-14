package com.onlineshop.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.onlineshop.data.model.CartItem
import com.onlineshop.data.source.repository.interfaces.ProductsRepository
import com.onlineshop.mapper.MapperCartItemToHistoryItem
import com.onlineshop.networking.Error
import com.onlineshop.networking.Loading
import com.onlineshop.networking.Resource
import com.onlineshop.networking.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

/**
 * Connects view with resources, performs all the logic of [com.onlineshop.ui.cart.CartFragment] screen
 */
class ProductsCartViewModel @Inject constructor(
    private val repository: ProductsRepository
) : ViewModel() {

    /**
     * Live data, contains [Resource] with status of request and with list of cart items in case request is [Success]
     */
    var products: MutableLiveData<Resource<List<CartItem>>>? = null

    /**
     * Live data, contains total cost of products from cart
     */
    var totalCost: MutableLiveData<Double> = MutableLiveData(0.0)
    private val mapperCartItemToHistoryItem = MapperCartItemToHistoryItem()

    /**
     * Initiates fetching cart items from remote resources
     *
     * @param userId id of user to get cart for
     */
    fun initialize(userId: Long) {
        viewModelScope.launch {
            products = liveData(Dispatchers.IO) {
                emit(Loading(data = null))
                try {
                    val cartItems = repository.getCartItemsForUser(userId)
                    val productsList =
                        repository.loadProductsByIds(cartItems.map { cartItem -> cartItem.productId })

                    for (cartItem in cartItems) {
                        cartItem.product =
                            productsList.find { product -> product.id == cartItem.productId }
                    }

                    emit(Success(data = cartItems))
                } catch (exception: Exception) {
                    emit(Error(data = null, message = exception.message ?: "Error Occurred!"))
                }
            } as MutableLiveData<Resource<List<CartItem>>>
        }

        products?.observeForever {
            totalCost.value =
                it.data?.sumOf { cartItem: CartItem -> cartItem.product?.price?.times(cartItem.count) ?: 0.0 } ?: 0.0
        }
    }

    /**
     * Initiates buying operation for all products in cart
     */
    fun buy() {
        viewModelScope.launch {
            repository.addProductsToHistory(
                mapperCartItemToHistoryItem.transformToSecondType(
                    products?.value?.data ?: listOf()
                )
            )
            repository.clearCart()
            products?.value = Success(listOf())
        }
    }
}
