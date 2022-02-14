package com.onlineshop.data.source.repository.implementations

import com.onlineshop.data.model.CartItem
import com.onlineshop.data.model.HistoryItem
import com.onlineshop.data.model.Product
import com.onlineshop.data.source.local.CartItemsDao
import com.onlineshop.data.source.local.HistoryItemsDao
import com.onlineshop.data.source.local.ProductsDao
import com.onlineshop.data.source.repository.interfaces.ProductsRepository
import com.onlineshop.mapper.CartItemMapper
import com.onlineshop.mapper.HistoryItemMapper
import com.onlineshop.mapper.ProductDaoMapper
import com.onlineshop.mapper.ProductDataClassMapper
import com.onlineshop.networking.api.ProductsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Class, using for connecting with remote and local storage
 */
class ProductsRepositoryImpl @Inject constructor(
    private val productsDao: ProductsDao,
    private val productsApiService: ProductsApiService,
    private val cartItemsDao: CartItemsDao,
    private val historyItemsDao: HistoryItemsDao
) : ProductsRepository {

    private val productMapper = ProductDataClassMapper()
    private val productDaoMapper = ProductDaoMapper()
    private val cartItemMapper = CartItemMapper()
    private val historyItemMapper = HistoryItemMapper()

    override suspend fun getAllProducts() = withContext(Dispatchers.IO) {
        val products =
            productMapper.transformToSecondType(productsApiService.getAllProducts())
        productsDao.deleteAll()
        productsDao.insertAll(*productDaoMapper.transformToFirstType(products).toTypedArray())

        return@withContext products
    }

    override suspend fun getProductById(id: Long): Product = withContext(Dispatchers.IO) {
        return@withContext productDaoMapper.transformToSecondType(
            listOf(
                productsDao.loadById(id)
            )
        )[0]
    }

    override suspend fun loadProductsByIds(ids: List<Long>): List<Product> =
        withContext(Dispatchers.IO) {
            return@withContext productDaoMapper.transformToSecondType(
                productsDao.loadProductsByIds(ids)
            )
        }

    override suspend fun loadProductsByPartOfName(partOfName: String) =
        withContext(Dispatchers.IO) {
            return@withContext productDaoMapper.transformToSecondType(
                productsDao.loadByPartOfName(partOfName)
            )
        }

    override suspend fun addCartItem(cartItem: CartItem) =
        withContext(Dispatchers.IO) {
            cartItemsDao.insertAll(*cartItemMapper.transformToFirstType(listOf(cartItem)).toTypedArray())
        }

    override suspend fun getHistory(): List<HistoryItem> =
        withContext(Dispatchers.IO) {
            return@withContext historyItemMapper.transformToSecondType(historyItemsDao.getAll())
        }

    override suspend fun addProductsToHistory(historyItems: List<HistoryItem>) =
        withContext(Dispatchers.IO) {
            return@withContext historyItemsDao.insertAll(
                *historyItemMapper.transformToFirstType(
                    historyItems
                ).toTypedArray()
            )
        }

    override suspend fun clearCart() =
        withContext(Dispatchers.IO) {
            cartItemsDao.deleteAll()
        }

    override suspend fun getCartItemsForUser(userId: Long) =
        withContext(Dispatchers.IO) {
            return@withContext cartItemMapper.transformToSecondType(cartItemsDao.getAll())
        }
}
