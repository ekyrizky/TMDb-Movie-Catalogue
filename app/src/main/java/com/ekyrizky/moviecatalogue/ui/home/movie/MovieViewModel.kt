package com.ekyrizky.moviecatalogue.ui.home.movie

import androidx.lifecycle.ViewModel
import com.ekyrizky.moviecatalogue.data.source.ContentRepository

class MovieViewModel(private val movieCatalogueRepository: ContentRepository) : ViewModel() {
    fun getMovies(sort: String) = movieCatalogueRepository.getMovies(sort)
}