package com.evaluation.pokemons.model.item.database.ability

import androidx.room.Relation

data class AbilityTableView(
    var index: Int,
    val name: String,
    val url: String,

    @Relation(parentColumn = "index", entityColumn = "index")
    var ability: List<AbilityNameTableItem>
)