package com.evaluation.language.adapter.viewholder.item

import com.evaluation.adapter.factory.TypesFactory
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.pokemons.model.item.view.language.LanguageView

/**
 * @author Vladyslav Havrylenko
 * @since 05.11.2020
 */
data class LanguageItemView(
    override var index: String,
    override var name: String,
    var viewItem: LanguageView
) : BaseItemView {

    override fun type(typesFactory: TypesFactory): Int = typesFactory.type(this)

}