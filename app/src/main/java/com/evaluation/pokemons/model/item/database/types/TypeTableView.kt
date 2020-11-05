package com.evaluation.pokemons.model.item.database.types

import androidx.room.Relation

data class TypeTableView(
    var index: Int,
    val name: String,
    val url: String,

    @Relation(parentColumn = "index", entityColumn = "index")
    var type: List<TypeNameTableItem>
)