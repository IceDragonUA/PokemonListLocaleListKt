package com.evaluation.pokemons.model.item.rest.pokemon.options

import com.evaluation.pokemons.model.item.rest.ResponseResult
import com.evaluation.pokemons.model.item.rest.language.LanguageResult
import com.google.gson.annotations.SerializedName

/**
 * @author Vladyslav Havrylenko
 * @since 22.10.2020
 */
data class Ability(
    @SerializedName("ability")
    val ability: ResponseResult
)