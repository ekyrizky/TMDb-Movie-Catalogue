package com.ekyrizky.moviecatalogue.di

import com.ekyrizky.core.domain.usecase.ContentUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@EntryPoint
@InstallIn(ApplicationComponent::class)
interface FavoriteModuleDependencies {

    fun contentUsecase(): ContentUseCase
}