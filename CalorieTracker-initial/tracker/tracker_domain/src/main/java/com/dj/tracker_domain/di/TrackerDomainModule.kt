package com.dj.tracker_domain.di

import com.dj.core.domain.preferences.Preferences
import com.dj.tracker_domain.repository.TrackerRepository
import com.dj.tracker_domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class TrackerDomainModule {

    @ViewModelScoped
    @Provides
    fun provideTrackerUseCases(
        preferences: Preferences,
        repository: TrackerRepository
    ): TrackerUseCases {
        return TrackerUseCases(
            trackFood = TrackFood(repository),
            searchFood = SearchFood(repository),
            getFoodsForDate = GetFoodsForDate(repository),
            deleteTrackedFood = DeleteTrackedFood(repository),
            calculateMealNutrients = CalculateMealNutrients(preferences = preferences)
        )
    }
}