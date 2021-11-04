package com.dj.hero_interactors

import com.dj.core.DataState
import com.dj.core.ProgressBarState
import com.dj.core.UIComponent
import com.dj.hero_datasource.cache.HeroCache
import com.dj.hero_datasource.network.HeroService
import com.dj.hero_domain.Hero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetHeroes(
    private val cache: HeroCache,
    private val service: HeroService,
    // private val logger: Logger
    // TODO("Add Caching")
) {

    fun execute(): Flow<DataState<List<Hero>>> = flow {
        try {
            emit(DataState.Loading(progressBarState = ProgressBarState.Loading))

            val heroes = try {
                service.getHeroStats()
            } catch (e: Exception) {
                e.printStackTrace()
                emit(DataState.Response<List<Hero>>(
                    uiComponent = UIComponent.Dialog(
                        title = "Network Data Error",
                        description = e.message ?: "Unknown Error"
                    )
                ))
                emptyList<Hero>()
            }

            // cache network data
            cache.insert(heroes)

            // emit data from cache
            val cachedHeroes = cache.selectAll()
            emit(DataState.Data(cachedHeroes))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Response<List<Hero>>(
                uiComponent = UIComponent.Dialog(
                    title = "Error",
                    description = e.message ?: "Unknown Error"
                )
            ))
        } finally {
            emit(DataState.Loading(progressBarState = ProgressBarState.Idle))
        }
    }
}