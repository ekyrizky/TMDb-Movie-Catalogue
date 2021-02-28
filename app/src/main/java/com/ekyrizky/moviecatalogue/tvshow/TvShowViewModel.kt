package com.ekyrizky.moviecatalogue.tvshow

import androidx.lifecycle.ViewModel
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentUseCase

class TvShowViewModel(private val contentUseCase: ContentUseCase): ViewModel() {
    fun getTvShows(sort: String) = contentUseCase.getTvShows(sort)
}