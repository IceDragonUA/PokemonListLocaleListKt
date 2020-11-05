package com.evaluation.pokemons.repository

import android.content.Context
import com.evaluation.R
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.adapter.viewholder.item.EmptyItemView
import com.evaluation.adapter.viewholder.item.NoItemView
import com.evaluation.executor.ThreadExecutor
import com.evaluation.language.adapter.viewholder.item.LanguageItemView
import com.evaluation.pokemons.adapter.viewholder.item.CardItemView
import com.evaluation.pokemons.database.AppPokemonsDatabaseDao
import com.evaluation.pokemons.mapper.PokemonMapper
import com.evaluation.pokemons.model.item.database.pokemon.PokemonInfoTableItem
import com.evaluation.pokemons.model.item.database.types.TypeTableItem
import com.evaluation.pokemons.model.item.rest.pokemon.options.Type
import com.evaluation.pokemons.network.AppPokemonsRestApiDao
import com.evaluation.storage.ConfigPreferences
import com.evaluation.utils.LauncherViewState
import com.evaluation.utils.NO_ITEM
import com.evaluation.utils.defIfNull
import com.evaluation.utils.fromJson
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject


/**
 * @author Vladyslav Havrylenko
 * @since 01.05.2020
 */
class AppPokemonsRepositoryImpl @Inject constructor(
    private val context: Context,
    private val mapper: PokemonMapper,
    private val appRestApiDao: AppPokemonsRestApiDao,
    private val appDatabaseDao: AppPokemonsDatabaseDao,
    private val configPreferences: ConfigPreferences,
    private val executor: ThreadExecutor,
    private val gson: Gson
) : AppPokemonsRepository {

    private val compositeDisposable = CompositeDisposable()

    override fun pokemonListInit(
        offset: Int,
        limit: Int,
        query: String,
        category: String,
        onPrepared: () -> Unit,
        onSuccess: (MutableList<BaseItemView>) -> Unit,
        onError: (MutableList<BaseItemView>) -> Unit
    ): Disposable {
        return Single.zip(
            loadList(category, query, offset, limit),
            listCount(category, query),
            { list, count -> Pair(list, count.size) })
            .doOnSubscribe {
                onPrepared()
            }
            .subscribe(
                { pair ->
                    val pokemonList = pair.first
                    val itemCount = pair.second
                    val itemList: MutableList<BaseItemView> = mutableListOf()
                    val statisticList = appDatabaseDao.statisticListView()
                    val abilityList = appDatabaseDao.abilityListView()
                    val typeList = appDatabaseDao.typeListView()
                    pokemonList.forEach {
                        itemList.add(
                            CardItemView(
                                index = it.index.defIfNull().toString(),
                                next = (offset + limit < itemCount),
                                viewItem = mapper.toViewItem(
                                    it,
                                    statisticList,
                                    abilityList,
                                    typeList
                                )
                            )
                        )
                    }
                    itemList.ifEmpty {
                        itemList.add(
                            NoItemView(
                                title = context.resources.getString(R.string.result).defIfNull()
                            )
                        )
                    }

                    onSuccess(itemList)
                },
                { errorMessage ->
                    Timber.e(errorMessage, "Loading error")

                    val itemList: MutableList<BaseItemView> = mutableListOf()
                    itemList.ifEmpty {
                        itemList.add(
                            NoItemView(
                                title = context.resources.getString(R.string.result).defIfNull()
                            )
                        )
                    }

                    onError(itemList)
                }
            )
    }

    override fun pokemonListPaged(
        offset: Int,
        limit: Int,
        query: String,
        category: String,
        onPrepared: () -> Unit,
        onSuccess: (MutableList<BaseItemView>) -> Unit,
        onError: () -> Unit
    ): Disposable {
        return Single.zip(
            loadList(category, query, offset, limit),
            listCount(category, query),
            { list, count -> Pair(list, count.size) })
            .doOnSubscribe {
                onPrepared()
            }
            .subscribe(
                { pair ->
                    val pokemonList = pair.first
                    val itemCount = pair.second
                    val itemList: MutableList<BaseItemView> = mutableListOf()
                    val statisticList = appDatabaseDao.statisticListView()
                    val abilityList = appDatabaseDao.abilityListView()
                    val typeList = appDatabaseDao.typeListView()
                    pokemonList.forEach {
                        itemList.add(
                            CardItemView(
                                index = it.index.defIfNull().toString(),
                                next = (offset + limit < itemCount),
                                viewItem = mapper.toViewItem(
                                    it,
                                    statisticList,
                                    abilityList,
                                    typeList
                                )
                            )
                        )
                    }

                    itemList.ifEmpty {
                        itemList.add(
                            EmptyItemView(
                                index = NO_ITEM + (offset + limit),
                                next = (offset + limit < itemCount)
                            )
                        )
                    }

                    onSuccess(itemList)
                },
                { errorMessage ->
                    Timber.e(errorMessage, "Loading error")
                    onError()
                }
            )
    }

    private fun loadList(
        category: String,
        query: String,
        offset: Int,
        limit: Int
    ): Single<List<PokemonInfoTableItem>> {
        return (if (category.isEmpty())
            (if (query.isEmpty())
                appDatabaseDao.pokemonPagedList(
                    offset = offset,
                    limit = limit
                ) else
                appDatabaseDao.pokemonPagedList(
                    offset = offset,
                    limit = limit,
                    filter = query
                )) else
            (if (query.isEmpty())
                appDatabaseDao.pokemonPagedList(
                    indexes = indexesByCategory(category),
                    offset = offset,
                    limit = limit
                ) else
                appDatabaseDao.pokemonPagedList(
                    indexes = indexesByCategory(category),
                    offset = offset,
                    limit = limit,
                    filter = query
                )))
    }

    private fun listCount(
        category: String,
        query: String,
    ): Single<List<Int>> {
        return (if (category.isEmpty())
            (if (query.isEmpty())
                appDatabaseDao.pokemonPagedListCount() else
                appDatabaseDao.pokemonPagedListCount(
                    filter = query
                )) else
            (if (query.isEmpty())
                appDatabaseDao.pokemonPagedListCount(
                    indexes = indexesByCategory(category)
                ) else
                appDatabaseDao.pokemonPagedListCount(
                    indexes = indexesByCategory(category),
                    filter = query
                )))
    }

    private fun indexesByCategory(category: String): List<Int> {
        return appDatabaseDao.pokemonInfoList()
            .filter { (gson.fromJson(it.types) as List<Type>).find { type -> type.type.name == category } != null }
            .map { it.index }
    }

    override fun status(
        offset: Int,
        limit: Int,
        status: PublishSubject<LauncherViewState>
    ): Observable<LauncherViewState> {
        compositeDisposable.clear()
        compositeDisposable.add(load(offset, limit, status))
        return status
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
            .onErrorReturn {
                Timber.e(it, "Loading error")
                LauncherViewState.ERROR
            }
    }

    private fun load(
        offset: Int,
        limit: Int,
        status: PublishSubject<LauncherViewState>
    ): Disposable {
        return Completable.fromAction { status.onNext(LauncherViewState.CHECK_CONNECTION) }
            .andThen(appRestApiDao.checkCloudConnection())
            .doOnComplete { status.onNext(LauncherViewState.CHECK_BOOT) }
            .andThen(appRestApiDao.checkBoot())
            .doOnComplete { status.onNext(LauncherViewState.LOAD_LANGUAGE) }
            .andThen(appRestApiDao.languageList(offset = offset, limit = limit))
            .doOnComplete { status.onNext(LauncherViewState.LOAD_STATS) }
            .andThen(appRestApiDao.statisticList(offset = offset, limit = limit))
            .doOnComplete { status.onNext(LauncherViewState.LOAD_ABILITIES) }
            .andThen(appRestApiDao.abilityList(offset = offset, limit = limit))
            .doOnComplete { status.onNext(LauncherViewState.LOAD_TYPES) }
            .andThen(appRestApiDao.typeList(offset = offset, limit = limit))
            .doOnComplete {
                configPreferences.saveBoot(true)
                status.onNext(LauncherViewState.FINISHED)
            }
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
            .subscribe({ loadCompleted() },{ loadError(status) })
    }

    private fun loadCompleted() {
        Timber.d("Loading finished")
    }

    private fun loadError(status: PublishSubject<LauncherViewState>) {
        status.onNext(LauncherViewState.FINISHED)
    }

    override fun loadLanguages(): Single<MutableList<BaseItemView>> {
        return appDatabaseDao.languageList()
            .map {
                val itemList: MutableList<BaseItemView> = mutableListOf()
                it.forEach { item ->
                    itemList.add(
                        LanguageItemView(
                            index = item.name.defIfNull(),
                            next = false,
                            viewItem = mapper.toViewItem(item)
                        )
                    )
                }
                itemList.ifEmpty {
                    itemList.add(
                        NoItemView(
                            title = context.resources.getString(R.string.result).defIfNull()
                        )
                    )
                }
                itemList
            }
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
            .onErrorReturn {
                Timber.e(it, "Loading error")
                mutableListOf()
            }
    }

    override fun loadCategories(): Single<List<TypeTableItem>> {
        return appDatabaseDao.categoryList()
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
            .onErrorReturn {
                Timber.e(it, "Loading error")
                listOf()
            }
    }
}
