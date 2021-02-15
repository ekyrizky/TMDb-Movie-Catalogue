package com.ekyrizky.moviecatalogue.di

import com.ekyrizky.moviecatalogue.data.source.ContentRepository
import com.ekyrizky.moviecatalogue.data.source.remote.RemoteDataSource

object Injection {
    fun provideContentRepository(): ContentRepository {
        val remoteDataSource = RemoteDataSource.getInstance()
        return ContentRepository.getInstance(remoteDataSource)
    }
}