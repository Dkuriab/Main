package com.onlineshop.mapper

import com.onlineshop.data.model.CartItem
import com.onlineshop.data.model.HistoryItem

class MapperCartItemToHistoryItem : MapperInterface<List<CartItem>, List<HistoryItem>> {

    override fun transformToSecondType(type: List<CartItem>): List<HistoryItem> {
        return type.map {
            HistoryItem(
                id = it.id,
                productId = it.productId,
                product = it.product,
                count = it.count
            )
        }
    }

    override fun transformToFirstType(type: List<HistoryItem>): List<CartItem> {
        return type.map {
            CartItem(
                id = it.id,
                productId = it.productId,
                product = it.product,
                count = it.count
            )
        }
    }
}
