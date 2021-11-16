package com.dj.hero_interactors

import com.dj.core.DataState
import com.dj.core.ProgressBarState
import com.dj.hero_datasource_test.cache.HeroCacheFake
import com.dj.hero_datasource_test.cache.HeroDatabaseFake
import com.dj.hero_datasource_test.network.HeroServiceFake
import com.dj.hero_datasource_test.network.HeroServiceResponseType
import com.dj.hero_datasource_test.network.data.HeroDataValid.NUM_HEROES
import com.dj.hero_domain.Hero
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

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
}