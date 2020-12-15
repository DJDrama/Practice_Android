package com.answer.univ.di

import android.content.Context
import com.answer.univ.repository.LauncherRepository
import com.answer.univ.repository.LauncherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideLauncherRepository(
        @ApplicationContext app: Context
    ) : LauncherRepository = LauncherRepositoryImpl(app)
}