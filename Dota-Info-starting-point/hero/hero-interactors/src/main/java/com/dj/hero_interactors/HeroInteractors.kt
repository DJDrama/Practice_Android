package com.dj.hero_interactors

import com.dj.hero_datasource.network.HeroService

data class HeroInteractors(
    val getHeroes: GetHeroes,
    // TODO("Add Other Usecases")
) {

    companion object Factory {
        fun build(): HeroInteractors {
            val service = HeroService.build()
            return HeroInteractors(
                getHeroes = GetHeroes(
                    service = service
                )
            )
        }
    }
}