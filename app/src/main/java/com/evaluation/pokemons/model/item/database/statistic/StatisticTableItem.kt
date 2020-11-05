package com.evaluation.pokemons.model.item.database.statistic

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.evaluation.pokemons.model.item.database.TableItem

/**
 * @author Vladyslav Havrylenko
 * @since 26.10.2020
 */
@Entity(tableName = "statistics")
data class StatisticTableItem(
    @PrimaryKey
    var index: Int,
    val name: String,
    val url: String
) : TableItem