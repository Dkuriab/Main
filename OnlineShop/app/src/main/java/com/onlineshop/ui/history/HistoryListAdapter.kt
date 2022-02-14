package com.onlineshop.ui.history

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onlineshop.data.model.HistoryItem
import com.onlineshop.databinding.ProductHistoryItemBinding
import com.onlineshop.utils.ImageDownloader

/**
 * Adapter for [RecyclerView] contains list of history items
 *
 * @param onClick function, calls if user click on item
 */
class HistoryListAdapter(
    val onClick: (Long) -> Unit
) : ListAdapter<HistoryItem, HistoryListAdapter.ProductViewHolder>(HistoryItemsDiffCallback()) {

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
    class ProductViewHolder private constructor(private val binding: ProductHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Sets content to view
         *
         * @param cartItem [com.onlineshop.data.model.HistoryItem] which information will be used
         */
        fun bind(cartItem: HistoryItem) {
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
                val binding = ProductHistoryItemBinding.inflate(layoutInflater, parent, false)
                return ProductViewHolder(binding)
            }
        }
    }
}
