package com.onlineshop.viewmodel

import androidx.lifecycle.ViewModel
import com.onlineshop.data.source.repository.interfaces.ProductsRepository
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    repository: ProductsRepository
) : ViewModel() {

    fun signUp() {
        // TODO
    }
}
