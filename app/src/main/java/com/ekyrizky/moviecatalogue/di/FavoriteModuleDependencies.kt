package com.ekyrizky.moviecatalogue.di

import com.ekyrizky.core.domain.usecase.MovieUseCase
import com.ekyrizky.core.domain.usecase.TvShowUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@EntryPoint
@InstallIn(ApplicationComponent::class)
interface FavoriteModuleDependencies {

    fun movieUsecase(): MovieUseCase

    fun tvShowUsecase(): TvShowUseCase
}