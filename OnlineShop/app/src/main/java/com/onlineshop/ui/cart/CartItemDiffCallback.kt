package com.onlineshop.ui.cart

import androidx.recyclerview.widget.DiffUtil
import com.onlineshop.data.model.CartItem

/**
 * [DiffUtil.ItemCallback] implementation for CartItem compare
 */
class CartItemDiffCallback : DiffUtil.ItemCallback<CartItem>() {

    /**
     * Compares [CartItem] by ids
     *
     * @param oldItem previous version of product
     * @param newItem new version of product
     *
     * @return `true` if ids same, `false` else
     */
    override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem.id == newItem.id
    }

    /**
     * Compares [CartItem] by contents
     *
     * @param oldItem previous version of product
     * @param newItem new version of product
     *
     * @return `true` if objects same, `false` else
     */
    override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
        return oldItem == newItem
    }
}
