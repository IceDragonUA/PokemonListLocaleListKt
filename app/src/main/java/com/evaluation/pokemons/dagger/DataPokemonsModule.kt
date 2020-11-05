package com.evaluation.pokemons.dagger

import android.content.Context
import com.evaluation.database.AppDatabase
import com.evaluation.executor.BaseExecutor
import com.evaluation.network.RestApi
import com.evaluation.network.handler.NetworkHandler
import com.evaluation.pokemons.database.AppPokemonsDatabaseDao
import com.evaluation.pokemons.interaction.AppPokemonsInteraction
import com.evaluation.pokemons.interaction.AppPokemonsInteractionImpl
import com.evaluation.pokemons.mapper.PokemonMapper
import com.evaluation.pokemons.network.AppPokemonsRestApiDao
import com.evaluation.pokemons.network.AppPokemonsRestApiDaoImpl
import com.evaluation.pokemons.repository.AppPokemonsRepository
import com.evaluation.pokemons.repository.AppPokemonsRepositoryImpl
import com.evaluation.storage.ConfigPreferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataPokemonsModule {

    @Singleton
    @Provides
    fun appRest(appRest: RestApi, appDatabase: AppPokemonsDatabaseDao, handler: NetworkHandler, config: ConfigPreferences, mapper: PokemonMapper): AppPokemonsRestApiDao =
        AppPokemonsRestApiDaoImpl(appRest, appDatabase, handler, config, mapper)

    @Provides
    @Singleton
    fun appDao(appDatabase: AppDatabase): AppPokemonsDatabaseDao = appDatabase.appListDao()

    @Singleton
    @Provides
    fun appRepository(context: Context, mapper: PokemonMapper, remoteDao: AppPokemonsRestApiDao, localDao: AppPokemonsDatabaseDao, config: ConfigPreferences, executor: BaseExecutor) : AppPokemonsRepository =
        AppPokemonsRepositoryImpl(context, mapper, remoteDao, localDao, config, executor)

    @Singleton
    @Provides
    fun appInteraction(repository: AppPokemonsRepository): AppPokemonsInteraction = AppPokemonsInteractionImpl(repository)

}