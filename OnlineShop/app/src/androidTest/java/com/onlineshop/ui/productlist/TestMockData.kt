package com.onlineshop.ui.productlist

import com.onlineshop.data.model.CartItem
import com.onlineshop.data.model.HistoryItem
import com.onlineshop.data.model.Product
import com.onlineshop.data.source.repository.interfaces.ProductsRepository
import javax.inject.Inject

class TestProductsRepositoryImpl @Inject constructor() : ProductsRepository {
    private val productList = listOf(
        Product(
            1,
            "Test item 1",
            110001.11,
            "test description",
            1.2,
            5,
            "https://i5.walmartimages.com/asr/229226f5-c5a9-425b-b411-9484aa23e845_1.06a852888d9c1b507db92f8a013b466b.jpeg"
        ),
        Product(
            2,
            "Test item 1",
            110001.11,
            "test description",
            1.2,
            5,
            "https://i5.walmartimages.com/asr/229226f5-c5a9-425b-b411-9484aa23e845_1.06a852888d9c1b507db92f8a013b466b.jpeg"
        ),
        Product(
            3,
            "Test item 1",
            110001.11,
            "test description",
            1.2,
            5,
            "https://i5.walmartimages.com/asr/229226f5-c5a9-425b-b411-9484aa23e845_1.06a852888d9c1b507db92f8a013b466b.jpeg"
        ),
        Product(
            4,
            "Test item 1",
            110001.11,
            "test description",
            1.2,
            5,
            "https://i5.walmartimages.com/asr/229226f5-c5a9-425b-b411-9484aa23e845_1.06a852888d9c1b507db92f8a013b466b.jpeg"
        ),
        Product(
            5,
            "Test item 1",
            110001.11,
            "test description",
            1.2,
            5,
            "https://i5.walmartimages.com/asr/229226f5-c5a9-425b-b411-9484aa23e845_1.06a852888d9c1b507db92f8a013b466b.jpeg"
        ),
        Product(
            6,
            "Test item 1",
            110001.11,
            "test description",
            1.2,
            5,
            "https://i5.walmartimages.com/asr/229226f5-c5a9-425b-b411-9484aa23e845_1.06a852888d9c1b507db92f8a013b466b.jpeg"
        ),
        Product(
            7,
            "Test item 1",
            110001.11,
            "test description",
            1.2,
            5,
            "https://i5.walmartimages.com/asr/229226f5-c5a9-425b-b411-9484aa23e845_1.06a852888d9c1b507db92f8a013b466b.jpeg"
        ),
        Product(
            8,
            "Test item 1",
            110001.11,
            "test description",
            1.2,
            5,
            "https://i5.walmartimages.com/asr/229226f5-c5a9-425b-b411-9484aa23e845_1.06a852888d9c1b507db92f8a013b466b.jpeg"
        ),
        Product(
            9,
            "Test item 1",
            110001.11,
            "test description",
            1.2,
            5,
            "https://i5.walmartimages.com/asr/229226f5-c5a9-425b-b411-9484aa23e845_1.06a852888d9c1b507db92f8a013b466b.jpeg"
        ),
        Product(
            10,
            "Test item 1",
            110001.11,
            "test description",
            1.2,
            5,
            "https://i5.walmartimages.com/asr/229226f5-c5a9-425b-b411-9484aa23e845_1.06a852888d9c1b507db92f8a013b466b.jpeg"
        ),
        Product(
            11,
            "Test item 1",
            110001.11,
            "test description",
            1.2,
            5,
            "https://i5.walmartimages.com/asr/229226f5-c5a9-425b-b411-9484aa23e845_1.06a852888d9c1b507db92f8a013b466b.jpeg"
        ),
        Product(
            12,
            "Test item 1",
            110001.11,
            "test description",
            1.2,
            5,
            "https://i5.walmartimages.com/asr/229226f5-c5a9-425b-b411-9484aa23e845_1.06a852888d9c1b507db92f8a013b466b.jpeg"
        ),
        Product(
            13,
            "Test item 1",
            110001.11,
            "test description",
            1.2,
            5,
            "https://i5.walmartimages.com/asr/229226f5-c5a9-425b-b411-9484aa23e845_1.06a852888d9c1b507db92f8a013b466b.jpeg"
        ),
        Product(
            14,
            "Test item 1",
            110001.11,
            "test description",
            1.2,
            5,
            "https://i5.walmartimages.com/asr/229226f5-c5a9-425b-b411-9484aa23e845_1.06a852888d9c1b507db92f8a013b466b.jpeg"
        )
    )

    override suspend fun getAllProducts(): List<Product> {
        return productList
    }

    override suspend fun getProductById(id: Long): Product {
        return productList.find { product -> product.id == id } ?: productList[2]
    }

    override suspend fun loadProductsByIds(ids: List<Long>): List<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun loadProductsByPartOfName(partOfName: String): List<Product> {
        return productList.filter { product -> product.name.contains(partOfName) }
    }

    override suspend fun getCartItemsForUser(userId: Long): List<CartItem> {
        TODO("Not yet implemented")
    }

    override suspend fun addCartItem(cartItem: CartItem) {
        TODO("Not yet implemented")
    }

    override suspend fun getHistory(): List<HistoryItem> {
        TODO("Not yet implemented")
    }

    override suspend fun addProductsToHistory(historyItems: List<HistoryItem>) {
        TODO("Not yet implemented")
    }

    override suspend fun clearCart() {
        TODO("Not yet implemented")
    }
}