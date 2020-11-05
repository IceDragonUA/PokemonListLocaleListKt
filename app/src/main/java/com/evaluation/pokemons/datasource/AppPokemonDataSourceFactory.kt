package com.evaluation.pokemons.datasource

import androidx.paging.DataSource
import com.evaluation.adapter.viewholder.item.BaseItemView
import com.evaluation.utils.emptyString
import javax.inject.Inject

/**
 * @author Vladyslav Havrylenko
 * @since 08.10.2020
 */

class AppPokemonDataSourceFactory @Inject constructor(private var dataSource: AppPokemonDataSource) :
    DataSource.Factory<Int, BaseItemView>() {

    var query = emptyString()
    var category = emptyString()
    var network = dataSource.network

    override fun create(): DataSource<Int, BaseItemView> {
        dataSource.query = query
        dataSource.category = category
        return dataSource
    }

}