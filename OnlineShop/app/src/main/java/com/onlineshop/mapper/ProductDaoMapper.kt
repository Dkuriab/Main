package com.onlineshop.mapper

import com.onlineshop.data.entity.ProductEntity
import com.onlineshop.data.model.Product

class ProductDaoMapper : MapperInterface<List<ProductEntity>, List<Product>> {

    override fun transformToSecondType(type: List<ProductEntity>): List<Product> {
        return type.map {
            Product(
                id = it.id,
                name = it.name,
                price = it.price,
                description = it.description,
                rating = it.rating,
                categoryId = it.categoryId,
                imagePath = it.imagePath
            )
        }
    }

    override fun transformToFirstType(type: List<Product>): List<ProductEntity> {
        return type.map {
            ProductEntity(
                id = it.id,
                name = it.name,
                price = it.price,
                description = it.description,
                rating = it.rating,
                categoryId = it.categoryId,
                imagePath = it.imagePath
            )
        }
    }
}
