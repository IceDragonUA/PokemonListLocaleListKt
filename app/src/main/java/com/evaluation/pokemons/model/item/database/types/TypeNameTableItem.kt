package com.evaluation.pokemons.model.item.database.types

import androidx.room.*
import com.evaluation.pokemons.model.item.database.TableItem
import com.evaluation.pokemons.model.item.database.pokemon.PokemonTableItem

/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
@Entity(tableName = "types_name",
    foreignKeys = [
        ForeignKey(
            entity = TypeTableItem::class,
            parentColumns = ["index"],
            childColumns = ["index"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TypeNameTableItem(
    @PrimaryKey
    var index: Int,
    val name: String
) : TableItem