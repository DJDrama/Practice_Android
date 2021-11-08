package com.dj.ui_herolist.ui

import com.dj.core.ProgressBarState
import com.dj.hero_domain.Hero

data class HeroListState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val heros: List<Hero> = listOf(),
)