package com.evaluation.pokemons.interaction

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.pokemons.datasource.AppPokemonDataSourceFactory
import com.evaluation.pokemons.model.item.database.types.TypeTableItem
import com.evaluation.pokemons.repository.AppPokemonsRepository
import com.evaluation.pokemons.repository.AppPokemonsRepositoryImpl
import com.evaluation.utils.*
import io.reactivex.BackpressureStrategy
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.Executor
import javax.inject.Inject


/**
 * @author Vladyslav Havrylenko
 * @since 09.10.2020
 */
class AppPokemonsInteractionImpl @Inject constructor(
    private val factory: AppPokemonDataSourceFactory,
    private val config: PagedList.Config,
    private val networkExecutor: Executor,
    private val repository: AppPokemonsRepository
) : AppPokemonsInteraction {

    @MainThread
    override fun pokemonList(query: String, category: String): Listing<BaseItemView> {

        factory.query = query
        factory.category = category

        val liveList =
            LivePagedListBuilder(factory, config)
                .setFetchExecutor(networkExecutor)
                .build()

        return Listing(
            pagedList = liveList,
            networkState = factory.network
        )
    }

    @MainThread
    override fun load(): BehaviorProcessor<LauncherViewState> {
        val processor = BehaviorProcessor.create<LauncherViewState>()
        repository.status(offset = PAGE_OFFSET, limit = PAGE_SIZE, status = PublishSubject.create()).toFlowable(BackpressureStrategy.BUFFER).subscribe(processor)
        return processor
    }

    @MainThread
    override fun loadLanguages(): LiveData<MutableList<BaseItemView>> {
        return repository.loadLanguages().toLiveData()
    }

    @MainThread
    override fun loadCategories(): BehaviorProcessor<List<TypeTableItem>> {
        val processor = BehaviorProcessor.create<List<TypeTableItem>>()
        repository.loadCategories().toFlowable().subscribe(processor)
        return processor
    }

}
