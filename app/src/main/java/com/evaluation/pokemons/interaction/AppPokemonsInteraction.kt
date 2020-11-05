package com.evaluation.pokemons.interaction

import androidx.lifecycle.LiveData
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.utils.LauncherViewState
import com.evaluation.pokemons.model.item.database.language.LanguageTableItem
import com.evaluation.pokemons.model.item.database.types.TypeTableItem
import com.evaluation.pokemons.model.item.view.language.LanguageView
import com.evaluation.utils.Listing
import io.reactivex.processors.BehaviorProcessor

/**
 * @author Vladyslav Havrylenko
 * @since 09.10.2020
 */
interface AppPokemonsInteraction {

    fun pokemonList(query: String, category: String): Listing<BaseItemView>

    fun load(): BehaviorProcessor<LauncherViewState>

    fun loadLanguages(): LiveData<MutableList<BaseItemView>>

    fun loadCategories(): BehaviorProcessor<List<TypeTableItem>>

}