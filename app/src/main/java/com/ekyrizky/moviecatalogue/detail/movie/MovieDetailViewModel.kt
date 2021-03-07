package com.ekyrizky.moviecatalogue.detail.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ekyrizky.moviecatalogue.core.domain.model.movie.MovieDetailDomain
import com.ekyrizky.moviecatalogue.core.domain.usecase.ContentUseCase
import com.ekyrizky.moviecatalogue.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailViewModel(private val contentUseCase: ContentUseCase): ViewModel() {

    fun getMovieDetail(id: String) = contentUseCase.getMovieDetail(id.toInt()).asLiveData()

    fun insertFavoriteMovie(movieDetail: MovieDetailDomain) {
        val movieValue = DataMapper.mapDetailMovieDomainToFavoriteEntity(movieDetail)
        viewModelScope.launch(Dispatchers.IO) {
            contentUseCase.insertFavoriteMovie(movieValue)
        }
    }

    suspend fun checkFavoriteMovie(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            contentUseCase.checkFavoriteMovie(id)
        }
    }

    fun deleteFavoriteMovieById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            contentUseCase.deleteFavoriteMovieById(id)
        }
    }
}