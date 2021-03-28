package com.ekyrizky.core.di

import android.content.Context
import androidx.room.Room
import com.ekyrizky.core.data.source.local.room.ContentDatabase
import com.ekyrizky.core.data.source.local.room.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ContentDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("ekyrizky".toCharArray())
        val factory = SupportFactory(passphrase)

        return Room.databaseBuilder(context, ContentDatabase::class.java, "Content.db")
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }


    @Provides
    fun providesMovieDao(database: ContentDatabase): MovieDao = database.movieDao()
}