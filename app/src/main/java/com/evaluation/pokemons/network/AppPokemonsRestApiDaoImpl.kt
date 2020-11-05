package com.evaluation.pokemons.network

import com.evaluation.network.RestApi
import com.evaluation.network.handler.NetworkHandler
import com.evaluation.pokemons.database.AppPokemonsDatabaseDao
import com.evaluation.pokemons.mapper.PokemonMapper
import com.evaluation.pokemons.model.item.database.ability.AbilityNameTableItem
import com.evaluation.pokemons.model.item.database.ability.AbilityTableItem
import com.evaluation.pokemons.model.item.database.language.LanguageTableItem
import com.evaluation.pokemons.model.item.database.pokemon.PokemonTableItem
import com.evaluation.pokemons.model.item.database.statistic.StatisticNameTableItem
import com.evaluation.pokemons.model.item.database.statistic.StatisticTableItem
import com.evaluation.pokemons.model.item.database.types.TypeNameTableItem
import com.evaluation.pokemons.model.item.database.types.TypeTableItem
import com.evaluation.pokemons.model.item.rest.ResponseResult
import com.evaluation.pokemons.model.item.rest.language.LanguageResult
import com.evaluation.pokemons.model.item.rest.pokemon.Pokemon
import com.evaluation.storage.ConfigPreferences
import com.evaluation.utils.BootException
import com.evaluation.utils.DatabaseType
import com.evaluation.utils.NoConnectionException
import io.reactivex.Completable
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject


