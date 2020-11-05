package com.evaluation.pokemons.model.item.view.pokemon.options

import android.os.Parcelable
import com.evaluation.pokemons.model.item.view.language.LanguageNameView
import com.evaluation.pokemons.model.item.view.pokemon.PokemonInfo
import kotlinx.android.parcel.Parcelize

/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
@Parcelize
data class PokemonTypeView(
    val names: List<LanguageNameView>,
) : Parcelable, PokemonInfo {
    override fun names(): List<LanguageNameView> = names
}