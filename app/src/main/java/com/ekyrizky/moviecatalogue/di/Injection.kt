package com.ekyrizky.moviecatalogue.di

import android.content.Context
import com.ekyrizky.moviecatalogue.data.source.ContentRepository
import com.ekyrizky.moviecatalogue.data.source.local.LocalDataSource
import com.ekyrizky.moviecatalogue.data.source.local.room.ContentDatabase
import com.ekyrizky.moviecatalogue.data.source.remote.RemoteDataSource
import com.ekyrizky.moviecatalogue.utils.AppExecutors

object Injection {
    fun provideContentRepository(context: Context): ContentRepository {
        val database = ContentDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.contentDao())
        val appExecutors = AppExecutors()

        return ContentRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}