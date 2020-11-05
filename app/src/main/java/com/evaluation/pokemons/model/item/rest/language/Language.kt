package com.evaluation.pokemons.model.item.rest.language

import com.google.gson.annotations.SerializedName

/**
 * @author Vladyslav Havrylenko
 * @since 22.10.2020
 */
data class Language(
    @SerializedName("name")
    val name: String?,
)