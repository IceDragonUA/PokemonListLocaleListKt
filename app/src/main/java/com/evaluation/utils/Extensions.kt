package com.evaluation.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.evaluation.R
import com.evaluation.glide.GlideApp
import com.evaluation.pokemons.model.item.view.pokemon.PokemonItemView
import com.evaluation.pokemons.model.item.view.pokemon.options.PokemonAbilityView
import com.evaluation.pokemons.model.item.view.pokemon.options.PokemonStatView
import com.evaluation.pokemons.model.item.view.pokemon.options.PokemonTypeView
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * @author Vladyslav Havrylenko
 * @since 01.10.2020
 */
fun String?.defIfNull() = this ?: ""
fun Int?.defIfNull(def: Int = 0) = this ?: def

fun emptyString() = ""

fun emptyItem() = PokemonItemView(
    name = emptyString(),
    weight = 0,
    height = 0,
    experience = 0,
    front_default = emptyString(),
    back_default = emptyString(),
    stats = listOf(),
    abilities = listOf(),
    types = listOf()
)

fun ImageView.loadFromUrl(url: String) {
    GlideApp.with(this.context.applicationContext)
        .load(url)
        .into(this)
}

fun TextView.initText(text: String) {
    if (text.isNotEmpty()) this.text = text else this.text =
        this.context.applicationContext.getString(R.string.none)
}

fun <T> Single<T>.toLiveData(): LiveData<T> =
    LiveDataReactiveStreams.fromPublisher(this.toFlowable())

fun <T> Flowable<T>.toLiveData(): LiveData<T> =
    LiveDataReactiveStreams.fromPublisher(this)