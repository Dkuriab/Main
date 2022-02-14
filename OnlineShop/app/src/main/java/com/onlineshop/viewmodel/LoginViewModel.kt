package com.onlineshop.viewmodel

import androidx.lifecycle.ViewModel
import com.onlineshop.data.source.repository.interfaces.ProductsRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val repository: ProductsRepository
) : ViewModel() {

    fun logIn() {
        // TODO
    }
}
