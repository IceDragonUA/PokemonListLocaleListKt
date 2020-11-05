package com.evaluation.pokemons.model.item.rest.language

import com.google.gson.annotations.SerializedName

/**
 * @author Vladyslav Havrylenko
 * @since 22.10.2020
 */
data class LanguageName(
    @SerializedName("name")
    val name: String?,
    @SerializedName("language")
    val language: Language
)