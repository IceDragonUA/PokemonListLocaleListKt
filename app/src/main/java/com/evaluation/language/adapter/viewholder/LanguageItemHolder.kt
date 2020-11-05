package com.evaluation.language.adapter.viewholder

import android.view.View
import com.evaluation.adapter.AdapterItemClickListener
import com.evaluation.adapter.viewholder.BaseViewHolder
import com.evaluation.language.adapter.viewholder.item.LanguageItemView
import com.evaluation.utils.initText
import kotlinx.android.synthetic.main.card_item.view.*

/**
 * @author Vladyslav Havrylenko
 * @since 05.11.2020
 */
class LanguageItemHolder(itemView: View, listener: AdapterItemClickListener<LanguageItemView>?) :
    BaseViewHolder<LanguageItemView>(itemView, listener) {

    override fun bind(item: LanguageItemView, language: String?) {
        itemView.name.initText(item.viewItem.name)
        itemView.setOnClickListener {
            listener?.onClicked(item)
        }
    }

}