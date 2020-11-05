package com.evaluation.adapter.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.evaluation.adapter.viewholder.item.BaseItemView

/**
 * @author Vladyslav Havrylenko
 * @since 05.10.2020
 */
class DiffItemCallback : DiffUtil.ItemCallback<BaseItemView>() {

    override fun areItemsTheSame(oldItem: BaseItemView, newItem: BaseItemView): Boolean {
        return oldItem.index == newItem.index
    }

    override fun areContentsTheSame(oldItem: BaseItemView, newItem: BaseItemView): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

}