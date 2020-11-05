package com.evaluation.pokemons.adapter.viewholder

import android.view.View
import com.evaluation.adapter.AdapterItemClickListener
import com.evaluation.adapter.viewholder.BaseViewHolder
import com.evaluation.pokemons.adapter.viewholder.item.CardItemView
import com.evaluation.utils.emptyString
import com.evaluation.utils.initText
import com.evaluation.utils.loadFromUrl
import kotlinx.android.synthetic.main.card_item.view.*


class CardItemHolder(itemView: View, listener: AdapterItemClickListener<CardItemView>?) :
    BaseViewHolder<CardItemView>(itemView, listener) {

    override fun bind(item: CardItemView, language: String) {
        itemView.image.loadFromUrl(item.viewItem.front_default)
        itemView.name.initText(item.viewItem.name)

        val abilities = item.viewItem.abilities
            .filter { it.names.find { name -> name.language.name == language }?.name != null  }
            .map { it.names.find { name -> name.language.name == language } }
        if (abilities.isNotEmpty())
            itemView.abilities.initText(abilities.joinToString { it?.name ?: emptyString() }) else
            itemView.abilities.initText(emptyString())

        itemView.setOnClickListener {
            listener?.onClicked(item)
        }
    }

}