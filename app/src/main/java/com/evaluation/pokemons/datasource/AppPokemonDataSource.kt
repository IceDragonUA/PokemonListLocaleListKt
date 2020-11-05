package com.evaluation.pokemons.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.adapter.viewholder.item.NoItemView
import com.evaluation.pokemons.repository.AppPokemonsRepository
import com.evaluation.pokemons.repository.AppPokemonsRepositoryImpl
import com.evaluation.utils.NetworkState
import com.evaluation.utils.PAGE_OFFSET
import com.evaluation.utils.PAGE_SIZE
import com.evaluation.utils.emptyString
import io.reactivex.disposables.Disposable
import javax.inject.Inject


/**
 * @author Vladyslav Havrylenko
 * @since 08.10.2020
 */
class AppPokemonDataSource @Inject constructor(
    private val repository: AppPokemonsRepository
) : PageKeyedDataSource<Int, BaseItemView>() {

    var query = emptyString()
    var category = emptyString()
    val network = MutableLiveData<Boolean>()

    private var disposeInit: Disposable? = null
    private var disposePaged: Disposable? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, BaseItemView>) {
        disposeInit?.dispose()
        disposePaged?.dispose()
        disposeInit = repository.pokemonListInit(
            offset = PAGE_OFFSET,
            limit = PAGE_SIZE,
            query = query,
            category = category,
            onPrepared = {
                postInitialState(NetworkState.LOADING)
            },
            onSuccess = {
                val refresh = it.firstOrNull() is NoItemView
                postInitialState(refreshNetworkState(refresh))
                callback.onResult(it, null, PAGE_OFFSET + PAGE_SIZE)
            },
            onError = {
                postBeforeAfterState(NetworkState.LOADED)
                callback.onResult(it, null, null)
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, BaseItemView>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, BaseItemView>) {
        disposeInit?.dispose()
        disposePaged?.dispose()
        disposePaged = repository.pokemonListPaged(
            offset = params.key,
            limit = PAGE_SIZE,
            query = query,
            category = category,
            onPrepared = {
                postBeforeAfterState(NetworkState.LOADING)
            },
            onSuccess = {
                postBeforeAfterState(NetworkState.LOADED)
                callback.onResult(it, populateNextKey(it, params))
            },
            onError = {
                postBeforeAfterState(NetworkState.LOADED)
                callback.onResult(listOf(), null)
            })
    }

    private fun postInitialState(networkState: NetworkState) {
        network.postValue(networkState.value())
    }

    private fun postBeforeAfterState(networkState: NetworkState) {
        network.postValue(networkState.value())
    }

    private fun refreshNetworkState(refresh: Boolean) =
        if (refresh) NetworkState.LOADED else NetworkState.LOADING

    private fun populateNextKey(it: MutableList<BaseItemView>, params: LoadParams<Int>) =
        if (it.last().next) params.key + PAGE_SIZE else null


}