class AppPokemonsRestApiDaoImpl @Inject constructor(
    private val appRest: RestApi,
    private val appDatabaseDao: AppPokemonsDatabaseDao,
    private val networkHandler: NetworkHandler,
    private val configPreferences: ConfigPreferences,
    private val mapper: PokemonMapper
) : AppPokemonsRestApiDao {

    override fun checkCloudConnection(): Completable {
        return Completable.create {
            if (networkHandler.isConnected) {
                it.onComplete()
                Timber.d("Connected")
            } else {
                it.onError(NoConnectionException())
                Timber.d("No Connection")
            }
        }
    }

    override fun checkBoot(): Completable {
        return Completable.create {
            if (!configPreferences.restoreBoot()) {
                it.onComplete()
                Timber.d("First boot")
            } else {
                it.onError(BootException())
                Timber.d("Not first boot")
            }
        }
    }

    override fun pokemonList(offset: Int, limit: Int): Completable {
        return appRest.pokemonList(offset, limit)
            .flatMapCompletable { data ->
                val hasNext = !data.next.isNullOrEmpty()
                return@flatMapCompletable data.results.let {
                    storePokemonListToRoom(it, offset)
                        .flatMapCompletable {
                            if (hasNext) {
                                pokemonList(offset + limit, limit)
                            } else {
                                Completable.complete()
                            }
                        }
                }
            }.onErrorComplete {
                Timber.e(it, "Loading error")
                true
            }
    }

    private fun storePokemonListToRoom(data: List<ResponseResult>, offset: Int): Single<Unit> {
        return Single.fromCallable {
            appDatabaseDao.insertPokemonList(data.mapIndexed { index, result ->
                mapper.toTableItem(result, offset + index, DatabaseType.POKEMON) as PokemonTableItem
            })
        }
    }


    private fun storePokemonItemToRoom(data: Pokemon, index: Int) {
        return appDatabaseDao.insertPokemon(mapper.toTableItem(data, index))
    }

    override fun languageList(offset: Int, limit: Int): Completable {
        return appRest.languageList(offset, limit)
            .flatMapCompletable { data ->
                val hasNext = !data.next.isNullOrEmpty()
                return@flatMapCompletable data.results.let {
                    storeLanguagesToRoom(it, offset)
                        .andThen(
                            if (hasNext) {
                                languageList(offset + limit, limit)
                            } else {
                                Completable.complete()
                            }
                        )
                }
            }.onErrorComplete {
                Timber.e(it, "Loading error")
                true
            }
    }

    private fun storeLanguagesToRoom(data: List<ResponseResult>, offset: Int): Completable {
        return Completable.fromAction {
            appDatabaseDao.insertLanguageList(data.mapIndexed { index, result ->
                mapper.toTableItem(
                    result,
                    offset + index,
                    DatabaseType.LANGUAGE
                ) as LanguageTableItem
            })
        }
    }

    override fun statisticList(offset: Int, limit: Int): Completable {
        return appRest.statisticList(offset, limit)
            .flatMapCompletable { data ->
                val hasNext = !data.next.isNullOrEmpty()
                return@flatMapCompletable data.results.let {
                    storeStatisticToRoom(it, offset)
                        .flatMapCompletable {
                            if (hasNext) {
                                statisticList(offset + limit, limit)
                            } else {
                                val resultList = mutableListOf<Single<LanguageResult>>()
                                val databaseList = appDatabaseDao.statisticList()
                                databaseList.forEach { item ->
                                    resultList.add(appRest.paramList(item.url))
                                }
                                Single.zip(resultList) { args ->
                                    args.forEach { arg ->
                                        databaseList.forEach { item ->
                                            if (item.name == (arg as LanguageResult).name) storeStatisticNameToRoom(
                                                arg,
                                                item.index
                                            )
                                        }
                                    }
                                }.flatMapCompletable {
                                    Completable.complete()
                                }
                            }
                        }
                }
            }.onErrorComplete {
                Timber.e(it, "Loading error")
                true
            }
    }

    private fun storeStatisticToRoom(data: List<ResponseResult>, offset: Int): Single<Unit> {
        return Single.fromCallable {
            appDatabaseDao.insertStatisticList(data.mapIndexed { index, result ->
                mapper.toTableItem(
                    result,
                    offset + index,
                    DatabaseType.STATISTICS
                ) as StatisticTableItem
            })
        }
    }

    private fun storeStatisticNameToRoom(data: LanguageResult, index: Int) {
        return appDatabaseDao.insertStatisticNames(
            mapper.toTableItem(
                data,
                index,
                DatabaseType.STATISTICS
            ) as StatisticNameTableItem
        )
    }

    override fun abilityList(offset: Int, limit: Int): Completable {
        return appRest.abilityList(offset, limit)
            .flatMapCompletable { data ->
                val hasNext = !data.next.isNullOrEmpty()
                return@flatMapCompletable data.results.let {
                    storeAbilityToRoom(it, offset)
                        .flatMapCompletable {
                            if (hasNext) {
                                abilityList(offset + limit, limit)
                            } else {
                                val resultList = mutableListOf<Single<LanguageResult>>()
                                val databaseList = appDatabaseDao.abilityList()
                                databaseList.forEach { item ->
                                    resultList.add(appRest.paramList(item.url))
                                }
                                Single.zip(resultList) { args ->
                                    args.forEach { arg ->
                                        databaseList.forEach { item ->
                                            if (item.name == (arg as LanguageResult).name) storeAbilityNameToRoom(
                                                arg,
                                                item.index
                                            )
                                        }
                                    }
                                }.flatMapCompletable {
                                    Completable.complete()
                                }
                            }
                        }
                }
            }.onErrorComplete {
                Timber.e(it, "Loading error")
                true
            }
    }

    private fun storeAbilityToRoom(data: List<ResponseResult>, offset: Int): Single<Unit> {
        return Single.fromCallable {
            appDatabaseDao.insertAbilityList(data.mapIndexed { index, result ->
                mapper.toTableItem(
                    result,
                    offset + index,
                    DatabaseType.ABILITIES
                ) as AbilityTableItem
            })
        }
    }

    private fun storeAbilityNameToRoom(data: LanguageResult, index: Int) {
        return appDatabaseDao.insertAbilityNames(
            mapper.toTableItem(
                data,
                index,
                DatabaseType.ABILITIES
            ) as AbilityNameTableItem
        )
    }

    override fun typeList(offset: Int, limit: Int): Completable {
        return appRest.typeList(offset, limit)
            .flatMapCompletable { data ->
                val hasNext = !data.next.isNullOrEmpty()
                return@flatMapCompletable data.results.let {
                    storeTypeToRoom(it, offset)
                        .flatMapCompletable {
                            if (hasNext) {
                                typeList(offset + limit, limit)
                            } else {
                                val resultList = mutableListOf<Single<LanguageResult>>()
                                val databaseList = appDatabaseDao.typeList()
                                databaseList.forEach { item ->
                                    resultList.add(appRest.paramList(item.url))
                                }
                                Single.zip(resultList) { args ->
                                    args.forEach { arg ->
                                        databaseList.forEach { item ->
                                            if (item.name == (arg as LanguageResult).name) storeTypeNameToRoom(
                                                arg,
                                                item.index
                                            )
                                        }
                                    }
                                }.flatMapCompletable {
                                    Completable.complete()
                                }
                            }
                        }
                }
            }.onErrorComplete {
                Timber.e(it, "Loading error")
                true
            }
    }

    private fun storeTypeToRoom(data: List<ResponseResult>, offset: Int): Single<Unit> {
        return Single.fromCallable {
            appDatabaseDao.insertTypeList(data.mapIndexed { index, result ->
                mapper.toTableItem(result, offset + index, DatabaseType.TYPES) as TypeTableItem
            })
        }
    }

    private fun storeTypeNameToRoom(data: LanguageResult, index: Int) {
        return appDatabaseDao.insertTypeNames(
            mapper.toTableItem(
                data,
                index,
                DatabaseType.TYPES
            ) as TypeNameTableItem
        )
    }
}