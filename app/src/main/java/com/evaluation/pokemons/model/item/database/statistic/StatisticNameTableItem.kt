package com.evaluation.pokemons.model.item.database.statistic

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.evaluation.pokemons.model.item.database.TableItem
import com.evaluation.pokemons.model.item.database.types.TypeTableItem

/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
@Entity(tableName = "statistics_name",
    foreignKeys = [
        ForeignKey(
            entity = StatisticTableItem::class,
            parentColumns = ["index"],
            childColumns = ["index"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class StatisticNameTableItem(
    @PrimaryKey
    var index: Int,
    val name: String
) : TableItem