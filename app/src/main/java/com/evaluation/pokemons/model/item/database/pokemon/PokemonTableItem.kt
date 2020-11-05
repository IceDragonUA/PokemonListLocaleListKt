package com.evaluation.pokemons.model.item.database.pokemon

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.evaluation.pokemons.model.item.database.TableItem

/**
 * @author Vladyslav Havrylenko
 * @since 07.10.2020
 */
@Entity(tableName = "pokemons")
data class PokemonTableItem(
    @PrimaryKey
    var index: Int,
    val name: String,
    val url: String
) : TableItem