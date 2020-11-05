package com.evaluation.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.evaluation.pokemons.database.AppPokemonsDatabaseDao
import com.evaluation.pokemons.model.item.database.language.LanguageTableItem
import com.evaluation.pokemons.model.item.database.ability.AbilityNameTableItem
import com.evaluation.pokemons.model.item.database.ability.AbilityTableItem
import com.evaluation.pokemons.model.item.database.pokemon.PokemonInfoTableItem
import com.evaluation.pokemons.model.item.database.statistic.StatisticNameTableItem
import com.evaluation.pokemons.model.item.database.pokemon.PokemonTableItem
import com.evaluation.pokemons.model.item.database.statistic.StatisticTableItem
import com.evaluation.pokemons.model.item.database.types.TypeNameTableItem
import com.evaluation.pokemons.model.item.database.types.TypeTableItem
import com.evaluation.utils.DATABASE_VERSION

@Database(
    entities = [
        PokemonTableItem::class,
        PokemonInfoTableItem::class,

        StatisticTableItem::class,
        StatisticNameTableItem::class,

        AbilityTableItem::class,
        AbilityNameTableItem::class,

        TypeTableItem::class,
        TypeNameTableItem::class,

        LanguageTableItem::class
    ],
    version = DATABASE_VERSION
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appListDao(): AppPokemonsDatabaseDao

}