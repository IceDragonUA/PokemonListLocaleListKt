package com.evaluation.pokemons.adapter.viewholder

import android.view.View
import com.evaluation.adapter.AdapterItemClickListener
import com.evaluation.adapter.viewholder.BaseViewHolder
import com.evaluation.pokemons.adapter.viewholder.item.CardItemView
import com.evaluation.pokemons.interaction.AppPokemonsInteraction
import com.evaluation.utils.emptyString
import com.evaluation.utils.initText
import com.evaluation.utils.loadFromUrl
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.card_item.view.*
import timber.log.Timber


class CardItemHolder(
    itemView: View,
    listener: AdapterItemClickListener<CardItemView>?,
    interaction: AppPokemonsInteraction,
) : BaseViewHolder<CardItemView>(itemView, listener, interaction) {

    private val compositeDisposable = CompositeDisposable()

    override fun bind(item: CardItemView, language: String?) {
        itemView.image.setImageDrawable(null)

        itemView.name.initText(item.viewItem.name)

        itemView.setOnClickListener {
            listener?.onClicked(item)
        }

        compositeDisposable.clear()

        interaction?.pokemonInfo(item.viewItem, item.index.toInt())
            ?.subscribe({
                loadCompleted()
            }, {
                loadError(it)
            })?.let { compositeDisposable.add(it) }

        interaction?.pokemonInfo(item.viewItem.name)?.subscribe {
            item.viewItemInfo = it
            itemView.image.loadFromUrl(it.front_default)
            val abilities = it.abilities
                .filter { view -> view.names.find { name -> name.language.name == language }?.name != null }
                .map { view -> view.names.find { name -> name.language.name == language } }
            if (abilities.isNotEmpty())
                itemView.abilities.initText(abilities.joinToString { view ->
                    view?.name ?: emptyString()
                }) else
                itemView.abilities.initText(emptyString())
        }?.let { compositeDisposable.add(it) }
    }

    private fun loadCompleted() {
        Timber.d("Loading finished")
    }

    private fun loadError(throwable: Throwable) {
        Timber.e(throwable,"Loading finished")
    }

}