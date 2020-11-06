package com.evaluation.pokemons.network

import com.evaluation.pokemons.model.item.rest.pokemon.Pokemon
import io.reactivex.Completable
import io.reactivex.Single

interface AppPokemonsRestApiDao {

    fun checkCloudConnection(): Completable

    fun checkBoot(): Completable

    fun pokemonList(offset: Int, limit: Int): Completable

    fun pokemonInfo(url: String, index: Int): Single<Pokemon>

    fun languageList(offset: Int, limit: Int): Completable

    fun statisticList(offset: Int, limit: Int): Completable

    fun abilityList(offset: Int, limit: Int): Completable

    fun typeList(offset: Int, limit: Int): Completable

}