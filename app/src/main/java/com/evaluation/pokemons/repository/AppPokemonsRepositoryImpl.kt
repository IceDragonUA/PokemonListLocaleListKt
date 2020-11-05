package com.evaluation.pokemons.repository

import android.content.Context
import com.evaluation.R
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.adapter.viewholder.item.NoItemView
import com.evaluation.executor.ThreadExecutor
import com.evaluation.language.adapter.viewholder.item.LanguageItemView
import com.evaluation.pokemons.adapter.viewholder.item.CardItemView
import com.evaluation.pokemons.database.AppPokemonsDatabaseDao
import com.evaluation.pokemons.mapper.PokemonMapper
import com.evaluation.pokemons.model.item.database.pokemon.PokemonInfoTableItem
import com.evaluation.pokemons.model.item.database.pokemon.PokemonTableItem
import com.evaluation.pokemons.model.item.database.types.TypeTableItem
import com.evaluation.pokemons.model.item.rest.pokemon.options.Type
import com.evaluation.pokemons.network.AppPokemonsRestApiDao
import com.evaluation.storage.ConfigPreferences
import com.evaluation.utils.LauncherViewState
import com.evaluation.utils.defIfNull
import com.evaluation.utils.fromJson
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Flowable
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
    private val executor: ThreadExecutor
) : AppPokemonsRepository {

    private val compositeDisposable = CompositeDisposable()

    override fun loadPokemons(): Flowable<MutableList<BaseItemView>> {
        return appDatabaseDao.pokemonLiveList()
            .map { pokemonList ->
                val itemList: MutableList<BaseItemView> = mutableListOf()
                pokemonList.forEach {
                    itemList.add(
                        CardItemView(
                            index = it.index.defIfNull().toString(),
                            name = it.name.defIfNull(),
                            viewItem = mapper.toViewItem(it)
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
    }

    override fun loadList(
        offset: Int,
        limit: Int,
        status: PublishSubject<LauncherViewState>,
    ): Observable<LauncherViewState> {
        compositeDisposable.clear()
        compositeDisposable.add(loadData(offset, limit, status))
        return status
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
            .onErrorReturn {
                Timber.e(it, "Loading error")
                LauncherViewState.ERROR
            }
    }

    private fun loadData(
        offset: Int,
        limit: Int,
        status: PublishSubject<LauncherViewState>,
    ): Disposable {
        return Completable.fromAction { appRestApiDao.checkCloudConnection() }
            .andThen(appRestApiDao.pokemonList(offset, limit))
            .doOnComplete { status.onNext(LauncherViewState.FINISHED) }
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
            .subscribe({ loadCompleted() }, { loadError(status) })
    }

    override fun loadStatus(
        offset: Int,
        limit: Int,
        status: PublishSubject<LauncherViewState>,
    ): Observable<LauncherViewState> {
        compositeDisposable.clear()
        compositeDisposable.add(loadSettings(offset, limit, status))
        return status
            .subscribeOn(executor.mainExecutor)
            .observeOn(executor.postExecutor)
            .onErrorReturn {
                Timber.e(it, "Loading error")
                LauncherViewState.ERROR
            }
    }

    private fun loadSettings(
        offset: Int,
        limit: Int,
        status: PublishSubject<LauncherViewState>,
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
            .subscribe({ loadCompleted() }, { loadError(status) })
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
                            index = item.index.defIfNull().toString(),
                            name = item.name.defIfNull(),
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
