package com.onlineshop.ui.productlist

import androidx.recyclerview.widget.DiffUtil
import com.onlineshop.data.model.Product

/**
 * [DiffUtil.ItemCallback] implementation for Product compare
 */
class ProductDiffCallback : DiffUtil.ItemCallback<Product>() {

    /**
     * Compares [Product] by ids
     *
     * @param oldItem previous version of product
     * @param newItem new version of product
     *
     * @return `true` if ids same, `false` else
     */
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    /**
     * Compares [Product] by contents
     *
     * @param oldItem previous version of product
     * @param newItem new version of product
     *
     * @return `true` if objects same, `false` else
     */
    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}
