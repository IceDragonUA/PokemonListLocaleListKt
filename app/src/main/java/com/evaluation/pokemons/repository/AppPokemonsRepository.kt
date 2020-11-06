package com.evaluation.pokemons.repository

import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.pokemons.model.item.database.types.TypeTableItem
import com.evaluation.pokemons.model.item.rest.pokemon.Pokemon
import com.evaluation.pokemons.model.item.view.pokemon.PokemonItemView
import com.evaluation.pokemons.model.item.view.pokemon.PokemonView
import com.evaluation.utils.LauncherViewState
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

/**
 * @author Vladyslav Havrylenko
 * @since 05.11.2020
 */
interface AppPokemonsRepository {

    fun loadPokemons(): Flowable<MutableList<BaseItemView>>

    fun loadPokemonInfo(name: String): Flowable<PokemonItemView>

    fun loadPokemonInfo(item: PokemonView, index: Int): Completable

    fun loadList(
        offset: Int,
        limit: Int,
        status: PublishSubject<LauncherViewState>
    ): Observable<LauncherViewState>

    fun loadStatus(
        offset: Int,
        limit: Int,
        status: PublishSubject<LauncherViewState>
    ): Observable<LauncherViewState>

    fun loadLanguages(): Single<MutableList<BaseItemView>>

    fun loadCategories(): Single<List<TypeTableItem>>
}