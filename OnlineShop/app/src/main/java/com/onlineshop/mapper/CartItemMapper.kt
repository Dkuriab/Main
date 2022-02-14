package com.onlineshop.mapper

import com.onlineshop.data.entity.CartItemEntity
import com.onlineshop.data.model.CartItem

class CartItemMapper : MapperInterface<List<CartItemEntity>, List<CartItem>> {

    override fun transformToSecondType(type: List<CartItemEntity>): List<CartItem> {
        return type.map {
            CartItem(
                id = it.id,
                productId = it.productId,
                count = it.count,
                product = null
            )
        }
    }

    override fun transformToFirstType(type: List<CartItem>): List<CartItemEntity> {
        return type.map {
            CartItemEntity(
                id = it.id,
                productId = it.productId,
                count = it.count
            )
        }
    }
}
