package com.onlineshop.data.source.repository.interfaces

import com.onlineshop.data.model.CartItem
import com.onlineshop.data.model.HistoryItem
import com.onlineshop.data.model.Product

/**
 * Describes the necessary functionality for communicating with external resources
 */
interface ProductsRepository {
    /**
     * Gets actual products list
     *
     * @return list of [com.onlineshop.data.model.Product]
     */
    suspend fun getAllProducts(): List<Product>

    /**
     * Gets product by its id
     *
     * @param id id of product to be search
     *
     * @return [com.onlineshop.data.model.Product] with passed id if exists
     */
    suspend fun getProductById(id: Long): Product

    /**
     * Gets products by its ids
     *
     * @param ids ids of products to be search
     *
     * @return list of [com.onlineshop.data.model.Product] with passed ids if exists
     */
    suspend fun loadProductsByIds(ids: List<Long>): List<Product>

    /**
     * Gets the products whose name contains the passed fragment
     *
     * @param partOfName part of name using for searching
     *
     * @return list [com.onlineshop.data.model.Product]
     */
    suspend fun loadProductsByPartOfName(partOfName: String): List<Product>

    /**
     * Gets cart items for user
     *
     * @param userId id of user
     *
     * @return list of [com.onlineshop.data.model.CartItem] for passed user
     */
    suspend fun getCartItemsForUser(userId: Long): List<CartItem>

    /**
     * Adds cart item for current user
     *
     * @param cartItem cart item to be added
     */
    suspend fun addCartItem(cartItem: CartItem)

    /**
     * Fetches history of orders
     *
     * @return list of [HistoryItem]
     */
    suspend fun getHistory(): List<HistoryItem>

    /**
     * Adds history items to database
     *
     * @param historyItems list of [HistoryItem] to be inserted
     */
    suspend fun addProductsToHistory(historyItems: List<HistoryItem>)

    /**
     * Deletes all items from cart database
     */
    suspend fun clearCart()
}
