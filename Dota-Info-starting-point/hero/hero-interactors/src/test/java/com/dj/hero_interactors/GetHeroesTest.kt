package com.dj.hero_interactors

import com.dj.core.DataState
import com.dj.core.ProgressBarState
import com.dj.core.UIComponent
import com.dj.hero_datasource_test.cache.HeroCacheFake
import com.dj.hero_datasource_test.cache.HeroDatabaseFake
import com.dj.hero_datasource_test.network.HeroServiceFake
import com.dj.hero_datasource_test.network.HeroServiceResponseType
import com.dj.hero_datasource_test.network.data.HeroDataValid
import com.dj.hero_datasource_test.network.data.HeroDataValid.NUM_HEROES
import com.dj.hero_datasource_test.network.util.serializeHeroData
import com.dj.hero_domain.Hero
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.lang.IllegalStateException
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse

class GetHeroesTest {

    private lateinit var getHeroes: GetHeroes

    @Test
    fun `get heroes return success`() = runBlocking {
        val heroDatabaseFake = HeroDatabaseFake()
        val heroCacheFake = HeroCacheFake(heroDatabaseFake)
        val heroServiceFake = HeroServiceFake.build(
            type = HeroServiceResponseType.GoodData
        )

        getHeroes = GetHeroes(
            cache = heroCacheFake,
            service = heroServiceFake
        )

        // confirm cache is empty
        var cachedHeroes = heroCacheFake.selectAll()
        assert(cachedHeroes.isEmpty())

        // execute the use case
        val emissions = getHeroes.execute().toList()

        // first emission is Loading
        assert(emissions[0] == DataState.Loading<List<Hero>>(ProgressBarState.Loading))

        // second emission data from cache
        assert(emissions[1] is DataState.Data)
        assert((emissions[1] as DataState.Data).data?.size ?: 0 == NUM_HEROES)

        // confirm cache is no longer empty
        cachedHeroes = heroCacheFake.selectAll()
        assert(cachedHeroes.size == NUM_HEROES)

        // final emission is Idle
        assert(emissions[2] == DataState.Loading<List<Hero>>(ProgressBarState.Idle))

    }

    @Test
    fun `get heroes malformed data should be successful`() = runBlocking {
        // setup
        val heroDatabase = HeroDatabaseFake()
        val heroCache = HeroCacheFake(heroDatabase)
        val heroService = HeroServiceFake.build(
            type = HeroServiceResponseType.MalformedData // Malformed Data
        )

        getHeroes = GetHeroes(
            cache = heroCache,
            service = heroService
        )

        // Confirm the cache is empty before any use-cases have been executed
        var cachedHeroes = heroCache.selectAll()
        assert(cachedHeroes.isEmpty())

        // Add some data to the cache by executing a successful request
        val heroData = serializeHeroData(HeroDataValid.data)
        heroCache.insert(heroData)

        // Confirm the cache is not empty anymore
        cachedHeroes = heroCache.selectAll()
        assert(cachedHeroes.size == 122)

        // Execute the use-case
        val emissions = getHeroes.execute().toList()

        // First emission should be loading
        assert(emissions[0] == DataState.Loading<List<Hero>>(ProgressBarState.Loading))

        // Confirm second emission is error response
        assert(emissions[1] is DataState.Response)
        assert(((emissions[1] as DataState.Response).uiComponent as UIComponent.Dialog).title == "Network Data Error")
        assert(((emissions[1] as DataState.Response).uiComponent as UIComponent.Dialog).description.contains(
            "Unexpected JSON token at offset"))

        // Confirm third emission is data from the cache
        assert(emissions[2] is DataState.Data)
        assert((emissions[2] as DataState.Data).data?.size == 122)

        // Confirm the cache is still not empty
        cachedHeroes = heroCache.selectAll()
        assert(cachedHeroes.size == 122)

        // Confirm loading state is IDLE
        assert(emissions[3] == DataState.Loading<List<Hero>>(ProgressBarState.Idle))

    }
}