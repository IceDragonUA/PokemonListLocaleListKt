package com.evaluation.adapter.factory

import android.view.View
import com.evaluation.R
import com.evaluation.adapter.AdapterItemClickListener
import com.evaluation.adapter.viewholder.BaseViewHolder
import com.evaluation.adapter.viewholder.NoItemHolder
import com.evaluation.pokemons.adapter.viewholder.CardItemHolder
import com.evaluation.pokemons.adapter.viewholder.item.CardItemView
import com.evaluation.adapter.viewholder.item.NoItemView
import com.evaluation.language.adapter.viewholder.LanguageItemHolder
import com.evaluation.language.adapter.viewholder.item.LanguageItemView
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TypesFactoryImpl @Inject constructor() : TypesFactory {

    override fun type(item: NoItemView): Int = R.layout.no_item

    override fun type(item: CardItemView): Int = R.layout.card_item

    override fun type(item: LanguageItemView): Int = R.layout.language_item

    @Suppress("UNCHECKED_CAST")
    override fun holder(type: Int, view: View, listener: AdapterItemClickListener<*>?): BaseViewHolder<*> {
        return when (type) {
            R.layout.no_item -> NoItemHolder(view)
            R.layout.card_item -> CardItemHolder(view, listener as? AdapterItemClickListener<CardItemView>)
            R.layout.language_item -> LanguageItemHolder(view, listener as? AdapterItemClickListener<LanguageItemView>)
            else -> throw RuntimeException("Illegal view type")
        }
    }
}