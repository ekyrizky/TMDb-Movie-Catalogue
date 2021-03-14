package com.ekyrizky.core.di

import com.ekyrizky.core.data.MovieRepository
import com.ekyrizky.core.data.TvShowRepository
import com.ekyrizky.core.domain.repository.IMovieRepository
import com.ekyrizky.core.domain.repository.ITvShowRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMovieRepository(movieRepository: MovieRepository): IMovieRepository

    @Binds
    abstract fun provideTvShowRepository(tvShowRepository: TvShowRepository): ITvShowRepository
}