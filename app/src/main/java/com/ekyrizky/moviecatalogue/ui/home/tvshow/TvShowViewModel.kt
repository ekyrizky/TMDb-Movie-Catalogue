package com.ekyrizky.moviecatalogue.ui.home.tvshow

import androidx.lifecycle.ViewModel
import com.ekyrizky.moviecatalogue.data.source.ContentRepository

class TvShowViewModel(private val contentRepository: ContentRepository): ViewModel() {
    fun getTvShows(sort: String) = contentRepository.getTvShows(sort)
}