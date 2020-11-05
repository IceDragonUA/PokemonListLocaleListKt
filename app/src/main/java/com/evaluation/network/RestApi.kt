package com.evaluation.network

import com.evaluation.pokemons.model.item.rest.ResponseResultList
import com.evaluation.pokemons.model.item.rest.language.LanguageResult
import com.evaluation.pokemons.model.item.rest.pokemon.Pokemon
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface RestApi {

    @GET("language")
    fun languageList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Single<ResponseResultList>

    @GET("stat")
    fun statisticList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Single<ResponseResultList>

    @GET("ability")
    fun abilityList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Single<ResponseResultList>

    @GET("type")
    fun typeList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Single<ResponseResultList>

    @GET("pokemon")
    fun pokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Single<ResponseResultList>

    @GET
    fun paramList(@Url url: String?): Single<LanguageResult>

    @GET
    fun pokemon(@Url url: String?): Single<Pokemon>

}