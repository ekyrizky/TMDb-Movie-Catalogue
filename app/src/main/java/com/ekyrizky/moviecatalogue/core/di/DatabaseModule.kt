package com.ekyrizky.moviecatalogue.core.di

import android.content.Context
import androidx.room.Room
import com.ekyrizky.moviecatalogue.core.data.source.local.room.ContentDao
import com.ekyrizky.moviecatalogue.core.data.source.local.room.ContentDatabase

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): ContentDatabase =
        Room.databaseBuilder(context,
            ContentDatabase::class.java,
            "Content.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun providesContentDao(database: ContentDatabase): ContentDao = database.contentDao()
}