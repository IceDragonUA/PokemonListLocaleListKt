package com.evaluation.adapter.factory

import android.view.View
import com.evaluation.adapter.AdapterItemClickListener
import com.evaluation.adapter.viewholder.BaseViewHolder
import com.evaluation.adapter.viewholder.item.EmptyItemView
import com.evaluation.pokemons.adapter.viewholder.item.CardItemView
import com.evaluation.adapter.viewholder.item.NoItemView
import com.evaluation.language.adapter.viewholder.item.LanguageItemView

interface TypesFactory {

    fun type(item: EmptyItemView): Int

    fun type(item: NoItemView): Int

    fun type(item: CardItemView): Int

    fun type(item: LanguageItemView): Int

    fun holder(type: Int, view: View, listener: AdapterItemClickListener<*>?): BaseViewHolder<*>

}