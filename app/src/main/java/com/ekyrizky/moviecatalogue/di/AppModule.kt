package com.ekyrizky.moviecatalogue.di

import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentInteractor
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentUseCase
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun provideContentUseCase(contentInteractor: ContentInteractor): ContentUseCase
}