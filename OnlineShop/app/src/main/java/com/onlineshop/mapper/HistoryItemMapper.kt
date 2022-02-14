package com.onlineshop.mapper

import com.onlineshop.data.entity.HistoryItemEntity
import com.onlineshop.data.model.HistoryItem

class HistoryItemMapper : MapperInterface<List<HistoryItemEntity>, List<HistoryItem>> {

    override fun transformToSecondType(type: List<HistoryItemEntity>): List<HistoryItem> {
        return type.map {
            HistoryItem(
                id = it.id,
                productId = it.productId,
                count = it.count,
                product = null
            )
        }
    }

    override fun transformToFirstType(type: List<HistoryItem>): List<HistoryItemEntity> {
        return type.map {
            HistoryItemEntity(
                id = it.id,
                productId = it.productId,
                count = it.count
            )
        }
    }
}
