package com.evaluation.pokemons.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.evaluation.pokemons.model.item.database.ability.AbilityTableItem
import com.evaluation.pokemons.model.item.database.ability.AbilityNameTableItem
import com.evaluation.pokemons.model.item.database.ability.AbilityTableView
import com.evaluation.pokemons.model.item.database.language.LanguageTableItem
import com.evaluation.pokemons.model.item.database.pokemon.*
import com.evaluation.pokemons.model.item.database.statistic.StatisticNameTableItem
import com.evaluation.pokemons.model.item.database.statistic.StatisticTableItem
import com.evaluation.pokemons.model.item.database.statistic.StatisticTableView
import com.evaluation.pokemons.model.item.database.types.TypeTableItem
import com.evaluation.pokemons.model.item.database.types.TypeNameTableItem
import com.evaluation.pokemons.model.item.database.types.TypeTableView
import io.reactivex.Single

@Dao
interface AppPokemonsDatabaseDao {

    @Query("SELECT * FROM languages ORDER BY `index` ASC")
    fun languageList(): Single<List<LanguageTableItem>>

    @Query("SELECT * FROM statistics ORDER BY `index` ASC")
    fun statisticListView(): List<StatisticTableView>

    @Query("SELECT * FROM abilities ORDER BY `index` ASC")
    fun abilityListView(): List<AbilityTableView>

    @Query("SELECT * FROM types ORDER BY `index` ASC")
    fun typeListView(): List<TypeTableView>

    @Query("SELECT * FROM statistics ORDER BY `index` ASC")
    fun statisticList(): List<StatisticTableItem>

    @Query("SELECT * FROM abilities ORDER BY `index` ASC")
    fun abilityList(): List<AbilityTableItem>

    @Query("SELECT * FROM types ORDER BY `index` ASC")
    fun typeList(): List<TypeTableItem>

    @Query("SELECT * FROM types ORDER BY `index` ASC")
    fun categoryList(): Single<List<TypeTableItem>>

    @Query("SELECT * FROM pokemons ORDER BY `index` ASC")
    fun pokemonList(): List<PokemonTableItem>

    @Query("SELECT * FROM pokemon_info ORDER BY `index` ASC")
    fun pokemonInfoList(): List<PokemonInfoTableItem>

    @Query("SELECT * FROM pokemon_info ORDER BY `index` ASC LIMIT :limit OFFSET :offset ")
    fun pokemonPagedList(limit: Int, offset: Int): Single<List<PokemonInfoTableItem>>

    @Query("SELECT * FROM pokemon_info WHERE name LIKE '%' || :filter || '%' ORDER BY `index` ASC LIMIT :limit OFFSET :offset ")
    fun pokemonPagedList(limit: Int, offset: Int, filter: String): Single<List<PokemonInfoTableItem>>

    @Query("SELECT * FROM pokemon_info WHERE `index` IN (:indexes) ORDER BY `index` ASC LIMIT :limit OFFSET :offset ")
    fun pokemonPagedList(indexes: List<Int>, limit: Int, offset: Int): Single<List<PokemonInfoTableItem>>

    @Query("SELECT * FROM pokemon_info WHERE `index` IN (:indexes) AND name LIKE '%' || :filter || '%' ORDER BY `index` ASC LIMIT :limit OFFSET :offset ")
    fun pokemonPagedList(indexes: List<Int>, limit: Int, offset: Int, filter: String): Single<List<PokemonInfoTableItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLanguageList(items: List<LanguageTableItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStatisticList(items: List<StatisticTableItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStatisticNames(item: StatisticNameTableItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAbilityList(items: List<AbilityTableItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAbilityNames(item: AbilityNameTableItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTypeList(items: List<TypeTableItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTypeNames(item: TypeNameTableItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonList(items: List<PokemonTableItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemon(item: PokemonInfoTableItem)

    @Query("SELECT `index` FROM pokemon_info ORDER BY `index`")
    fun pokemonPagedListCount(): Single<List<Int>>

    @Query("SELECT `index` FROM pokemon_info WHERE name LIKE '%' || :filter || '%' ORDER BY `index`")
    fun pokemonPagedListCount(filter: String): Single<List<Int>>

    @Query("SELECT `index` FROM pokemon_info WHERE `index` IN (:indexes) ORDER BY `index`")
    fun pokemonPagedListCount(indexes: List<Int>): Single<List<Int>>

    @Query("SELECT `index` FROM pokemon_info WHERE `index` IN (:indexes) AND name LIKE '%' || :filter || '%' ORDER BY `index`")
    fun pokemonPagedListCount(indexes: List<Int>, filter: String): Single<List<Int>>

    @Query("DELETE FROM languages")
    fun deleteLanguageList()

    @Query("DELETE FROM statistics")
    fun deleteStatistic()

    @Query("DELETE FROM statistics_name")
    fun deleteStatisticName()

    @Query("DELETE FROM abilities")
    fun deleteAbilities()

    @Query("DELETE FROM abilities_name")
    fun deleteAbilitiesName()

    @Query("DELETE FROM types")
    fun deleteTypes()

    @Query("DELETE FROM types_name")
    fun deleteTypesName()

    @Query("DELETE FROM pokemons")
    fun deletePokemonList()

}