package com.ekyrizky.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ekyrizky.moviecatalogue.data.source.ContentRepository
import com.ekyrizky.moviecatalogue.data.source.remote.response.MovieResponse
import com.ekyrizky.moviecatalogue.data.source.remote.response.TvShowResponse

class DetailViewModel(private val contentRepository: ContentRepository): ViewModel() {
    fun getDetailMovie(movieId: Int): LiveData<MovieResponse> = contentRepository.getMovieDetail(movieId)

    fun getDetailTvShow(tvShowId: Int): LiveData<TvShowResponse> = contentRepository.getTvShowDetail(tvShowId)
}