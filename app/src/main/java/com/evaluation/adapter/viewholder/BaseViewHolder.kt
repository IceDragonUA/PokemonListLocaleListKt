package com.evaluation.adapter.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.evaluation.adapter.AdapterItemClickListener
import com.evaluation.pokemons.interaction.AppPokemonsInteraction

abstract class BaseViewHolder<T>(view: View, val listener: AdapterItemClickListener<T>?, val interaction: AppPokemonsInteraction?) : RecyclerView.ViewHolder(view) {

    abstract fun bind(item: T, language: String?)

}


