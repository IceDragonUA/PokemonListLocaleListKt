package com.evaluation.pokemons.repository

import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.pokemons.model.item.database.types.TypeTableItem
import com.evaluation.utils.LauncherViewState
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

/**
 * @author Vladyslav Havrylenko
 * @since 05.11.2020
 */
interface AppPokemonsRepository {

    fun pokemonListInit(
        offset: Int,
        limit: Int,
        query: String,
        category: String,
        onPrepared: () -> Unit,
        onSuccess: (MutableList<BaseItemView>) -> Unit,
        onError: (MutableList<BaseItemView>) -> Unit
    ): Disposable

    fun pokemonListPaged(
        offset: Int,
        limit: Int,
        query: String,
        category: String,
        onPrepared: () -> Unit,
        onSuccess: (MutableList<BaseItemView>) -> Unit,
        onError: () -> Unit
    ): Disposable

    fun status(
        offset: Int,
        limit: Int,
        status: PublishSubject<LauncherViewState>
    ): Observable<LauncherViewState>

    fun loadLanguages(): Single<MutableList<BaseItemView>>

    fun loadCategories(): Single<List<TypeTableItem>>
}