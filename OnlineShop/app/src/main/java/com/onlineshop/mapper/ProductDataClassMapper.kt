package com.onlineshop.mapper

import com.onlineshop.data.dto.ProductDTO
import com.onlineshop.data.model.Product

class ProductDataClassMapper : MapperInterface<List<ProductDTO>, List<Product>> {

    override fun transformToSecondType(type: List<ProductDTO>): List<Product> {
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

    override fun transformToFirstType(type: List<Product>): List<ProductDTO> {
        return type.map {
            ProductDTO(
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
