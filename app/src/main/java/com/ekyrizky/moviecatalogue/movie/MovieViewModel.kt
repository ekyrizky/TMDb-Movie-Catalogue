package com.ekyrizky.moviecatalogue.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentUseCase

class MovieViewModel(private val contentUseCase: ContentUseCase) : ViewModel() {
    fun getMovies(sort: String) = contentUseCase.getMovies(sort).asLiveData()
}