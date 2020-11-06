package com.evaluation.pokemons.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import com.evaluation.pokemons.interaction.AppPokemonsInteraction
import com.evaluation.storage.ConfigPreferences
import com.evaluation.utils.LauncherViewState
import com.evaluation.utils.emptyString


/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
class PokemonViewModel @ViewModelInject constructor(
    interaction: AppPokemonsInteraction,
    configPreferences: ConfigPreferences
) : ViewModel() {

    var items = interaction.pokemonList()

    init {
        interaction.loadList()
    }

    val language = configPreferences.restoreLanguage()

}