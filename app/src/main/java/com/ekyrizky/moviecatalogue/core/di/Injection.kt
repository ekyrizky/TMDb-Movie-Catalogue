package com.ekyrizky.moviecatalogue.core.di

import android.content.Context
import com.ekyrizky.moviecatalogue.core.data.ContentRepository
import com.ekyrizky.moviecatalogue.core.data.source.local.LocalDataSource
import com.ekyrizky.moviecatalogue.core.data.source.local.room.ContentDatabase
import com.ekyrizky.moviecatalogue.core.data.source.remote.RemoteDataSource
import com.ekyrizky.moviecatalogue.core.data.source.remote.network.ApiConfig
import com.ekyrizky.moviecatalogue.core.domain.repository.IContentRepository
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentInteractor
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentUseCase
import com.ekyrizky.moviecatalogue.core.utils.AppExecutors

object Injection {

    private fun provideRepository(context: Context): IContentRepository {
        val database = ContentDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance(ApiConfig.provideApiService())
        val localDataSource = LocalDataSource.getInstance(database.contentDao())
        val appExecutors = AppExecutors()

        return ContentRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

    fun provideContentUseCase(context: Context): ContentUseCase {
        val repository = provideRepository(context)
        return ContentInteractor(repository)
    }
}