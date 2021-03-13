package com.ekyrizky.moviecatalogue.di

import com.ekyrizky.core.domain.usecase.ContentInteractor
import com.ekyrizky.core.domain.usecase.ContentUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideContentUseCase(contentInteractor: ContentInteractor): ContentUseCase
}