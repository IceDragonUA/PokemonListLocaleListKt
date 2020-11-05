package com.evaluation.pokemons.network

import io.reactivex.Completable

interface AppPokemonsRestApiDao {

    fun checkCloudConnection(): Completable

    fun checkBoot(): Completable

    fun pokemonList(offset: Int, limit: Int): Completable

    fun languageList(offset: Int, limit: Int): Completable

    fun statisticList(offset: Int, limit: Int): Completable

    fun abilityList(offset: Int, limit: Int): Completable

    fun typeList(offset: Int, limit: Int): Completable

}