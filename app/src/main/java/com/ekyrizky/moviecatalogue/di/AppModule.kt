package com.ekyrizky.moviecatalogue.di

import com.ekyrizky.core.domain.usecase.MovieInteractor
import com.ekyrizky.core.domain.usecase.MovieUseCase
import com.ekyrizky.core.domain.usecase.TvShowInteractor
import com.ekyrizky.core.domain.usecase.TvShowUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase

    @Binds
    abstract fun provideTvShowUseCase(tvShowInteractor: TvShowInteractor): TvShowUseCase
}