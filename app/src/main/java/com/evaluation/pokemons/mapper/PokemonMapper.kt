package com.evaluation.pokemons.mapper

import com.evaluation.pokemons.model.item.database.TableItem
import com.evaluation.pokemons.model.item.database.ability.AbilityNameTableItem
import com.evaluation.pokemons.model.item.database.ability.AbilityTableItem
import com.evaluation.pokemons.model.item.database.ability.AbilityTableView
import com.evaluation.pokemons.model.item.database.language.LanguageTableItem
import com.evaluation.pokemons.model.item.database.pokemon.PokemonInfoTableItem
import com.evaluation.pokemons.model.item.database.pokemon.PokemonTableItem
import com.evaluation.pokemons.model.item.database.statistic.StatisticNameTableItem
import com.evaluation.pokemons.model.item.database.statistic.StatisticTableItem
import com.evaluation.pokemons.model.item.database.statistic.StatisticTableView
import com.evaluation.pokemons.model.item.database.types.TypeNameTableItem
import com.evaluation.pokemons.model.item.database.types.TypeTableItem
import com.evaluation.pokemons.model.item.database.types.TypeTableView
import com.evaluation.pokemons.model.item.rest.ResponseResult
import com.evaluation.pokemons.model.item.rest.language.Language
import com.evaluation.pokemons.model.item.rest.language.LanguageName
import com.evaluation.pokemons.model.item.rest.language.LanguageResult
import com.evaluation.pokemons.model.item.rest.pokemon.Pokemon
import com.evaluation.pokemons.model.item.rest.pokemon.options.Ability
import com.evaluation.pokemons.model.item.rest.pokemon.options.Stat
import com.evaluation.pokemons.model.item.rest.pokemon.options.Type
import com.evaluation.pokemons.model.item.view.language.LanguageNameView
import com.evaluation.pokemons.model.item.view.language.LanguageView
import com.evaluation.pokemons.model.item.view.pokemon.options.PokemonAbilityView
import com.evaluation.pokemons.model.item.view.pokemon.options.PokemonStatView
import com.evaluation.pokemons.model.item.view.pokemon.options.PokemonTypeView
import com.evaluation.pokemons.model.item.view.pokemon.PokemonItemView
import com.evaluation.pokemons.model.item.view.pokemon.PokemonView
import com.evaluation.utils.DatabaseType
import com.evaluation.utils.defIfNull
import com.evaluation.utils.fromJson
import com.evaluation.utils.toTypedJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

/**
 * @author Vladyslav Havrylenko
 * @since 01.10.2020
 */
class PokemonMapper @Inject constructor(private val gson: Gson) {

    fun toTableItem(item: ResponseResult, index: Int, type: DatabaseType): TableItem {
        return when (type) {
            DatabaseType.LANGUAGE -> LanguageTableItem(
                index = index,
                name = item.name.defIfNull(),
                url = item.url.defIfNull()
            )
            DatabaseType.STATISTICS -> StatisticTableItem(
                index = index,
                name = item.name.defIfNull(),
                url = item.url.defIfNull()
            )
            DatabaseType.ABILITIES -> AbilityTableItem(
                index = index,
                name = item.name.defIfNull(),
                url = item.url.defIfNull()
            )
            DatabaseType.TYPES -> TypeTableItem(
                index = index,
                name = item.name.defIfNull(),
                url = item.url.defIfNull()
            )
            DatabaseType.POKEMON -> PokemonTableItem(
                index = index,
                name = item.name.defIfNull(),
                url = item.url.defIfNull()
            )
        }
    }

    fun toTableItem(item: LanguageResult, index: Int, type: DatabaseType): TableItem {
        return when (type) {
            DatabaseType.STATISTICS -> StatisticNameTableItem(
                index = index,
                name = gson.toTypedJson(item, object : TypeToken<LanguageResult>() {}.type)
            )
            DatabaseType.ABILITIES -> AbilityNameTableItem(
                index = index,
                name = gson.toTypedJson(item, object : TypeToken<LanguageResult>() {}.type)
            )
            DatabaseType.TYPES -> TypeNameTableItem(
                index = index,
                name = gson.toTypedJson(item, object : TypeToken<LanguageResult>() {}.type)
            )
            else -> TODO()
        }
    }

    fun toTableItem(item: Pokemon, index: Int): PokemonInfoTableItem {
        return PokemonInfoTableItem(
            index = index,
            name = item.name.defIfNull(),
            weight = item.weight.defIfNull(),
            height = item.height.defIfNull(),
            experience = item.experience.defIfNull(),
            front_default = item.sprites.front_default.defIfNull(),
            back_default = item.sprites.back_default.defIfNull(),
            stats = gson.toTypedJson(item.stats, object : TypeToken<List<Stat>>() {}.type),
            abilities = gson.toTypedJson(item.abilities, object : TypeToken<List<Ability>>() {}.type),
            types = gson.toTypedJson(item.types, object : TypeToken<List<Type>>() {}.type)
        )
    }

    fun toViewItem(item: LanguageTableItem): LanguageView {
        return LanguageView(
            name = item.name.defIfNull()
        )
    }

    fun toViewItem(item: PokemonTableItem): PokemonView {
        return PokemonView(
            name = item.name.defIfNull(),
            url = item.url.defIfNull()
        )
    }

    fun toViewItem(item: PokemonInfoTableItem, statisticList: List<StatisticTableView>, abilityList: List<AbilityTableView>, typeList: List<TypeTableView>): PokemonItemView {
        return PokemonItemView(
            name = item.name.defIfNull(),
            weight = item.weight.defIfNull(),
            height = item.height.defIfNull(),
            experience = item.experience.defIfNull(),
            front_default = item.front_default.defIfNull(),
            back_default = item.back_default.defIfNull(),
            stats = (gson.fromJson(item.stats) as List<Stat>).mapNotNull { this.toViewItem(statisticList.find { view -> view.name == it.stat.name }) },
            abilities = (gson.fromJson(item.abilities) as List<Ability>).mapNotNull { this.toViewItem(abilityList.find { view -> view.name == it.ability.name }) },
            types = (gson.fromJson(item.types) as List<Type>).mapNotNull { this.toViewItem(typeList.find { view -> view.name == it.type.name }) }
        )
    }

    private fun toViewItem(item: StatisticTableView?): PokemonStatView? {
        return item?.let {
            PokemonStatView(
                names = (gson.fromJson(item.statistic[0].name) as LanguageResult).names.map { this.toViewItem(it) }
            )
        }
    }

    private fun toViewItem(item: AbilityTableView?): PokemonAbilityView? {
        return item?.let {
            PokemonAbilityView(
                names = (gson.fromJson(item.ability[0].name) as LanguageResult).names.map { this.toViewItem(it) }
            )
        }
    }

    private fun toViewItem(item: TypeTableView?): PokemonTypeView? {
        return item?.let {
            PokemonTypeView(
                names = (gson.fromJson(item.type[0].name) as LanguageResult).names.map { this.toViewItem(it) }
            )
        }
    }

    private fun toViewItem(item: LanguageName): LanguageNameView {
        return LanguageNameView(
            name = item.name.defIfNull(),
            language = this.toViewItem(item.language)
        )
    }

    private fun toViewItem(item: Language): LanguageView {
        return LanguageView(
            name = item.name.defIfNull()
        )
    }
}