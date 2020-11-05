package com.evaluation.pokemons.model.item.database.statistic

import androidx.room.Relation

data class StatisticTableView(
    var index: Int,
    val name: String,
    val url: String,

    @Relation(parentColumn = "index", entityColumn = "index")
    var statistic: List<StatisticNameTableItem>
)