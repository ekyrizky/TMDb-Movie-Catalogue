package com.ekyrizky.moviecatalogue.ui.content.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ekyrizky.moviecatalogue.data.source.ContentRepository
import com.ekyrizky.moviecatalogue.data.source.remote.response.MovieResponse

class MovieViewModel(private val contentRepository: ContentRepository): ViewModel() {
    fun getListMovies(): LiveData<List<MovieResponse>> = contentRepository.getNowPlayingMovies()
}