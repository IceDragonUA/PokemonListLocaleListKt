package com.evaluation.pokemons.interaction

import androidx.lifecycle.LiveData
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.utils.LauncherViewState
import com.evaluation.pokemons.model.item.database.types.TypeTableItem
import com.evaluation.pokemons.model.item.view.pokemon.PokemonItemView
import com.evaluation.pokemons.model.item.view.pokemon.PokemonView
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor

/**
 * @author Vladyslav Havrylenko
 * @since 09.10.2020
 */
interface AppPokemonsInteraction {

    fun pokemonList(): LiveData<MutableList<BaseItemView>>

    fun pokemonInfo(name: String): Flowable<PokemonItemView>

    fun pokemonInfo(item: PokemonView, index: Int): Completable

    fun loadList(): BehaviorProcessor<LauncherViewState>

    fun loadStatus(): BehaviorProcessor<LauncherViewState>

    fun loadLanguages(): LiveData<MutableList<BaseItemView>>

    fun loadCategories(): BehaviorProcessor<List<TypeTableItem>>

}