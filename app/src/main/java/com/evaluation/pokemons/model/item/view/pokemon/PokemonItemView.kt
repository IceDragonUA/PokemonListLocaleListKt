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
data class PokemonItemView(
    val name: String,
    val weight: Int,
    val height: Int,
    val experience: Int,
    val front_default: String,
    val back_default: String,
    var stats: List<PokemonStatView>,
    var abilities: List<PokemonAbilityView>,
    var types: List<PokemonTypeView>
) : Parcelable