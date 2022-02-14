package com.onlineshop.ui.productlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onlineshop.data.model.Product
import com.onlineshop.databinding.ProductPreviewBinding
import com.onlineshop.utils.ImageDownloader

/**
 * Adapter for [RecyclerView] contains list of products
 *
 * @param onClick function, calls if user click on product item
 */
class ProductsListAdapter(
    val onClick: (Long) -> Unit,
    val onAddToCartButtonClick: (Long) -> Unit
) : ListAdapter<Product, ProductsListAdapter.ProductViewHolder>(ProductDiffCallback()) {

    /**
     * Called by RecyclerView to display the data at the specified position
     *
     * @param holder – The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position – The position of the item within the adapter's data set
     */
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onAddToCartButtonClick)
    }

    /**
     * Called when [RecyclerView] needs a new [RecyclerView.ViewHolder] of the given type to represent an item.
     *
     * @param parent – The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType – The view type of the new View.
     * @return a new [ProductViewHolder] that holds a View of the given view type
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val viewHolder = ProductViewHolder.from(parent)
        viewHolder.itemView.setOnClickListener { onClick(getItem(viewHolder.adapterPosition).id) }
        return viewHolder
    }

    /**
     * Implementation of [RecyclerView.ViewHolder]
     */
    class ProductViewHolder private constructor(private val binding: ProductPreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Sets content to view
         *
         * @param product [Product] which information will be used
         */
        fun bind(product: Product, onAddToCartButtonClick: (Long) -> Unit) {
            binding.apply {
                productName.text = product.name
                productPrice.text = product.formattedPrice
                productRating.rating = product.rating.toFloat()
                ImageDownloader().downloadImageToViewByURL(product.imagePath, productImage)
                addToCartButton.setOnClickListener {
                    onAddToCartButtonClick(product.id)
                }
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
                val binding = ProductPreviewBinding.inflate(layoutInflater, parent, false)
                return ProductViewHolder(binding)
            }
        }
    }
}
