package com.ekyrizky.core.di

import android.content.Context
import androidx.room.Room
import com.ekyrizky.core.data.source.local.room.ContentDatabase
import com.ekyrizky.core.data.source.local.room.MovieDao
import com.ekyrizky.core.data.source.local.room.TvShowDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ContentDatabase =
        Room.databaseBuilder(context,
            ContentDatabase::class.java,
            "Content.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun providesMovieDao(database: ContentDatabase): MovieDao = database.movieDao()

    @Provides
    fun providesTvShowDao(database: ContentDatabase): TvShowDao = database.tvShowDao()
}