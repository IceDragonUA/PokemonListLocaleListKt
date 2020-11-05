package com.evaluation.pokemons.model.item.rest.pokemon

import com.google.gson.annotations.SerializedName

/**
 * @author Vladyslav Havrylenko
 * @since 22.10.2020
 */
data class Sprite(
    @SerializedName("front_default")
    val front_default: String?,
    @SerializedName("back_default")
    val back_default: String?
)