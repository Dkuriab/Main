package com.onlineshop.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.onlineshop.data.model.HistoryItem
import com.onlineshop.data.source.repository.interfaces.ProductsRepository
import com.onlineshop.networking.Error
import com.onlineshop.networking.Loading
import com.onlineshop.networking.Resource
import com.onlineshop.networking.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

/**
 * Connects view with resources, performs all the logic of [com.onlineshop.ui.history.HistoryFragment] screen
 */
class HistoryViewModel @Inject constructor(
    private val repository: ProductsRepository
) : ViewModel() {

    /**
     * [MutableLiveData] with [Resource] contains all items bought by user
     */
    var products: MutableLiveData<Resource<List<HistoryItem>>>? = null

    /**
     * Initiates fetching information about history of orders for current user
     */
    fun initialize() {
        viewModelScope.launch {
            products = liveData(Dispatchers.IO) {
                emit(Loading(data = null))
                val historyItems = repository.getHistory()
                val productsList =
                    repository.loadProductsByIds(historyItems.map { historyItem -> historyItem.productId })

                for (historyItem in historyItems) {
                    historyItem.product =
                        productsList.find { product -> product.id == historyItem.productId }
                }

                try {
                    emit(Success(data = historyItems))
                } catch (exception: Exception) {
                    emit(Error(data = null, message = exception.message ?: "Error Occurred!"))
                }
            } as MutableLiveData<Resource<List<HistoryItem>>>
        }
    }
}
