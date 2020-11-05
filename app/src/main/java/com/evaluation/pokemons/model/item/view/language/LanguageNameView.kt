package com.evaluation.pokemons.model.item.view.language

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LanguageNameView(
    val name: String,
    val language: LanguageView
) : Parcelable