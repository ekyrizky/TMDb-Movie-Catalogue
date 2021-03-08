package com.ekyrizky.moviecatalogue.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentUseCase
import javax.inject.Inject

class MovieViewModel  @Inject constructor(private val contentUseCase: ContentUseCase) : ViewModel() {
    fun getMovies(sort: String) = contentUseCase.getMovies(sort).asLiveData()
}