package com.evaluation.pokemons.interaction

import androidx.lifecycle.LiveData
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.utils.LauncherViewState
import com.evaluation.pokemons.model.item.database.types.TypeTableItem
import io.reactivex.processors.BehaviorProcessor

/**
 * @author Vladyslav Havrylenko
 * @since 09.10.2020
 */
interface AppPokemonsInteraction {

    fun pokemonList(): LiveData<MutableList<BaseItemView>>

    fun loadList(): BehaviorProcessor<LauncherViewState>

    fun loadStatus(): BehaviorProcessor<LauncherViewState>

    fun loadLanguages(): LiveData<MutableList<BaseItemView>>

    fun loadCategories(): BehaviorProcessor<List<TypeTableItem>>

}