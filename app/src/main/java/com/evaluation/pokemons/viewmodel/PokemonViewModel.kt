package com.evaluation.pokemons.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import com.evaluation.pokemons.interaction.AppPokemonsInteraction
import com.evaluation.utils.emptyString


/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
class PokemonViewModel @ViewModelInject constructor(
    private val interaction: AppPokemonsInteraction
) : ViewModel() {

    private var category: String = emptyString()
    private val queryResult = MutableLiveData<String>()
    private var itemResult = map(queryResult) { query -> interaction.pokemonList(query, category) }
    val items = switchMap(itemResult) { it.pagedList }
    val networkState = switchMap(itemResult) { it.networkState }

    fun search(query: String, category: String) {
        this.category = category
        this.queryResult.value = query
    }

}