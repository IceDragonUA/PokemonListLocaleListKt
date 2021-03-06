package com.evaluation.pokemons.model.item.view.pokemon

import android.os.Parcelable
import com.evaluation.pokemons.model.item.view.pokemon.options.PokemonAbilityView
import com.evaluation.pokemons.model.item.view.pokemon.options.PokemonStatView
import com.evaluation.pokemons.model.item.view.pokemon.options.PokemonTypeView
import kotlinx.android.parcel.Parcelize

/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
@Parcelize
data class PokemonView(
    val name: String,
    val url: String
) : Parcelable