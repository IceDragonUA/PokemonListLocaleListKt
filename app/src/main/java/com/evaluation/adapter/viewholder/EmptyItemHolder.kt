package com.evaluation.adapter.viewholder

import android.view.View
import com.evaluation.adapter.viewholder.item.EmptyItemView

class EmptyItemHolder(itemView: View) : BaseViewHolder<EmptyItemView>(itemView, null) {

    override fun bind(item: EmptyItemView, language: String) {}

}