package com.evaluation.viewmodel.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.evaluation.pokemons.interaction.AppPokemonsInteraction
import com.evaluation.pokemons.model.item.database.language.LanguageTableItem
import com.evaluation.pokemons.model.item.database.types.TypeTableItem


/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
class MainViewModel @ViewModelInject constructor(
    private val interaction: AppPokemonsInteraction
) : ViewModel() {

    private var _categoryResult: LiveData<List<TypeTableItem>>? = null
    val categoryResult: LiveData<List<TypeTableItem>>
        get() {
            if (_categoryResult == null) {
                _categoryResult = LiveDataReactiveStreams.fromPublisher(interaction.loadCategories())
            }
            return _categoryResult ?: throw AssertionError("Set to null by another thread")
        }

}