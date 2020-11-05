package com.evaluation.pokemons.model.item.rest.pokemon

import com.evaluation.pokemons.model.item.rest.pokemon.options.Ability
import com.evaluation.pokemons.model.item.rest.pokemon.options.Stat
import com.evaluation.pokemons.model.item.rest.pokemon.options.Type
import com.google.gson.annotations.SerializedName

/**
 * @author Vladyslav Havrylenko
 * @since 22.10.2020
 */
data class Pokemon(
    @SerializedName("name")
    val name: String?,
    @SerializedName("sprites")
    val sprites: Sprite,
    @SerializedName("weight")
    val weight: Int?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("base_experience")
    val experience: Int?,
    @SerializedName("stats")
    val stats: List<Stat>,
    @SerializedName("abilities")
    val abilities: List<Ability>,
    @SerializedName("types")
    val types: List<Type>
)