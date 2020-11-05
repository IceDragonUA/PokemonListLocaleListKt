package com.evaluation.pokemons.model.item.database.ability

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.evaluation.pokemons.model.item.database.TableItem

/**
 * @author Vladyslav Havrylenko
 * @since 26.10.2020
 */
@Entity(tableName = "abilities")
data class AbilityTableItem(
    @PrimaryKey
    var index: Int,
    val name: String,
    val url: String
) : TableItem