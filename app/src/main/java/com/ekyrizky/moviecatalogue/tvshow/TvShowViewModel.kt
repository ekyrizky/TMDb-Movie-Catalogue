package com.ekyrizky.moviecatalogue.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentUseCase
import javax.inject.Inject

class TvShowViewModel  @Inject constructor(private val contentUseCase: ContentUseCase): ViewModel() {
    fun getTvShows(sort: String) = contentUseCase.getTvShows(sort).asLiveData()
}