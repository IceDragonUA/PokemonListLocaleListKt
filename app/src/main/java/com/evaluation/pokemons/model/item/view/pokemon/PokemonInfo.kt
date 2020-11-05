package com.evaluation.pokemons.model.item.view.pokemon

import com.evaluation.pokemons.model.item.view.language.LanguageNameView

/**
 * @author Vladyslav Havrylenko
 * @since 25.10.2020
 */
interface PokemonInfo {
    fun names(): List<LanguageNameView>
}