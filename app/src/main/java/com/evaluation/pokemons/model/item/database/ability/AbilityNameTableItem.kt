package com.evaluation.pokemons.model.item.database.ability

import androidx.room.*
import com.evaluation.pokemons.model.item.database.TableItem
import com.evaluation.pokemons.model.item.database.pokemon.PokemonTableItem
import com.evaluation.pokemons.model.item.database.types.TypeTableItem

/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
@Entity(tableName = "abilities_name",
    foreignKeys = [
        ForeignKey(
            entity = AbilityTableItem::class,
            parentColumns = ["index"],
            childColumns = ["index"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AbilityNameTableItem(
    @PrimaryKey
    var index: Int,
    val name: String
) : TableItem