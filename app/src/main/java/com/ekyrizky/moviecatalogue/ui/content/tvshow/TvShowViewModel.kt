package com.ekyrizky.moviecatalogue.ui.content.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ekyrizky.moviecatalogue.data.source.ContentRepository
import com.ekyrizky.moviecatalogue.data.source.remote.response.TvShowResponse

class TvShowViewModel(private val contentRepository: ContentRepository): ViewModel() {
    fun getListTvShows(): LiveData<List<TvShowResponse>> = contentRepository.getOnTheAirTvShows()
}