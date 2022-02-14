package com.onlineshop.ui.history

import androidx.recyclerview.widget.DiffUtil
import com.onlineshop.data.model.HistoryItem

/**
 * [DiffUtil.ItemCallback] implementation for HistoryItem compare
 */
class HistoryItemsDiffCallback : DiffUtil.ItemCallback<HistoryItem>() {

    /**
     * Compares [HistoryItem] by ids
     *
     * @param oldItem previous version of product
     * @param newItem new version of product
     *
     * @return `true` if ids same, `false` else
     */
    override fun areItemsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
        return oldItem.id == newItem.id
    }

    /**
     * Compares [HistoryItem] by contents
     *
     * @param oldItem previous version of product
     * @param newItem new version of product
     *
     * @return `true` if objects same, `false` else
     */
    override fun areContentsTheSame(oldItem: HistoryItem, newItem: HistoryItem): Boolean {
        return oldItem == newItem
    }
}
