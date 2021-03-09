package com.ekyrizky.core.di

import com.ekyrizky.core.data.ContentRepository
import com.ekyrizky.core.domain.repository.IContentRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class, DatabaseModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(contentRepository: ContentRepository): IContentRepository
}