package com.evaluation.pokemons.interaction

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.pokemons.model.item.database.types.TypeTableItem
import com.evaluation.pokemons.model.item.rest.pokemon.Pokemon
import com.evaluation.pokemons.model.item.view.pokemon.PokemonItemView
import com.evaluation.pokemons.model.item.view.pokemon.PokemonView
import com.evaluation.pokemons.repository.AppPokemonsRepository
import com.evaluation.utils.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


/**
 * @author Vladyslav Havrylenko
 * @since 09.10.2020
 */
class AppPokemonsInteractionImpl @Inject constructor(
    private val repository: AppPokemonsRepository
) : AppPokemonsInteraction {

    @MainThread
    override fun pokemonList(): LiveData<MutableList<BaseItemView>> {
        return repository.loadPokemons().toLiveData()
    }

    @MainThread
    override fun pokemonInfo(name: String): Flowable<PokemonItemView> {
        return repository.loadPokemonInfo(name)
    }

    @MainThread
    override fun pokemonInfo(item: PokemonView, index: Int): Completable {
        return repository.loadPokemonInfo(item, index)
    }

    @MainThread
    override fun loadList(): BehaviorProcessor<LauncherViewState> {
        val processor = BehaviorProcessor.create<LauncherViewState>()
        repository.loadList(offset = PAGE_OFFSET, limit = PAGE_SIZE, status = PublishSubject.create()).toFlowable(BackpressureStrategy.BUFFER).subscribe(processor)
        return processor
    }

    @MainThread
    override fun loadStatus(): BehaviorProcessor<LauncherViewState> {
        val processor = BehaviorProcessor.create<LauncherViewState>()
        repository.loadStatus(offset = PAGE_OFFSET, limit = PAGE_SIZE, status = PublishSubject.create()).toFlowable(BackpressureStrategy.BUFFER).subscribe(processor)
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
