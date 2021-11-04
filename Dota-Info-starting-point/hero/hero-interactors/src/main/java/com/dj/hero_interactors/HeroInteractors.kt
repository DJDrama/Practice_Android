package com.dj.hero_interactors

import com.dj.hero_datasource.cache.HeroCache
import com.dj.hero_datasource.network.HeroService
import com.squareup.sqldelight.db.SqlDriver

data class HeroInteractors(
    val getHeroes: GetHeroes,
    // TODO("Add Other Usecases")
) {

    companion object Factory {
        fun build(sqlDriver: SqlDriver): HeroInteractors {
            val service = HeroService.build()
            val cache = HeroCache.build(sqlDriver)
            return HeroInteractors(
                getHeroes = GetHeroes(
                    service = service,
                    cache = cache
                )
            )
        }

        val schema: SqlDriver.Schema = HeroCache.schema
        val dbName: String = HeroCache.dbName
    }
}