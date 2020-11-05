package com.evaluation.pokemons.model.item.database.pokemon

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.evaluation.pokemons.model.item.database.TableItem

/**
 * @author Vladyslav Havrylenko
 * @since 27.10.2020
 */
@Entity(tableName = "pokemon_info",
    foreignKeys = [
        ForeignKey(
            entity = PokemonTableItem::class,
            parentColumns = ["index"],
            childColumns = ["index"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PokemonInfoTableItem(
    @PrimaryKey
    var index: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val experience: Int,
    val front_default: String,
    val back_default: String,
    val stats: String,
    val abilities: String,
    val types: String
) : TableItem