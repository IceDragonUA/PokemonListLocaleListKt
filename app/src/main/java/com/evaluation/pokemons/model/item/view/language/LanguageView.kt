package com.evaluation.pokemons.model.item.view.language

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author Vladyslav Havrylenko
 * @since 22.10.2020
 */
@Parcelize
data class LanguageView(
    val name: String
) : Parcelable