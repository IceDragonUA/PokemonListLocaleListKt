package com.evaluation.language.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.pokemons.interaction.AppPokemonsInteraction
import com.evaluation.pokemons.model.item.database.language.LanguageTableItem
import com.evaluation.pokemons.model.item.view.language.LanguageView
import com.evaluation.storage.ConfigPreferences


/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
class LanguageViewModel @ViewModelInject constructor(
    private val interaction: AppPokemonsInteraction,
    private val configPreferences: ConfigPreferences
) : ViewModel() {

    val refresh = MutableLiveData<Boolean>()
    private val trigger = MutableLiveData<Unit>()

    val result: LiveData<MutableList<BaseItemView>> =
        trigger.switchMap { languageListLiveData() }

    init {
        load()
    }

    private fun trigger() {
        trigger.value = Unit
    }

    private fun load() {
        refresh.value = true
        trigger()
    }

    private fun ready() {
        refresh.value = false
    }

    private fun languageListLiveData(): LiveData<MutableList<BaseItemView>> =
        interaction.loadLanguages().map { mapLanguageList(it) }

    private fun mapLanguageList(it: MutableList<BaseItemView>): MutableList<BaseItemView> {
        ready()
        return it
    }

    fun saveLanguage(item: String) {
        configPreferences.saveLanguage(item)
    }

}