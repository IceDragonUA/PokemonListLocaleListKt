package com.evaluation.tests.dao.network

import android.content.Context
import com.evaluation.network.RestApi
import com.evaluation.network.handler.NetworkHandler
import com.evaluation.pokemons.database.AppPokemonsDatabaseDao
import com.evaluation.pokemons.mapper.PokemonMapper
import com.evaluation.pokemons.network.AppPokemonsRestApiDao
import com.evaluation.pokemons.network.AppPokemonsRestApiDaoImpl
import com.evaluation.tests.dao.RetrofitMocks
import com.evaluation.tests.dao.RoomMocks
import com.evaluation.tests.test
import com.evaluation.utils.PAGE_OFFSET
import com.evaluation.utils.PAGE_SIZE
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


/**
 * @author Vladyslav Havrylenko
 * @since 11.10.2020
 */
class AppPokemonsRestApiDaoTest {

    private lateinit var appPokemonsRestApiDao: AppPokemonsRestApiDao

    private lateinit var appRest: RestApi

    private lateinit var appDatabase: AppPokemonsDatabaseDao

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var networkHandler: NetworkHandler

    @Mock
    private lateinit var mapper: PokemonMapper

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        appRest = RetrofitMocks.appRest
        appDatabase = RoomMocks.appDatabase(context).appListDao()
        appPokemonsRestApiDao = AppPokemonsRestApiDaoImpl(
            appRest,
            appDatabase,
            networkHandler,
            mapper
        )
    }

    @Test
    fun `load language`() {
        assertNotNull(appRest)
        assertNotNull(appDatabase)
        assertNotNull(networkHandler)
        assertNotNull(mapper)
        assertNotNull(appPokemonsRestApiDao)

        appPokemonsRestApiDao.languageList(PAGE_OFFSET, PAGE_SIZE).test {
            assertNoErrors()
            assertComplete()
        }
    }

    @Test
    fun `load pokemon`() {
        assertNotNull(appRest)
        assertNotNull(appDatabase)
        assertNotNull(networkHandler)
        assertNotNull(mapper)
        assertNotNull(appPokemonsRestApiDao)

        appPokemonsRestApiDao.pokemonList(PAGE_OFFSET, PAGE_SIZE).test {
            assertNoErrors()
            assertComplete()
        }
    }

    @Test
    fun `load statistic`() {
        assertNotNull(appRest)
        assertNotNull(appDatabase)
        assertNotNull(networkHandler)
        assertNotNull(mapper)
        assertNotNull(appPokemonsRestApiDao)

        appPokemonsRestApiDao.statisticList(PAGE_OFFSET, PAGE_SIZE).test {
            assertNoErrors()
            assertComplete()
        }
    }

    @Test
    fun `load ability`() {
        assertNotNull(appRest)
        assertNotNull(appDatabase)
        assertNotNull(networkHandler)
        assertNotNull(mapper)
        assertNotNull(appPokemonsRestApiDao)

        appPokemonsRestApiDao.abilityList(PAGE_OFFSET, PAGE_SIZE).test {
            assertNoErrors()
            assertComplete()
        }
    }

    @Test
    fun `load type`() {
        assertNotNull(appRest)
        assertNotNull(appDatabase)
        assertNotNull(networkHandler)
        assertNotNull(mapper)
        assertNotNull(appPokemonsRestApiDao)

        appPokemonsRestApiDao.typeList(PAGE_OFFSET, PAGE_SIZE).test {
            assertNoErrors()
            assertComplete()
        }
    }
}