package com.onlineshop.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onlineshop.data.model.CartItem
import com.onlineshop.databinding.ProductCartItemBinding
import com.onlineshop.utils.ImageDownloader

/**
 * Adapter for [RecyclerView] contains list of cart items
 *
 * @param onClick function, calls if user click on item
 */
class CartListAdapter(
    val onClick: (Long) -> Unit
) : ListAdapter<CartItem, CartListAdapter.ProductViewHolder>(CartItemDiffCallback()) {

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val viewHolder = ProductViewHolder.from(parent)
        viewHolder.itemView.setOnClickListener {
            onClick(getItem(viewHolder.adapterPosition).product?.id ?: 0)
        }
        return viewHolder
    }

    /**
     * Implementation of [RecyclerView.ViewHolder]
     */
    class ProductViewHolder private constructor(private val binding: ProductCartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Sets content to view
         *
         * @param cartItem [CartItem] which information will be used
         */
        fun bind(cartItem: CartItem) {
            binding.apply {
                productName.text = cartItem.product?.name
                productPrice.text = cartItem.product?.formattedPrice
                itemCount.text = cartItem.count.toString()
                ImageDownloader().downloadImageToViewByURL(cartItem.product?.imagePath ?: "", productImage)
            }
        }

        companion object {
            /**
             * Creates new [ProductViewHolder]
             *
             * @param parent view holder will be connected to it
             *
             * @return [ProductViewHolder] object
             */
            fun from(parent: ViewGroup): ProductViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ProductCartItemBinding.inflate(layoutInflater, parent, false)
                return ProductViewHolder(binding)
            }
        }
    }
}
